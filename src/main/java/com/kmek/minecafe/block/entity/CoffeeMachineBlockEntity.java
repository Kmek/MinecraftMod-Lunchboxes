package com.kmek.minecafe.block.entity;

import com.kmek.minecafe.item.ModItemsInit;
import com.kmek.minecafe.networking.ModMessages;
import com.kmek.minecafe.networking.packet.ItemStackSyncS2CPacket;
import com.kmek.minecafe.recipe.CoffeeMachineRecipe;
import com.kmek.minecafe.screen.CoffeeMachineMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CoffeeMachineBlockEntity extends CustomBaseBlockEntity {
    private static final int SLOT_WATER = 0;
    private static final int SLOT_GROUNDS = 1;
    private static final int SLOT_FILTER = 2;
    private static final int SLOT_OUTPUT = 3;

    private int progress = 0;
    public static final int waterTime = 400;
    public static final int brewTime = 500;
    public static final int maxProgress = waterTime + brewTime;

    public CoffeeMachineBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState, ModBlockEntities.COFFEE_MACHINE.get(), 4, 1, "Coffee Machine");
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                if (pIndex == 0) {
                    return CoffeeMachineBlockEntity.this.progress;
                } else {
                    return 0;
                }
            }

            @Override
            public void set(int pIndex, int pValue) {
                if (pIndex == 0) {
                    CoffeeMachineBlockEntity.this.progress = pValue;
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
                    case SLOT_FILTER -> stack.getItem() == ModItemsInit.COFFEE_FILTER.get() || stack.getItem() == ModItemsInit.COFFEE_FILTER_USED.get();
                    case SLOT_OUTPUT -> stack.getItem() == ModItemsInit.COFFEE_POT.get() || stack.getItem() == ModItemsInit.COFFEE_POT_FULL.get();
                    default -> false;
                };
            }

            @Override
            public int getSlotLimit(int slot) {
                return switch (slot) {
                    case SLOT_FILTER -> 1;
                    case SLOT_OUTPUT -> 1;
                    default -> 64;
                };
            }
        };
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new CoffeeMachineMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    // For block entity rendering
    @Override
    public ItemStack getRenderStack() {
        return itemHandler.getStackInSlot(SLOT_OUTPUT);
    }

    /**
     * Tick progress stuff
     */

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, CoffeeMachineBlockEntity entity) {
        if (level.isClientSide) {
            return;
        }

        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<CoffeeMachineRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(CoffeeMachineRecipe.Type.INSTANCE, inventory, level);

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

    private static boolean hasRecipe(CoffeeMachineBlockEntity entity, SimpleContainer inventory, Optional<CoffeeMachineRecipe> recipe) {
        return recipe.isPresent();
    }

    private static void craftItem(CoffeeMachineBlockEntity entity, SimpleContainer inventory, Optional<CoffeeMachineRecipe> recipe) {
        if (recipe.isPresent()) {
            entity.itemHandler.setStackInSlot(SLOT_WATER, new ItemStack(Items.BUCKET));
            entity.itemHandler.extractItem(SLOT_GROUNDS, 1, false);
            entity.itemHandler.setStackInSlot(SLOT_FILTER, new ItemStack(ModItemsInit.COFFEE_FILTER_USED.get()));
            entity.itemHandler.setStackInSlot(SLOT_OUTPUT, new ItemStack(recipe.get().getResultItem().getItem()));
            entity.resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }
}
