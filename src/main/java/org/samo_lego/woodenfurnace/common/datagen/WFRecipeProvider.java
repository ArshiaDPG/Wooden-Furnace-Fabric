package org.samo_lego.woodenfurnace.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.VanillaRecipeProvider;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import org.samo_lego.woodenfurnace.init.WFBlocks;

import java.util.function.Consumer;

public class WFRecipeProvider extends FabricRecipeProvider {

    public WFRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, WFBlocks.WOODEN_FURNACE)
                .input('#', ItemTags.LOGS)
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .criterion("has_log", VanillaRecipeProvider.conditionsFromTag(ItemTags.LOGS)).offerTo(exporter);

    }
}
