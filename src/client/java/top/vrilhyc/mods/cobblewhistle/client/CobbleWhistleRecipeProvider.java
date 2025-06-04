package top.vrilhyc.mods.cobblewhistle.client;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import top.vrilhyc.mods.cobblewhistle.IdHelper;
import top.vrilhyc.mods.cobblewhistle.item.WhistleItems;

import java.util.concurrent.CompletableFuture;

public class CobbleWhistleRecipeProvider extends FabricRecipeProvider {
    public CobbleWhistleRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    /**
     * ULTRA RARE TREAT: Rare Treat + Nether Star
     * RARE TREAT : Uncommon Treat + 8 Golden Carrots
     * UNCOMMON TREAT: Common Treat + 4 Glistening Melon
     * COMMON TREAT: Potato + Carrot + Beetroot + Melon Slice + Bowl
     * @param recipeOutput
     */
    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        new ShapedRecipeBuilder(RecipeCategory.MISC, WhistleItems.WHISTLE,1)
                .define('a',Items.GOLD_INGOT)
                .define('b',Items.NETHERITE_SCRAP)
                .define('c',Items.DIAMOND)
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .unlockedBy("any", FabricRecipeProvider.has(Items.GOLD_INGOT))
                .save(recipeOutput, IdHelper.modLoc("whistle"));
    }
}
