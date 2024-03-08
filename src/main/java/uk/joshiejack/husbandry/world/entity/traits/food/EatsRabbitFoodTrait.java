package uk.joshiejack.husbandry.world.entity.traits.food;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import uk.joshiejack.husbandry.Husbandry;

public class EatsRabbitFoodTrait extends AbstractFoodTrait {
    public static final TagKey<Item> RABBIT_FOOD = ItemTags.create(new ResourceLocation(Husbandry.MODID, "rabbit_food"));

    @Override
    protected TagKey<Item> getFoodTag() {
        return RABBIT_FOOD;
    }
}
