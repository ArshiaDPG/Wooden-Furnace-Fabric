package org.samo_lego.woodenfurnace;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.samo_lego.woodenfurnace.common.datagen.WFBlockLootTableProvider;
import org.samo_lego.woodenfurnace.common.datagen.WFLanguageProvider;
import org.samo_lego.woodenfurnace.common.datagen.WFModelProvider;
import org.samo_lego.woodenfurnace.common.datagen.WFRecipeProvider;

public class WoodenFurnaceDatagen implements DataGeneratorEntrypoint {


    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(WFRecipeProvider::new);
        pack.addProvider(WFBlockLootTableProvider::new);
        pack.addProvider(WFModelProvider::new);
        pack.addProvider(WFLanguageProvider::new);
    }
}
