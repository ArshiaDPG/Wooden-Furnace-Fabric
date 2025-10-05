package org.samo_lego.woodenfurnace.common.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.samo_lego.woodenfurnace.common.block.WoodenFurnaceBlock;
import org.samo_lego.woodenfurnace.init.WFBlockEntityTypes;
import org.samo_lego.woodenfurnace.mixin.AbstractFurnaceBlockEntityMixinAccessor;


public class WoodenFurnaceBlockEntity extends AbstractFurnaceBlockEntity implements BlockEntityTicker<BlockEntity> {

    private byte turnToCoalCounter = 0;

    public WoodenFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(WFBlockEntityTypes.WOODEN_FURNACE, pos, state, RecipeType.SMELTING);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.woodenfurnace.wooden_furnace");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new FurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }



    @Override
    protected int getFuelTime(ItemStack fuel) {
        return super.getFuelTime(fuel) * 2;
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        tag.putByte("to_coal_counter", this.turnToCoalCounter);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.turnToCoalCounter = nbt.getByte("to_coal_counter");
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if(!world.isClient && isBurning()) {
            world.setBlockState(pos, state.with(WoodenFurnaceBlock.LIT, true), 3);
            if(world.random.nextInt(100) == 0) {
                if(++this.turnToCoalCounter > 120) {
                    world.setBlockState(pos, Blocks.COAL_BLOCK.getDefaultState());
                }
            }
        }
    }

    public boolean isBurning(){
        return ((AbstractFurnaceBlockEntityMixinAccessor) this).burning();
    }
}
