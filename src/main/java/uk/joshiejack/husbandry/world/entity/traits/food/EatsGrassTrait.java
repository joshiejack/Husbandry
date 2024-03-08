package uk.joshiejack.husbandry.world.entity.traits.food;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.Item;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.world.entity.ai.AbstractMoveToBlockGoal;
import uk.joshiejack.husbandry.world.entity.ai.EatFoodGoal;
import uk.joshiejack.husbandry.world.entity.ai.EatGrassBlockGoal;
import uk.joshiejack.husbandry.world.entity.ai.EatTallGrassGoal;

import java.util.Objects;

public class EatsGrassTrait extends AbstractFoodTrait {
    public static final TagKey<Item> HAY = ItemTags.create(new ResourceLocation(Husbandry.MODID, "hay"));

    @Override
    protected TagKey<Item> getFoodTag() {
        return HAY;
    }

    @Override
    public void onJoinWorld(Mob mob, IMobStats<?> stats) {
        GoalSelector selector = mob.goalSelector;
        if (mob instanceof Sheep sheep) {
            selector.removeGoal(Objects.requireNonNull(ObfuscationReflectionHelper.getPrivateValue(Sheep.class, sheep, "eatBlockGoal")));
        }

        selector.addGoal(3, new EatFoodGoal(mob, stats, getFoodTag(), AbstractMoveToBlockGoal.Orientation.BESIDE, 16));
        selector.addGoal(3, new EatTallGrassGoal(mob, stats));
        selector.addGoal(3, new EatGrassBlockGoal(mob, stats));
    }
}
