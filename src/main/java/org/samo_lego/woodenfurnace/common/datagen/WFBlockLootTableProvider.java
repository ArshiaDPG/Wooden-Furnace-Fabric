package org.samo_lego.woodenfurnace.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import org.samo_lego.woodenfurnace.init.WFBlocks;

public class WFBlockLootTableProvider extends FabricBlockLootTableProvider {
    public WFBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(WFBlocks.WOODEN_FURNACE, this::nameableContainerDrops);
    }
}
