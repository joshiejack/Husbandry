package uk.joshiejack.husbandry.world.item.crafting;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.joshiejack.husbandry.Husbandry;

@SuppressWarnings("unused")
public class HusbandryRegistries {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, Husbandry.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Husbandry.MODID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<IncubatorRecipe>> INCUBATOR = RECIPE_TYPES.register("incubator", () -> RecipeType.register(Husbandry.MODID + ":incubator"));
    public static final DeferredHolder<RecipeSerializer<?>, IncubatorRecipe.Serializer> INCUBATOR_SERIALIZER = SERIALIZERS.register("incubator", IncubatorRecipe.Serializer::new);
}
