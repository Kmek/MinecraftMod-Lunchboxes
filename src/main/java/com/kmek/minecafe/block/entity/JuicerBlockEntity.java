package com.kmek.minecafe.block.entity;

import com.kmek.minecafe.networking.ModMessages;
import com.kmek.minecafe.networking.packet.ItemStackSyncS2CPacket;
import com.kmek.minecafe.recipe.JuicerRecipe;
import com.kmek.minecafe.screen.JuicerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class JuicerBlockEntity extends CustomBaseBlockEntity {
    // Slots 1-6 are juicer slots
    public static final int SLOT_CUPS = 6;
    public static final int SLOT_OUTPUT = 7;

    private int progress = 0;
    public static final int maxProgress = 600;

    public JuicerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState, ModBlockEntities.JUICER.get(), 8, 1, "Juicer");
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                if (pIndex == 0) {
                    return JuicerBlockEntity.this.progress;
                } else {
                    return 0;
                }
            }

            @Override
            public void set(int pIndex, int pValue) {
                if (pIndex == 0) {
                    JuicerBlockEntity.this.progress = pValue;
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

//            @Override
//            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
//                return switch (slot) {
//                    case SLOT_WATER -> stack.getItem() == Items.WATER_BUCKET || stack.getItem() == Items.BUCKET;
//                    case SLOT_GROUNDS -> stack.getItem() == ModItemsInit.COFFEE_GROUNDS.get();
//                    case SLOT_FILTER -> stack.getItem() == ModItemsInit.COFFEE_FILTER.get() || stack.getItem() == ModItemsInit.COFFEE_FILTER_USED.get();
//                    case SLOT_OUTPUT -> stack.getItem() == ModItemsInit.COFFEE_POT.get() || stack.getItem() == ModItemsInit.COFFEE_POT_FULL.get();
//                    default -> false;
//                };
//            }

            @Override
            public int getSlotLimit(int slot) {
                return switch (slot) {
                    case SLOT_CUPS, SLOT_OUTPUT -> 64;
                    default -> 1;
                };
            }
        };
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new JuicerMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    // For block entity rendering
    @Override
    public ItemStack getRenderStack() {
        return itemHandler.getStackInSlot(SLOT_OUTPUT).isEmpty() ? itemHandler.getStackInSlot(SLOT_CUPS) : itemHandler.getStackInSlot(SLOT_OUTPUT);
    }

    /**
     * Tick progress stuff
     */

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, JuicerBlockEntity entity) {
        if (level.isClientSide) {
            return;
        }

        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<JuicerRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(JuicerRecipe.Type.INSTANCE, inventory, level);

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

    private static boolean hasRecipe(JuicerBlockEntity entity, SimpleContainer inventory, Optional<JuicerRecipe> recipe) {
        return recipe.isPresent() && canInsertIntoOutputSlot(inventory, recipe.get().getResultItem());
    }
    private static boolean canInsertIntoOutputSlot(SimpleContainer inv, ItemStack stack) {
        ItemStack inOutput = inv.getItem(SLOT_OUTPUT);
        return inOutput.isEmpty() || (inOutput.getItem() == stack.getItem() && (inOutput.getMaxStackSize() > inOutput.getCount()));
    }

    private static void craftItem(JuicerBlockEntity entity, SimpleContainer inventory, Optional<JuicerRecipe> recipe) {
        if (recipe.isPresent()) {
            for (int i = 0; i < 7; i++) {
                entity.itemHandler.extractItem(i, 1, false);
            }
            entity.itemHandler.insertItem(SLOT_OUTPUT, recipe.get().getResultItem(), false);
            entity.resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }
}
