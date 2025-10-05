package org.samo_lego.woodenfurnace.init;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.samo_lego.woodenfurnace.WoodenFurnace;
import org.samo_lego.woodenfurnace.common.block.WoodenFurnaceBlock;

import static net.minecraft.block.AbstractFurnaceBlock.LIT;

public class WFBlocks {
    public static Block createBlock(String name, Block block){
        return Registry.register(Registries.BLOCK, new Identifier(WoodenFurnace.MOD_ID, name), block);
    }
    public static BlockItem createBlockItem(String name, Block block){
        return Registry.register(Registries.ITEM, new Identifier(WoodenFurnace.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    public static Block createBlockWithItem(String name, Block block){
        createBlockItem(name, block);
        return createBlock(name, block);
    }


    
    public static final Block WOODEN_FURNACE = createBlockWithItem("wooden_furnace", new WoodenFurnaceBlock(FabricBlockSettings
            .create()
            .mapColor(MapColor.BROWN)
            .strength(4.0f)
            .burnable()
            .instrument(Instrument.BASEDRUM)
            .sounds(BlockSoundGroup.WOOD)
            .luminance(state -> state.get(LIT) ? 10 : 0)));


    public static void init(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.addBefore(Items.FURNACE, WOODEN_FURNACE));
    }
}
