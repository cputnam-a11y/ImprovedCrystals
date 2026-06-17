package com.crystal.block;

import com.crystal.ImprovedCrystals;
import com.crystal.block.entities.ModBlockEntityTypes;
import com.crystal.block.entities.MoonDetectorBlockEntity;
import com.crystal.block.entities.WeatherDetectorBlockEntity;
import com.crystal.util.WeatherDetectionCycleEnum;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.clock.ClockTimeMarkers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.MoonPhase;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.timeline.Timeline;
import net.minecraft.world.timeline.Timelines;
import org.jspecify.annotations.Nullable;

public class MoonDetectorBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty IN_VALID_DIMENSION = ModBlockProperties.IN_VALID_DIMENSION;
    public static final EnumProperty<MoonPhase> MOON_PHASE = ModBlockProperties.MOON_PHASE;
    public static final IntegerProperty POWER = BlockStateProperties.POWER;
    public static final MapCodec<WeatherDetectorBlock> CODEC = simpleCodec(WeatherDetectorBlock::new);

    protected MoonDetectorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false).setValue(WATERLOGGED, false).setValue(MOON_PHASE, MoonPhase.FULL_MOON).setValue(POWER, 0).setValue(IN_VALID_DIMENSION, false));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

        if(state.getValue(IN_VALID_DIMENSION))
        {
            var moonPhase = toggleMoonPhase(state);
            var newState = state.setValue(MOON_PHASE, MoonPhase.values()[moonPhase]).setValue(POWERED, validPower(state, level, pos)).setValue(POWER, getPower(level, pos, moonPhase));
            level.setBlockAndUpdate(pos, newState);
            level.playSound(player, pos, SoundEvents.COPPER_BULB_TURN_ON, SoundSource.BLOCKS);
            return InteractionResult.SUCCESS;
        }

        level.playSound(player, pos, SoundEvents.CRAFTER_FAIL, SoundSource.BLOCKS);
        return InteractionResult.SUCCESS;
    }

    private static boolean validPower(BlockState state, Level level, BlockPos pos)
    {
        return inValidDimnesion(level) &&  level.getEffectiveSkyBrightness(pos) <= 9;
    }

    private static boolean inValidDimnesion(Level level)
    {
        return level.dimensionType().skybox() == DimensionType.Skybox.OVERWORLD;
    }

    private static int getPower(Level level, BlockPos pos, int toggledPhase)
    {
        int moonPhase = level.environmentAttributes().getValue(EnvironmentAttributes.MOON_PHASE, pos).index();
        int dist = Mth.abs(toggledPhase - moonPhase) + 2;
        if(toggledPhase == moonPhase)
        {
            return 15;
        }
        else
        {
            return 15 - dist;
        }
    }

    private int toggleMoonPhase(BlockState state)
    {
        int phase = state.getValue(MOON_PHASE).index();

        if(phase < 7)
        {
            return phase += 1;
        }
        else
        {
            return 0;
        }
    }

    public @Nullable BlockState getStateForPlacement(final BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        FluidState replacedFluidState = context.getLevel().getFluidState(pos);

        return (BlockState)this.defaultBlockState().setValue(WATERLOGGED, replacedFluidState.is(Fluids.WATER)).setValue(IN_VALID_DIMENSION, inValidDimnesion(context.getLevel()));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MOON_PHASE);
        builder.add(WATERLOGGED);
        builder.add(POWERED);
        builder.add(POWER);
        builder.add(IN_VALID_DIMENSION);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Block.column((double)16.0F, (double)0.0F, (double)6.0F);
    }

    protected int getSignal(final BlockState state, final BlockGetter level, final BlockPos pos, final Direction direction) {
        return state.getValue(POWERED) ? state.getValue(POWER) : 0;
    }

    @Override
    protected FluidState getFluidState(final BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new MoonDetectorBlockEntity(worldPosition, blockState);
    }

    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(final Level level, final BlockState blockState, final BlockEntityType<T> type) {
        return !level.isClientSide() && level.dimensionType().hasSkyLight() && blockState.getValue(IN_VALID_DIMENSION) ? createTickerHelper(type, ModBlockEntityTypes.MOON_DETECTOR_ENTITY, MoonDetectorBlock::tickEntity) : null;
    }

    private static void tickEntity(final Level level, final BlockPos blockPos, final BlockState blockState, final MoonDetectorBlockEntity blockEntity) {
        if (blockState.getValue(POWERED) != validPower(blockState, level, blockPos)) {
            level.setBlockAndUpdate(blockPos, blockState.setValue(POWERED, !blockState.getValue(POWERED)).setValue(POWER, getPower(level, blockPos, blockState.getValue(MOON_PHASE).index())));
        }

    }
}
