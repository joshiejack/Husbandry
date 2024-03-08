package uk.joshiejack.husbandry.world.entity.traits.food;

import net.minecraft.world.item.Item;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import uk.joshiejack.husbandry.Husbandry;

public class EatsCatFoodTrait extends AbstractFoodTrait {
    public static final TagKey<Item> CAT_FOOD = ItemTags.create(new ResourceLocation(Husbandry.MODID, "cat_food"));

    @Override
    protected TagKey<Item> getFoodTag() {
        return CAT_FOOD;
    }
}
