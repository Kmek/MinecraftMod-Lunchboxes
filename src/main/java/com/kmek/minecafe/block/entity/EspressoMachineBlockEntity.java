package com.kmek.minecafe.block.entity;

import com.kmek.minecafe.item.ModItemsInit;
import com.kmek.minecafe.networking.ModMessages;
import com.kmek.minecafe.networking.packet.ItemStackSyncS2CPacket;
import com.kmek.minecafe.recipe.EspressoMachineRecipe;
import com.kmek.minecafe.screen.EspressoMachineMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class EspressoMachineBlockEntity extends CustomBaseBlockEntity {

    public static final int SLOT_WATER = 0;
    public static final int SLOT_GROUNDS = 1;
    public static final int SLOT_MILK = 2;
    public static final int SLOT_OUTPUT = 3;

    private int progress = 0;
    public static final int maxProgress = 400;

    public EspressoMachineBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState, ModBlockEntities.ESPRESSO_MACHINE.get(), 4, 1, "Espresso Machine");
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                if (pIndex == 0) {
                    return progress;
                } else {
                    return 0;
                }
            }

            @Override
            public void set(int pIndex, int pValue) {
                if (pIndex == 0) {
                    progress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };

        this.itemHandler = new ItemStackHandler(menuSlotCount) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                if (!level.isClientSide) {
                    ModMessages.sendToClients(new ItemStackSyncS2CPacket(this, worldPosition));
                }
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return switch (slot) {
                    case SLOT_WATER -> stack.getItem() == Items.WATER_BUCKET || stack.getItem() == Items.BUCKET;
                    case SLOT_GROUNDS -> stack.getItem() == ModItemsInit.COFFEE_GROUNDS.get();
                    case SLOT_MILK -> stack.getItem() == Items.MILK_BUCKET || stack.getItem() == ModItemsInit.STEAMED_MILK.get() || stack.getItem() == Items.BUCKET;
                    case SLOT_OUTPUT -> true;
                    default -> false;
                };
            }

            @Override
            public int getSlotLimit(int slot) {
                return switch (slot) {
                    case SLOT_OUTPUT -> 1;
                    default -> 64;
                };
            }
        };
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new EspressoMachineMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    /**
     * Tick progress stuff
     */

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, EspressoMachineBlockEntity entity) {
        if (level.isClientSide) {
            return;
        }

        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<EspressoMachineRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(EspressoMachineRecipe.Type.INSTANCE, inventory, level);

        if (hasRecipe(entity, inventory, recipe)) {
            entity.progress++;
            setChanged(level, blockPos, blockState);
            if (entity.progress >= maxProgress) {
                craftItem(entity, inventory, recipe);
            }
        } else {
            entity.resetProgress();
            setChanged(level, blockPos, blockState);
        }
    }

    private static boolean hasRecipe(EspressoMachineBlockEntity entity, SimpleContainer inventory, Optional<EspressoMachineRecipe> recipe) {
        return recipe.isPresent() && canInsertIntoOutputSlot(inventory, recipe.get().getResultItem());
    }
    private static boolean canInsertIntoOutputSlot(SimpleContainer inv, ItemStack stack) {
        ItemStack inOutput = inv.getItem(SLOT_OUTPUT);
        return (inOutput.getItem() == stack.getItem() || inOutput.isEmpty())
                && (inOutput.getMaxStackSize() > inOutput.getCount());
    }

    private static void craftItem(EspressoMachineBlockEntity entity, SimpleContainer inventory, Optional<EspressoMachineRecipe> recipe) {
        if (recipe.isPresent()) {
            entity.itemHandler.setStackInSlot(SLOT_WATER, new ItemStack(Items.BUCKET));
            if (recipe.get().getIngredients().get(SLOT_GROUNDS) == Ingredient.EMPTY) {
                entity.itemHandler.setStackInSlot(SLOT_MILK, new ItemStack(Items.BUCKET));
            } else {
                entity.itemHandler.extractItem(SLOT_GROUNDS, 1, false);
            }
            entity.itemHandler.setStackInSlot(SLOT_OUTPUT, new ItemStack(recipe.get().getResultItem().getItem(), inventory.getItem(SLOT_OUTPUT).getCount() + 1));
            entity.resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }
}
