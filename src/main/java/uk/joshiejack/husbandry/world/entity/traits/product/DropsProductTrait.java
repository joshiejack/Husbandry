package uk.joshiejack.husbandry.world.entity.traits.product;

import net.minecraft.world.entity.Mob;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IJoinWorldTrait;
import uk.joshiejack.husbandry.world.entity.ai.DropsProductGoal;

public class DropsProductTrait implements IJoinWorldTrait {
    @Override
    public void onJoinWorld(Mob mob, IMobStats<?> stats) {
        mob.goalSelector.addGoal(4, new DropsProductGoal(mob, stats));
    }
}
