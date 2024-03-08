package uk.joshiejack.husbandry.world.entity.traits.product;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.animal.Chicken;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IJoinWorldTrait;
import uk.joshiejack.husbandry.world.entity.ai.LayEggGoal;
import uk.joshiejack.husbandry.world.entity.ai.RoostGoal;

public class LaysEggTrait implements IJoinWorldTrait {
    @Override
    public void onJoinWorld(Mob mob, IMobStats<?> stats) {
        GoalSelector selector = mob.goalSelector;
        if (mob instanceof Chicken chicken) {
            chicken.eggTime = Integer.MAX_VALUE;
        }

        selector.addGoal(3, new RoostGoal(mob, stats));
        selector.addGoal(4, new LayEggGoal(mob, stats));
    }
}
