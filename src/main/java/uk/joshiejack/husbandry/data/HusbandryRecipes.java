package uk.joshiejack.husbandry.data;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.PlayerInteractTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.data.builders.IncubatorRecipeBuilder;
import uk.joshiejack.husbandry.world.block.HusbandryBlocks;
import uk.joshiejack.husbandry.world.item.HusbandryItems;
import uk.joshiejack.penguinlib.util.PenguinTags;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;

public class HusbandryRecipes extends RecipeProvider {
    public HusbandryRecipes(PackOutput packOutput) {
        super(packOutput);
    }

    private ResourceLocation rl (String name) {
        return new ResourceLocation(Husbandry.MODID, name);
    }

    private void cook(@Nonnull RecipeOutput consumer, Item input, Item output, float experience) {
        String name = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(input)).getPath();
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(input), RecipeCategory.FOOD, output, experience, 100).unlockedBy("has_" + name, has(input)).save(consumer, rl(name + "_smoking"));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(input), RecipeCategory.FOOD, output, experience, 600).unlockedBy("has_" + name, has(input)).save(consumer, rl(name + "_campfire"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.FOOD, output, experience, 200).unlockedBy("has_" + name, has(input)).save(consumer, rl(name + "_smelting"));
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, HusbandryItems.BRUSH::get).define('W', Tags.Items.CROPS_WHEAT).define('S', Tags.Items.RODS_WOODEN).pattern(" W").pattern("S ").unlockedBy("has_wheat", has(Items.WHEAT)).save(consumer, rl("brush"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, HusbandryItems.MIRACLE_POTION::get).define('A', PenguinTags.CROPS_APPLE).define('C', Tags.Items.CROPS_CARROT)
                .define('B', Items.GLASS_BOTTLE).define('W', Tags.Items.CROPS_WHEAT).define('F', PenguinTags.RAW_FISHES).pattern(" A ").pattern("CBW").pattern(" F ")
                .unlockedBy("entity_interaction", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), Optional.empty())).save(consumer, rl("miracle_potion"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, HusbandryItems.SICKLE::get).define('S', Items.COBBLESTONE).define('W', Tags.Items.RODS_WOODEN).pattern("SS").pattern(" W").pattern("W ").unlockedBy("has_cobble", has(Tags.Items.COBBLESTONE)).save(consumer, rl("stone_sickle"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, HusbandryItems.BIRD_FEED.get(), 12).requires(Tags.Items.SEEDS).requires(Tags.Items.SEEDS).requires(Tags.Items.SEEDS).unlockedBy("has_seeds", has(Tags.Items.SEEDS)).save(consumer, rl("bird_feed"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, HusbandryItems.SLOP.get(), 4).requires(PenguinTags.BREAD).requires(PenguinTags.CROPS_MELON).requires(Tags.Items.CROPS_WHEAT).unlockedBy("has_bread", has(PenguinTags.BREAD)).save(consumer, rl("slop"));
        //TODO: Move to gastronomy ? ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, HusbandryItems.MUG_OF_MILK.get(), 3).requires(Items.MILK_BUCKET).requires(PenguinItems.MUG.get()).requires(PenguinItems.MUG.get()).requires(PenguinItems.MUG.get()).unlockedBy("has_milk", has(Items.MILK_BUCKET)).save(consumer, rl("mug_of_milk"));
        cook(consumer, HusbandryItems.MUG_OF_MILK.get(), HusbandryItems.HOT_MILK.get(), 0.3F);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, HusbandryItems.DINNER_ROLL.get()).requires(PenguinTags.BREAD).requires(HusbandryItemTags.BUTTER).requires(Tags.Items.EGGS).unlockedBy("has_eggs", has(Tags.Items.EGGS)).save(consumer, rl("dinner_roll"));
        cook(consumer, Items.EGG, HusbandryItems.BOILED_EGG.get(), 0.3F);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, HusbandryItems.BUTTER.get(), 4).requires(Items.MILK_BUCKET).requires(Items.PAPER).unlockedBy("has_milk", has(Items.MILK_BUCKET)).save(consumer, rl("butter"));
        //TODO: Move to gastronomy ?ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, HusbandryItems.ICE_CREAM.get()).requires(Items.MILK_BUCKET).requires(Tags.Items.EGGS).requires(PenguinItems.DEEP_BOWL.get()).unlockedBy("has_milk", has(Items.MILK_BUCKET)).save(consumer, rl("ice_cream"));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, HusbandryBlocks.NEST.get()).define('P', ItemTags.PLANKS).define('H', Blocks.HAY_BLOCK).pattern("PHP").pattern("PPP").unlockedBy("has_wood", has(ItemTags.PLANKS)).save(consumer, rl("nest"));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, HusbandryBlocks.INCUBATOR.get()).define('I', Tags.Items.INGOTS_IRON).define('P', ItemTags.PLANKS).define('H', Blocks.HAY_BLOCK).pattern("IHI").pattern("PPP").unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON)).save(consumer, rl("incubator"));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, HusbandryBlocks.FEEDING_TRAY.get()).define('P', ItemTags.PLANKS).define('S', ItemTags.WOODEN_SLABS).pattern("PSP").unlockedBy("has_wood", has(ItemTags.PLANKS)).save(consumer, rl("feeding_tray"));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, HusbandryBlocks.BOWL.get()).define('S', ItemTags.WOODEN_SLABS).pattern(" S ").pattern("S S").pattern(" S ").unlockedBy("has_wood", has(ItemTags.PLANKS)).save(consumer, rl("bowl"));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, HusbandryBlocks.TROUGH.get()).define('P', ItemTags.PLANKS).define('L', ItemTags.LOGS).pattern("P P").pattern("PPP").pattern("L L").unlockedBy("has_wood", has(ItemTags.LOGS)).save(consumer, rl("trough"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, HusbandryItems.MOB_TRACKER.get()).requires(Items.PAPER).requires(Items.INK_SAC).requires(HusbandryItemTags.MOB_PRODUCT).unlockedBy("has_paper", has(Items.PAPER)).save(consumer, rl("mob_tracker"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.NAME_TAG).requires(Tags.Items.STRING).requires(Items.PAPER).unlockedBy("has_paper", has(Items.PAPER)).save(consumer, rl("name_tag"));
        IncubatorRecipeBuilder.incubate(Ingredient.of(Items.EGG), EntityType.CHICKEN, 1, 1).save(consumer, rl("chicken"));
    }
}
