package uk.joshiejack.husbandry.data.builders;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.husbandry.world.item.crafting.HusbandryRegistries;
import uk.joshiejack.husbandry.world.item.crafting.IncubatorRecipe;
import uk.joshiejack.penguinlib.data.generator.builder.SimplePenguinRegistryBuilder;

public class IncubatorRecipeBuilder extends SimplePenguinRegistryBuilder.EntityOutput<IncubatorRecipe> {
    private final int min;
    private final int max;

    public IncubatorRecipeBuilder(Ingredient ingredient, EntityType<?> type, int min, int max) {
        super(HusbandryRegistries.INCUBATOR_SERIALIZER.get(), (i, o) -> new IncubatorRecipe(i, o, min, max), ingredient, type);
        this.min = min;
        this.max = max;
    }

    public static IncubatorRecipeBuilder incubate(Ingredient item, EntityType<?> entity, int min ,int max) {
        return new IncubatorRecipeBuilder(item, entity, min, max);
    }

    @Override
    public @NotNull Item getResult() {
        return Items.AIR;
    }

    @Override
    public void save(@NotNull RecipeOutput output, @NotNull ResourceLocation resource) {
        output.accept(resource, new IncubatorRecipe(ingredient, result, min, max), null);
    }
}