package org.samo_lego.woodenfurnace.block_entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.samo_lego.woodenfurnace.mixin.AbstractFurnaceBlockEntityMixinAccessor;

import static org.samo_lego.woodenfurnace.WoodenFurnaceInit.FURNACE_BLOCK_ENTITY;

public class WoodenFurnaceBlockEntity extends AbstractFurnaceBlockEntity {

    private byte turnToCoalCounter = 0;

    public WoodenFurnaceBlockEntity() {
        super(FURNACE_BLOCK_ENTITY, RecipeType.SMELTING);
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.woodenfurnace.wooden_furnace");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new FurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public void tick() {
        super.tick();
        assert this.world != null;
        if(!this.world.isClient && ((AbstractFurnaceBlockEntityMixinAccessor) this).burning()) {
            if(this.world.random.nextInt(100) == 0) {
                if(++this.turnToCoalCounter > 120) {
                    this.world.setBlockState(this.pos, Blocks.COAL_BLOCK.getDefaultState());
                }
            }
        }
    }

    @Override
    protected int getCookTime() {
        return this.world.getRecipeManager().getFirstMatch(this.recipeType, this, this.world).map(AbstractCookingRecipe::getCookTime).orElse(200) * 2;
    }


    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putByte("to_coal_counter", this.turnToCoalCounter);

        return tag;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.turnToCoalCounter = tag.getByte("to_coal_counter");
    }
}
