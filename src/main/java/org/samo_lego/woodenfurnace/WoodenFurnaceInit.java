package org.samo_lego.woodenfurnace;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.samo_lego.woodenfurnace.block.WoodenFurnaceBlock;
import org.samo_lego.woodenfurnace.block_entity.WoodenFurnaceBlockEntity;

public class WoodenFurnaceInit implements ModInitializer {


	public static final String MODID = "woodenfurnace";

	public static final WoodenFurnaceBlock WOODEN_FURNACE = new WoodenFurnaceBlock(FabricBlockSettings
			.of(Material.WOOD)
			.strength(4.0f)
			.breakByHand(true)
			.breakByTool(FabricToolTags.AXES)
			.sounds(BlockSoundGroup.WOOD)
	);
	public static BlockEntityType<WoodenFurnaceBlockEntity> FURNACE_BLOCK_ENTITY;

	@Override
	public void onInitialize() {
		Identifier woodenFurnaceId = new Identifier(MODID, "wooden_furnace");
		Registry.register(Registry.BLOCK, woodenFurnaceId, WOODEN_FURNACE);
		Registry.register(Registry.ITEM, woodenFurnaceId, new BlockItem(WOODEN_FURNACE, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
		FURNACE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, woodenFurnaceId, BlockEntityType.Builder.create(WoodenFurnaceBlockEntity::new, WOODEN_FURNACE).build(null));

		FlammableBlockRegistry.getDefaultInstance().add(WOODEN_FURNACE, 1, 5);
	}
}
