package com.crystal.block;

import com.crystal.ImprovedCrystals;
import com.crystal.block.entities.EchoRodBlockEntity;
import com.crystal.block.entities.ModBlockEntityTypes;
import com.crystal.block.entities.WeatherDetectorBlockEntity;
import com.crystal.util.WeatherDetectionCycleEnum;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DaylightDetectorBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DaylightDetectorBlockEntity;
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
import org.jspecify.annotations.Nullable;

public class WeatherDetectorBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty IN_SNOW_BIOME = ModBlockProperties.IN_SNOW_BIOME;
    public static final BooleanProperty IN_HOT_BIOME = ModBlockProperties.IN_HOT_BIOME;
    public static final BooleanProperty IN_VALID_BIOME = ModBlockProperties.IN_VALID_BIOME;
    public static final EnumProperty<WeatherDetectionCycleEnum> WEATHER_DETECTION = ModBlockProperties.WEATHER_DETECTION_CYCLE;
    public static final MapCodec<WeatherDetectorBlock> CODEC = simpleCodec(WeatherDetectorBlock::new);

    public WeatherDetectorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(IN_SNOW_BIOME, false).setValue(WATERLOGGED, false).setValue(IN_HOT_BIOME, false).setValue(IN_VALID_BIOME, false).setValue(WEATHER_DETECTION, WeatherDetectionCycleEnum.CLEAR));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

        if(state.getValue(IN_VALID_BIOME))
        {
            var weatherState = toggleWeather(state);
            var newState = state.setValue(WEATHER_DETECTION, weatherState).setValue(POWERED, validPower(state, level));
            level.setBlockAndUpdate(pos, newState);
            level.playSound(player, pos, SoundEvents.COPPER_BULB_TURN_ON, SoundSource.BLOCKS);
            return InteractionResult.SUCCESS;
        }

        level.playSound(player, pos, SoundEvents.CRAFTER_FAIL, SoundSource.BLOCKS);
        return InteractionResult.SUCCESS;
    }

    protected int getSignal(final BlockState state, final BlockGetter level, final BlockPos pos, final Direction direction) {
        return state.getValue(POWERED) && state.getValue(IN_VALID_BIOME) ? 15 : 0;
    }

    @Override
    protected FluidState getFluidState(final BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    private static boolean validPower(BlockState state, Level level)
    {
        if(state.getValue(IN_VALID_BIOME)) {
            return !level.isRaining() && !level.isThundering() && state.getValue(WEATHER_DETECTION) == WeatherDetectionCycleEnum.CLEAR || level.isRaining() && !level.isThundering() && state.getValue(WEATHER_DETECTION) == WeatherDetectionCycleEnum.RAIN || level.isThundering() && state.getValue(WEATHER_DETECTION) == WeatherDetectionCycleEnum.THUNDER;
        }

        return false;
    }

    public @Nullable BlockState getStateForPlacement(final BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        var precipitation =  context.getLevel().getBiome(pos).value().getPrecipitationAt(pos, context.getLevel().getSeaLevel());
        Boolean isValid = context.getLevel().canHaveWeather();
        Boolean inSnow = precipitation == Biome.Precipitation.SNOW && isValid;
        Boolean isHot = precipitation == Biome.Precipitation.NONE && isValid;
        FluidState replacedFluidState = context.getLevel().getFluidState(pos);

        return (BlockState)this.defaultBlockState().setValue(WATERLOGGED, replacedFluidState.is(Fluids.WATER)).setValue(IN_SNOW_BIOME, inSnow).setValue(IN_HOT_BIOME, isHot).setValue(IN_VALID_BIOME, isValid).setValue(WEATHER_DETECTION, WeatherDetectionCycleEnum.CLEAR);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(IN_SNOW_BIOME);
        builder.add(WATERLOGGED);
        builder.add(IN_HOT_BIOME);
        builder.add(IN_VALID_BIOME);
        builder.add(WEATHER_DETECTION);
        builder.add(POWERED);
    }

    private WeatherDetectionCycleEnum toggleWeather(BlockState state)
    {
        if(state.getValue(WEATHER_DETECTION) == WeatherDetectionCycleEnum.CLEAR)
        {
            return WeatherDetectionCycleEnum.RAIN;
        }
        else if(state.getValue(WEATHER_DETECTION) == WeatherDetectionCycleEnum.RAIN)
        {
            return WeatherDetectionCycleEnum.THUNDER;
        }

        return WeatherDetectionCycleEnum.CLEAR;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Block.column((double)16.0F, (double)0.0F, (double)6.0F);
    }

    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(final Level level, final BlockState blockState, final BlockEntityType<T> type) {
        return !level.isClientSide() && level.dimensionType().hasSkyLight() && blockState.getValue(IN_VALID_BIOME) ? createTickerHelper(type, ModBlockEntityTypes.WEATHER_DETECTOR_ENTITY, WeatherDetectorBlock::tickEntity) : null;
    }

    private static void tickEntity(final Level level, final BlockPos blockPos, final BlockState blockState, final WeatherDetectorBlockEntity blockEntity) {
        if (blockState.getValue(POWERED) != validPower(blockState, level)) {
            level.setBlockAndUpdate(blockPos, blockState.setValue(POWERED, !blockState.getValue(POWERED)));
        }

    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new WeatherDetectorBlockEntity(worldPosition, blockState);
    }
}
