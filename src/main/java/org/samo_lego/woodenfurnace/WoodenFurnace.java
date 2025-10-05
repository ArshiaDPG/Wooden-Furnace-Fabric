package org.samo_lego.woodenfurnace;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import org.samo_lego.woodenfurnace.init.WFBlockEntityTypes;
import org.samo_lego.woodenfurnace.init.WFBlocks;

public class WoodenFurnace implements ModInitializer {

	public static final String MOD_ID = "woodenfurnace";


	@Override
	public void onInitialize() {

		WFBlocks.init();
		WFBlockEntityTypes.init();

		FlammableBlockRegistry.getDefaultInstance().add(WFBlocks.WOODEN_FURNACE, 1, 5);
		FuelRegistry.INSTANCE.add(WFBlocks.WOODEN_FURNACE, 800);
	}
}
