package uk.joshiejack.husbandry.data;

import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.PlayerEntityInteractionTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.block.HusbandryBlocks;
import uk.joshiejack.husbandry.item.HusbandryItems;
import uk.joshiejack.penguinlib.item.PenguinItems;
import uk.joshiejack.penguinlib.util.PenguinTags;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class HusbandryRecipes extends RecipeProvider {
    public HusbandryRecipes(DataGenerator generator) {
        super(generator);
    }

    private ResourceLocation rl (String name) {
        return new ResourceLocation(Husbandry.MODID, name);
    }

    private void cook(@Nonnull Consumer<IFinishedRecipe> consumer, Item input, Item output, float experience) {
        String name = input.getRegistryName().getPath();
        CookingRecipeBuilder.cooking(Ingredient.of(input), output, experience, 100, IRecipeSerializer.SMOKING_RECIPE).unlockedBy("has_" + name, has(input)).save(consumer, rl(name + "_smoking"));
        CookingRecipeBuilder.cooking(Ingredient.of(input), output, experience, 600, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_" + name, has(input)).save(consumer, rl(name + "_campfire"));
        CookingRecipeBuilder.smelting(Ingredient.of(input), output, experience, 200).unlockedBy("has_" + name, has(input)).save(consumer, rl(name + "_smelting"));
    }

    @Override
    protected void buildShapelessRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(HusbandryItems.BRUSH::get).define('W', Tags.Items.CROPS_WHEAT).define('S', Tags.Items.RODS_WOODEN).pattern(" W").pattern("S ").unlockedBy("has_wheat", has(Items.WHEAT)).save(consumer, rl("brush"));
        ShapedRecipeBuilder.shaped(HusbandryItems.MIRACLE_POTION::get).define('A', PenguinTags.CROPS_APPLE).define('C', Tags.Items.CROPS_CARROT)
                .define('B', Items.GLASS_BOTTLE).define('W', Tags.Items.CROPS_WHEAT).define('F', PenguinTags.RAW_FISHES).pattern(" A ").pattern("CBW").pattern(" F ")
                .unlockedBy("entity_interaction", new PlayerEntityInteractionTrigger.Instance(EntityPredicate.AndPredicate.ANY, ItemPredicate.ANY, EntityPredicate.AndPredicate.ANY)).save(consumer, rl("miracle_potion"));
        ShapedRecipeBuilder.shaped(HusbandryItems.SICKLE::get).define('S', Items.FLINT).define('W', Tags.Items.RODS_WOODEN).pattern("SS").pattern(" W").pattern("W ").unlockedBy("has_cobble", has(Tags.Items.COBBLESTONE)).save(consumer, rl("stone_sickle"));
        ShapelessRecipeBuilder.shapeless(HusbandryItems.BIRD_FEED.get(), 12).requires(Tags.Items.SEEDS).requires(Tags.Items.SEEDS).requires(Tags.Items.SEEDS).unlockedBy("has_seeds", has(Tags.Items.SEEDS)).save(consumer, rl("bird_feed"));
        ShapelessRecipeBuilder.shapeless(HusbandryItems.CAT_FOOD.get(), 8).requires(ItemTags.FISHES).requires(Items.CHICKEN).unlockedBy("has_fish", has(Tags.Items.SEEDS)).save(consumer, rl("cat_food"));
        ShapelessRecipeBuilder.shapeless(HusbandryItems.DOG_FOOD.get(), 8).requires(Items.BEEF).requires(Items.BONE).unlockedBy("has_beef", has(Items.BEEF)).save(consumer, rl("dog_food"));
        ShapelessRecipeBuilder.shapeless(HusbandryItems.RABBIT_FOOD.get(), 12).requires(Tags.Items.CROPS_CARROT).requires(Tags.Items.CROPS_CARROT).requires(Tags.Items.CROPS_CARROT).unlockedBy("has_carrot", has(Tags.Items.CROPS_CARROT)).save(consumer, rl("rabbit_food"));
        ShapelessRecipeBuilder.shapeless(HusbandryItems.SLOP.get(), 4).requires(PenguinTags.BREADS).requires(PenguinTags.CROPS_MELON).requires(Tags.Items.CROPS_WHEAT).unlockedBy("has_bread", has(PenguinTags.BREADS)).save(consumer, rl("slop"));
        ShapelessRecipeBuilder.shapeless(HusbandryItems.MUG_OF_MILK.get(), 3).requires(Items.MILK_BUCKET).requires(PenguinItems.MUG.get()).requires(PenguinItems.MUG.get()).requires(PenguinItems.MUG.get()).unlockedBy("has_milk", has(Items.MILK_BUCKET)).save(consumer, "mug_of_milk");
        cook(consumer, HusbandryItems.MUG_OF_MILK.get(), HusbandryItems.HOT_MILK.get(), 0.3F);
        ShapelessRecipeBuilder.shapeless(HusbandryItems.DINNER_ROLL.get()).requires(PenguinTags.BREADS).requires(HusbandryItemTags.BUTTER).requires(Tags.Items.EGGS).unlockedBy("has_eggs", has(Tags.Items.EGGS)).save(consumer, rl("dinner_roll"));
        cook(consumer, HusbandryItems.BOILED_EGG.get(), Items.EGG, 0.3F);
        ShapelessRecipeBuilder.shapeless(HusbandryItems.BUTTER.get(), 4).requires(Items.MILK_BUCKET).requires(Items.PAPER).unlockedBy("has_milk", has(Items.MILK_BUCKET)).save(consumer, rl("butter"));
        ShapelessRecipeBuilder.shapeless(HusbandryItems.ICE_CREAM.get()).requires(Items.MILK_BUCKET).requires(Tags.Items.EGGS).requires(PenguinItems.DEEP_BOWL.get()).unlockedBy("has_milk", has(Items.MILK_BUCKET)).save(consumer, "ice_cream");
        ShapedRecipeBuilder.shaped(HusbandryBlocks.NEST.get()).define('P', ItemTags.PLANKS).define('H', Blocks.HAY_BLOCK).pattern("PHP").pattern("PPP").unlockedBy("has_wood", has(ItemTags.PLANKS)).save(consumer, rl("nest"));
        ShapedRecipeBuilder.shaped(HusbandryBlocks.INCUBATOR.get()).define('I', Tags.Items.INGOTS_IRON).define('P', ItemTags.PLANKS).define('H', Blocks.HAY_BLOCK).pattern("IHI").pattern("PPP").unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON)).save(consumer, rl("incubator"));
        ShapedRecipeBuilder.shaped(HusbandryBlocks.FEEDING_TRAY.get()).define('P', ItemTags.PLANKS).define('S', ItemTags.WOODEN_SLABS).pattern("PSP").unlockedBy("has_wood", has(ItemTags.PLANKS)).save(consumer, rl("feeding_tray"));
        ShapedRecipeBuilder.shaped(HusbandryBlocks.BOWL.get()).define('S', ItemTags.WOODEN_SLABS).pattern(" S ").pattern("S S").pattern(" S ").unlockedBy("has_wood", has(ItemTags.PLANKS)).save(consumer, rl("bowl"));
        ShapelessRecipeBuilder.shapeless(HusbandryItems.ANIMAL_TRACKER.get()).requires(Items.PAPER).requires(Items.INK_SAC).requires(HusbandryItemTags.ANIMAL_PRODUCT).unlockedBy("has_paper", has(Items.PAPER)).save(consumer, "animal_tracker");
//        if (HusbandryConfig.enableFeederRecipes) {
//            helper.shapedRecipe("feeder_trough", TROUGH.getStackFromEnum(BlockTrough.Section.SINGLE), "P P", "PPP", "L L", 'P', "plankWood", 'L', "logWood");
//        }
    }
}
