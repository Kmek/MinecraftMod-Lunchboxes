package com.kmek.minecafe.item.custom;

import com.kmek.minecafe.item.ModItemsInit;
import com.kmek.minecafe.item.registery.MarshmallowStickEnum;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MarshmallowStickItem extends Item {
    private final String tagKeyToastiness = "minecafe.toastiness";
    private final String tagKeyModel = "CustomModelData";

    public MarshmallowStickItem() {
        super(new Item.Properties().stacksTo(1)
                .food(new FoodProperties.Builder()
                        .nutrition(3)
                        .saturationMod(1.5f)
                        .build()));
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (!pLevel.isClientSide) {
            if (pLevel.nextSubTickCount() % 20 == 0) {
                if ((pIsSelected || pEntity instanceof Player && ((Player) pEntity).getOffhandItem() == pStack)) {
                    Player player = (Player) pEntity;
                    Entity camera = Minecraft.getInstance().getCameraEntity();
                    if (camera == null) return;

                    HitResult targetBlock = camera.pick(2.5D, 0.0F, false);
                    if (targetBlock.getType() == HitResult.Type.BLOCK) {
                        BlockPos blockpos = ((BlockHitResult) targetBlock).getBlockPos();
                        BlockState bState = pLevel.getBlockState(blockpos);
                        Block block = bState.getBlock();
                        if (block instanceof CampfireBlock && pStack.hasTag()) {
                            int toastiness = pStack.getTag().getInt(tagKeyToastiness);
                            int model = pStack.getTag().getInt(tagKeyModel);
                            if (toastiness >= MarshmallowStickEnum.values()[model].maxBurnTime()) {
                                if (model < (MarshmallowStickEnum.values().length - 1))
                                    setTagData(pStack, 0,  model + 1);
                                else {
                                    player.getInventory().removeItem(pSlotId, 1);
                                    player.getInventory().add(pSlotId, new ItemStack(ModItemsInit.BURNT_CRISP.get()));
                                }
                            } else {
                                setTagData(pStack, toastiness + 1, model);
                            }
                        } else {
                            resetToastiness(pStack);
                        }
                    }
                } else {
                    resetToastiness(pStack);
                }
            }
        }
    }

    private void resetToastiness(ItemStack pStack) {
        CompoundTag tag = pStack.getTag();
        if (tag != null) {
            tag.putInt(tagKeyToastiness, 0);
            pStack.setTag(tag);
        } else
            setTagData(pStack, 0, 0);
    }

    private void setTagData(ItemStack pStack, int toastiness, int model) {
        CompoundTag tag = new CompoundTag();
        tag.putInt(tagKeyToastiness, toastiness);
        tag.putInt(tagKeyModel, model);
        pStack.setTag(tag);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = new ItemStack(this);
        setTagData(stack, 0, 0);
        return stack;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> components, TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getTag();
        int model = tag == null ? 0 : pStack.getTag().getInt(tagKeyModel);
        if (model > 0)
            components.add(Component.literal(MarshmallowStickEnum.values()[model].displayName()).withStyle(ChatFormatting.YELLOW));
        super.appendHoverText(pStack, pLevel, components, pIsAdvanced);
    }

    @Override
    public void onCraftedBy(ItemStack pStack, Level pLevel, Player pPlayer) {
        super.onCraftedBy(pStack, pLevel, pPlayer);
    }
}
