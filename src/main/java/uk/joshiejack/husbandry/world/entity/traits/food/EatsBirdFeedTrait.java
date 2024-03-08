package uk.joshiejack.husbandry.world.entity.traits.food;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.world.entity.ai.AbstractMoveToBlockGoal;
import uk.joshiejack.husbandry.world.entity.ai.EatFoodGoal;

public class EatsBirdFeedTrait extends AbstractFoodTrait {
    public static final TagKey<Item> BIRD_FEED = ItemTags.create(new ResourceLocation(Husbandry.MODID, "bird_feed"));

    @Override
    protected TagKey<Item> getFoodTag() {
        return BIRD_FEED;
    }

    @Override
    public void onJoinWorld(Mob mob, IMobStats<?> stats) {
        mob.goalSelector.addGoal(3, new EatFoodGoal(mob, stats, getFoodTag(), AbstractMoveToBlockGoal.Orientation.ABOVE, 8));
    }
}