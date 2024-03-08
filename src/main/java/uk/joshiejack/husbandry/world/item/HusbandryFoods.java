package uk.joshiejack.husbandry.world.item;

import net.minecraft.world.food.FoodProperties;

public class HusbandryFoods {
    public static final FoodProperties BUTTER = new FoodProperties.Builder().nutrition(1).saturationMod(0.2F).build();
    public static final FoodProperties DINNER_ROLL = new FoodProperties.Builder().nutrition(6).saturationMod(0.8F).build();
    public static final FoodProperties BOILED_EGG = new FoodProperties.Builder().nutrition(2).saturationMod(1F).build();
    public static final FoodProperties ICE_CREAM = new FoodProperties.Builder().nutrition(5).saturationMod(0.3F).build();
    public static final FoodProperties MUG_OF_MILK = new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build();
    public static final FoodProperties HOT_MILK = new FoodProperties.Builder().nutrition(3).saturationMod(0.2F).build();
    public static final FoodProperties TRUFFLE = new FoodProperties.Builder().nutrition(4).saturationMod(0.6F).build();
}
