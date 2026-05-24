package com.crystal.block;

import com.crystal.ImprovedCrystals;
import com.crystal.block.entities.EchoRodBlockEntity;
import com.crystal.block.entities.ModBlockEntityTypes;
import com.crystal.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.awt.*;
import java.util.Map;
import java.util.Random;

public class EchoRodBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final IntegerProperty POWER = BlockStateProperties.POWER;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    private static final Map<Direction.Axis, VoxelShape> SHAPES = Shapes.rotateAllAxis(Block.cube((double)4.0F, (double)4.0F, (double)16.0F));

    protected EchoRodBlock(Properties properties) {

        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y).setValue(WATERLOGGED, false).setValue(LIT, false).setValue(POWER, 0));
    }

    public @Nullable BlockState getStateForPlacement(final BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        FluidState replacedFluidState = context.getLevel().getFluidState(pos);
        return (BlockState)this.defaultBlockState().setValue(WATERLOGGED, replacedFluidState.is(Fluids.WATER)).setValue(AXIS, context.getClickedFace().getAxis()).setValue(LIT, false);
    }

    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(final Level level, final BlockState blockState, final BlockEntityType<T> type) {
        return !level.isClientSide() ? createTickerHelper(type, ModBlockEntityTypes.ECHO_ROD_ENTITY, (innerLevel, pos, state, entity) -> VibrationSystem.Ticker.tick(innerLevel, entity.getVibrationData(), entity.getVibrationUser())) : null;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        if(itemStack.is(Items.ECHO_SHARD) && state.getValue(BlockStateProperties.POWER) > 0)
        {
            ItemStack stack = new ItemStack(Holder.direct(ModItems.RESONATING_ECHO_SHARD), 1);
            stack.applyComponentsAndValidate(player.getItemInHand(hand).getComponentsPatch());
            stack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(BlockStateProperties.POWER, state.getValue(BlockStateProperties.POWER)));
            itemStack.shrink(1);
            player.addItem(stack);
            dePower(level, pos, state, player);
            return InteractionResult.SUCCESS;
        }
        else if(itemStack.is(ModItems.RESONATING_ECHO_SHARD) && state.getValue(BlockStateProperties.POWER) <= 0)
        {
            ItemStack stack = new ItemStack(Items.ECHO_SHARD);
            stack.applyComponentsAndValidate(player.getItemInHand(hand).getComponentsPatch());
            setPower(level, state, pos, itemStack.get(DataComponents.BLOCK_STATE).get(BlockStateProperties.POWER), player);
            itemStack.shrink(1);
            player.addItem(stack);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    protected FluidState getFluidState(final BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(state.getValue(BlockStateProperties.POWER) > 0)
        {
            dePower(level, pos, state, null);
        }
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EchoRodBlockEntity(pos, state);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
        builder.add(POWER);
        builder.add(WATERLOGGED);
        builder.add(AXIS);
    }

    protected boolean isSignalSource(final BlockState state) {
        return true;
    }

    protected int getSignal(final BlockState state, final BlockGetter level, final BlockPos pos, final Direction direction) {
        return (Integer)state.getValue(POWER);
    }

    private static void updateNeighbours(final Level level, final BlockPos pos, final BlockState state) {
        Block block = state.getBlock();
        level.updateNeighborsAt(pos, block);
        level.updateNeighborsAt(pos.below(), block);
    }

    public void animateTick(final BlockState state, final Level level, final BlockPos pos, final RandomSource random) {
        if (state.getValue(BlockStateProperties.LIT)) {
            Direction dir = Direction.getRandom(random);
            if (dir != Direction.UP && dir != Direction.DOWN) {
                double x = (double)pos.getX() + (double)0.5F + (dir.getStepX() == 0 ? (double)0.5F - random.nextDouble() : (double)dir.getStepX() * 0.6);
                double y = (double)pos.getY() + (double)0.25F;
                double z = (double)pos.getZ() + (double)0.5F + (dir.getStepZ() == 0 ? (double)0.5F - random.nextDouble() : (double)dir.getStepZ() * 0.6);
                double ya = (double)random.nextFloat() * 0.04;
                level.addParticle(DustColorTransitionOptions.SCULK_TO_REDSTONE, x, y, z, (double)0.0F, ya, (double)0.0F);
            }
        }
    }

    public void setPower(final Level level, final BlockState state, final BlockPos pos,  final int power, Player player)
    {
        if(state.getValue(BlockStateProperties.POWER) <= 0)
        {
            level.setBlock(pos, state.setValue(POWER, power).setValue(LIT, true), 3);
            playSound(level, pos, player);
            level.scheduleTick(pos, state.getBlock(), 1200);
            updateNeighbours(level, pos, state);
        }
    }

    public static void dePower(final Level level, final BlockPos pos, final BlockState state, Player player) {
        level.setBlock(pos, state.setValue(POWER, 0).setValue(LIT, false), 3);
        level.scheduleTick(pos, state.getBlock(), 10);
        playSound(level, pos, player);
        updateNeighbours(level, pos, state);
    }

    public static boolean canRecieveNewPower(final BlockState state)
    {
        return state.getValue(BlockStateProperties.POWER) <= 0;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return (VoxelShape)SHAPES.get(state.getValue(AXIS));
    }

    private static void playSound(Level level, BlockPos pos, Player player)
    {
        Random random = new Random();
        level.playSound(player,(double)pos.getX() + (double)0.5F, (double)pos.getY() + (double)0.5F, (double)pos.getZ() + (double)0.5F, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F);
    }
}
