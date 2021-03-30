package org.samo_lego.woodenfurnace.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.samo_lego.woodenfurnace.block_entity.WoodenFurnaceBlockEntity;

import java.util.Random;

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

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new WoodenFurnaceBlockEntity();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return super.getRenderType(state);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(LIT);
    }


    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
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
                    } else if (blockState.getMaterial().blocksMovement()) {
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
        return world.getChunk(pos) != null && world.getBlockState(pos).getMaterial().isBurnable();
    }


    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
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
            world.addParticle(ParticleTypes.FLAME, d + i, e + j, f + k, 0.0D, 0.0D, 0.0D);
        }
    }
}
