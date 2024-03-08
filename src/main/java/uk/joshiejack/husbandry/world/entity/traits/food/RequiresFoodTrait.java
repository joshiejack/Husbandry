package uk.joshiejack.husbandry.world.entity.traits.food;

import net.minecraft.world.entity.Mob;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.INewDayTrait;
import uk.joshiejack.penguinlib.data.TimeUnitRegistry;

public class RequiresFoodTrait implements INewDayTrait {
    @Override
    public void onNewDay(Mob mob, IMobStats<?> stats) {
        int hunger = stats.getHunger();
        if (hunger >= TimeUnitRegistry.get("require_food_max_days"))
            mob.hurt(mob.damageSources().starve(), hunger);
    }
}
