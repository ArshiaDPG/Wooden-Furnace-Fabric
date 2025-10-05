package org.samo_lego.woodenfurnace.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import org.samo_lego.woodenfurnace.init.WFBlocks;

public class WFLanguageProvider extends FabricLanguageProvider {
    public WFLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(WFBlocks.WOODEN_FURNACE, "Wooden Furnace");
        translationBuilder.add("container.woodenfurnace.wooden_furnace", "Wooden Furnace");
    }
}
