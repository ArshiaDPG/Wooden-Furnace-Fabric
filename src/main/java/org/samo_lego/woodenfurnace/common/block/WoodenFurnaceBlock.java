package org.samo_lego.woodenfurnace.common.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import org.samo_lego.woodenfurnace.common.block.entity.WoodenFurnaceBlockEntity;
import org.samo_lego.woodenfurnace.init.WFBlockEntityTypes;

public class WoodenFurnaceBlock extends AbstractFurnaceBlock {


    public WoodenFurnaceBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof WoodenFurnaceBlockEntity) {
            player.openHandledScreen((NamedScreenHandlerFactory) blockEntity);
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WoodenFurnaceBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return super.getRenderType(state);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(LIT);
    }

    private static boolean isNearWater(World world, BlockPos pos) {
        BlockPos.Mutable mutable = pos.mutableCopy();
        Direction[] directions = Direction.values();

        for(Direction direction : directions) {
            BlockState blockState = world.getBlockState(mutable);
            if (blockState.getFluidState().isIn(FluidTags.WATER)) {
                return true;
            }
            mutable.set(pos, direction);
        }
        return false;
    }


    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        if(world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK) && !isNearWater(world, pos)) {
            int i = random.nextInt(3);
            if (i > 0) {
                for(int j = 0; j < i; ++j) {
                    pos = pos.add(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
                    if (!world.canSetBlock(pos)) {
                        return;
                    }

                    BlockState blockState = world.getBlockState(pos);
                    if (blockState.isAir()) {
                        if (this.canLightFire(world, pos)) {
                            world.setBlockState(pos, AbstractFireBlock.getState(world, pos));
                            return;
                        }
                    } else if (blockState.blocksMovement()) {
                        return;
                    }
                }
            } else {
                for(int k = 0; k < 3; ++k) {
                    BlockPos blockPos = pos.add(random.nextInt(3) - 1, -1, random.nextInt(3) - 1);
                    if (!world.canSetBlock(blockPos)) {
                        return;
                    }

                    if (world.isAir(blockPos.up()) && this.hasBurnableBlock(world, blockPos)) {
                        world.setBlockState(blockPos.up(), AbstractFireBlock.getState(world, blockPos));
                    }
                }
            }

        }
    }

    private boolean canLightFire(WorldView world, BlockPos pos) {
        Direction[] var3 = Direction.values();

        for(Direction direction : var3) {
            if(this.hasBurnableBlock(world, pos.offset(direction))) {
                return true;
            }
        }

        return false;
    }

    private boolean hasBurnableBlock(WorldView world, BlockPos pos) {
        return world.getChunk(pos) != null && world.getBlockState(pos).isBurnable();
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return FurnaceBlock.checkType(world, type, WFBlockEntityTypes.WOODEN_FURNACE);
    }


    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        if (state.get(LIT)) {
            double d = (double)pos.getX() + 0.5D;
            double e = pos.getY();
            double f = (double)pos.getZ() + 0.5D;
            if (random.nextDouble() < 0.1D) {
                world.playSound(d, e, f, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = state.get(FACING);
            Direction.Axis axis = direction.getAxis();
            double h = random.nextDouble() * 0.6D - 0.3D;
            double i = axis == Direction.Axis.X ? (double)direction.getOffsetX() * 0.52D : h;
            double j = random.nextDouble() * 6.0D / 16.0D;
            double k = axis == Direction.Axis.Z ? (double)direction.getOffsetZ() * 0.52D : h;
            world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0D, 0.0D, 0.0D);
            world.addParticle(world.getBlockState(pos.down()).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS) ? ParticleTypes.SOUL : ParticleTypes.FLAME, d + i, e + j, f + k, 0.0D, 0.0D, 0.0D);
        }
    }
}
