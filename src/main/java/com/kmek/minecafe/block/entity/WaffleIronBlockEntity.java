package com.kmek.minecafe.block.entity;

import com.kmek.minecafe.item.ModItemsInit;
import com.kmek.minecafe.item.custom.WaffleItem;
import com.kmek.minecafe.item.custom.WaffleMoldItem;
import com.kmek.minecafe.networking.ModMessages;
import com.kmek.minecafe.networking.packet.ItemStackSyncS2CPacket;
import com.kmek.minecafe.screen.WaffleIronMenu;
import com.kmek.minecafe.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WaffleIronBlockEntity extends CustomBaseBlockEntity {
    public static final int SLOT_FILLING = 3;
    public static final int SLOT_BATTER = 0;
    public static final int SLOT_MOLD = 2;
    public static final int SLOT_OUTPUT = 1;

    private int progress = 0;
    private static final int batteredMessTime = 500;
    private static final int cookedTime = 1100;
    private static final int maxProgress = 1200;

    public WaffleIronBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState, ModBlockEntities.WAFFLE_IRON.get(), 4, 2, "Waffle Iron");
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> progress;
                    case 1 -> maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch(pIndex) {
                    case 0 -> progress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
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
                    case SLOT_BATTER -> itemIsRawWaffleBatter(stack.getItem());
                    case SLOT_FILLING -> itemIsWaffleFilling(stack);
                    case SLOT_MOLD -> itemIsWaffleMold(stack);
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
        return new WaffleIronMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    // For block entity rendering
    @Override
    public ItemStack getRenderStack() {
        return itemHandler.getStackInSlot(SLOT_MOLD);
    }

    /**
     * Tick progress stuff
     */

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, WaffleIronBlockEntity entity) {
        if (level.isClientSide) {
            return;
        }

        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        if (entity.progress == 0) {
            if (canMoveItem(inventory)) {
                moveItem(entity);
                entity.progress++;
                blockState = blockState.setValue(BlockStateProperties.OPEN, false);
                level.setBlock(blockPos, blockState, 3);
                setChanged(level, blockPos, blockState);
            }
        } else if (isCooking(inventory)) {
            // todo eventually if "open" blockstate is true then return to not increment progress
//            if (!entity.getBlockState().getValue(BlockStateProperties.OPEN)) {
                entity.progress++;
                cook(entity);
                setChanged(level, blockPos, blockState);
//            }
        } else {
            entity.resetProgress();
            blockState = blockState.setValue(BlockStateProperties.OPEN, true);
            level.setBlock(blockPos, blockState, 3);
            setChanged(level, blockPos, blockState);
        }
    }

    private static boolean itemIsRawWaffleBatter(Item item) {
        return item == ModItemsInit.RAW_WAFFLE_BATTER.get();
    }

    private static boolean itemIsBatterMess(Item item) {
        return item == ModItemsInit.BATTER_MESS.get();
    }

    private static boolean itemIsWaffle(ItemStack itemStack) {
        return itemStack.is(ModTags.Items.WAFFLE);
    }

    private static boolean itemIsWaffleMold(ItemStack itemStack) {
        return itemStack.is(ModTags.Items.WAFFLE_MOLD);
    }

    private static boolean hasWaffleMold(SimpleContainer inventory) {
        return !inventory.getItem(SLOT_MOLD).isEmpty()
                && itemIsWaffleMold(inventory.getItem(SLOT_MOLD));
    }

    private static boolean itemIsWaffleFilling(ItemStack itemStack) {
        return itemStack.is(ModTags.Items.WAFFLE_FILLING);
    }

    private static boolean canMoveItem(SimpleContainer inventory) {
        return inventory.getItem(SLOT_OUTPUT).isEmpty()
                && itemIsRawWaffleBatter(inventory.getItem(SLOT_BATTER).getItem())
                && hasWaffleMold(inventory);
    }

    private static void moveItem(WaffleIronBlockEntity entity) {
        entity.itemHandler.setStackInSlot(SLOT_OUTPUT, entity.itemHandler.extractItem(SLOT_BATTER, 1, false));
    }

    private static boolean isCooking(SimpleContainer inventory) {
        ItemStack itemStack = inventory.getItem(SLOT_OUTPUT);

        return inventory.getItem(SLOT_OUTPUT).getCount() == 1
                && hasWaffleMold(inventory)
                && (itemIsRawWaffleBatter(itemStack.getItem()) || itemIsBatterMess(itemStack.getItem()) || itemIsWaffle(itemStack));
    }

    private static void cook(WaffleIronBlockEntity entity) {
        if (entity.progress == batteredMessTime) { // must be raw batter, turns to batter mess
            entity.itemHandler.setStackInSlot(SLOT_OUTPUT, new ItemStack(ModItemsInit.BATTER_MESS.get(), 1));
        } else if (entity.progress == cookedTime) { // must be batter mess, turns to waffle
            WaffleItem waffle = ((WaffleMoldItem) entity.itemHandler.getStackInSlot(SLOT_MOLD).getItem()).getWaffle();
            if (itemIsWaffleFilling(entity.itemHandler.getStackInSlot(SLOT_FILLING))) {
                Item filling = entity.itemHandler.extractItem(SLOT_FILLING, 1, false).getItem();
                ItemStack stack = waffle.addNbtDataFillingToItemStack(filling.getDescription().getString() + " filling");
                entity.itemHandler.setStackInSlot(SLOT_OUTPUT, stack);
            } else {
                entity.itemHandler.setStackInSlot(SLOT_OUTPUT, new ItemStack(waffle, 1));
            }
        } else if (entity.progress >= maxProgress) { // must be waffle, burns
            entity.itemHandler.setStackInSlot(SLOT_OUTPUT, new ItemStack(ModItemsInit.BURNT_CRISP.get(), 1));
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }
}
