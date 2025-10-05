package org.samo_lego.woodenfurnace.init;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.samo_lego.woodenfurnace.WoodenFurnace;
import org.samo_lego.woodenfurnace.common.block.entity.WoodenFurnaceBlockEntity;

public class WFBlockEntityTypes {

    public static BlockEntityType<WoodenFurnaceBlockEntity> WOODEN_FURNACE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            new Identifier(WoodenFurnace.MOD_ID, "wooden_furnace"),
            BlockEntityType.Builder.create(WoodenFurnaceBlockEntity::new, WFBlocks.WOODEN_FURNACE).build(null));

    public static void init(){

    }
}
