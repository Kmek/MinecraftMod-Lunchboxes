package com.kmek.minecafe.block.custom.crop;

import com.kmek.minecafe.item.ModItemsInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class CropTreeTopBlock extends BushBlock implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_15;

    private Block bottomBlock;
    private String fruitName;
    private Item fruit = null;
    private final int resetAge;

    public CropTreeTopBlock(Block bottomBlock, String fruitName, int resetAge, BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.bottomBlock = bottomBlock;
        this.fruitName = fruitName;
        this.resetAge = resetAge;
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
    }

    public CropTreeTopBlock(Block bottomBlock, Item fruit, int resetAge, BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.bottomBlock = bottomBlock;
        this.fruit = fruit;
        this.resetAge = resetAge;
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
    }

    public Item getFruit() {
        if (fruit == null) {
            Optional<RegistryObject<Item>> opt = ModItemsInit.ITEMS.getEntries().stream()
//                    .peek(entry -> System.out.println(entry.get().getDescriptionId()))
                    .filter(entry -> entry.get().getDescriptionId().equals(fruitName))
                    .findFirst();
            fruit = opt.isPresent() ? opt.get().get() : Items.STICK;
        }
        return fruit;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pState.getValue(AGE) < 15;
    }
    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }
    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        this.grow(pLevel, pPos, pState, 2 + pLevel.random.nextInt(2));
    }

    // Grow the crop
    private void grow(ServerLevel pLevel, BlockPos pPos, BlockState pState, int toAdd) {
        int age = pState.getValue(AGE);
        if (age < 15) {
            pLevel.setBlock(pPos, this.defaultBlockState().setValue(AGE, Math.min(15, age + toAdd)), 2);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return pState.getValue(AGE) < 15;
    }
    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (pLevel.getRawBrightness(pPos.above(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt(5) == 0)) {
            grow(pLevel, pPos, pState, 1);
        }
    }

    // Right click to pick, move age back
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        int age = pState.getValue(AGE);
        if (age < 15 && pPlayer.getItemInHand(pHand).is(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        } else if (age == 15) {
            int j = 1 + pLevel.random.nextInt(2);
            popResource(pLevel, pPos, new ItemStack(getFruit(), j));
            pLevel.playSound(null, pPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);
            BlockState blockstate = pState.setValue(AGE, Integer.valueOf(resetAge));
            pLevel.setBlock(pPos, blockstate, 2);
            pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pPlayer, blockstate));
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        } else {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
    }

    // Will break if bottom block is broken
    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.above()).is(bottomBlock);
    }

    // Makes the block slow to walk through
    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity instanceof LivingEntity && pEntity.getType() != EntityType.FOX && pEntity.getType() != EntityType.BEE) {
            pEntity.makeStuckInBlock(pState, new Vec3(1F, 1D, 1F));
        }
    }
}
