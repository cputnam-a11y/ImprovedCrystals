package com.crystal.block.entities;

import com.crystal.ImprovedCrystals;
import com.crystal.block.EchoRodBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CalibratedSculkSensorBlockEntity;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class EchoRodBlockEntity extends SculkSensorBlockEntity {


    public EchoRodBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntityTypes.ECHO_ROD_ENTITY, worldPosition, blockState);
    }

    public VibrationSystem.User createVibrationUser() {
        return new EchoRodBlockEntity.VibrationUser(this.getBlockPos());
    }

    protected class VibrationUser extends SculkSensorBlockEntity.VibrationUser {
        public VibrationUser(final BlockPos blockPos) {
            Objects.requireNonNull(EchoRodBlockEntity.this);
            super(blockPos);
        }

        public int getListenerRadius() {
            return 16;
        }

        @Override
        public boolean canReceiveVibration(final ServerLevel level, final BlockPos pos, final Holder<GameEvent> event, final GameEvent.@Nullable Context context) {
            if (!pos.equals(this.blockPos) || !event.is(GameEvent.BLOCK_DESTROY) && !event.is(GameEvent.BLOCK_PLACE)) {
                return VibrationSystem.getGameEventFrequency(event) != 0 && EchoRodBlock.canRecieveNewPower(EchoRodBlockEntity.this.getBlockState());
            } else {
                return false;
            }
        }

        @Override
        public void onReceiveVibration(ServerLevel level, BlockPos pos, Holder<GameEvent> event, @Nullable Entity sourceEntity, @Nullable Entity projectileOwner, float receivingDistance) {
            BlockState state = EchoRodBlockEntity.this.getBlockState();
            int calculatedPower = VibrationSystem.getRedstoneStrengthForDistance(receivingDistance, this.getListenerRadius());

            Block echoRodBlock = state.getBlock();
            if (echoRodBlock instanceof EchoRodBlock) {
                EchoRodBlock echoRod = (EchoRodBlock) echoRodBlock;
                echoRod.setPower(level, state, this.blockPos, calculatedPower, null);
            }
        }
    }
}
