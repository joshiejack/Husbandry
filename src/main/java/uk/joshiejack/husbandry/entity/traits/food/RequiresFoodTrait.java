package uk.joshiejack.husbandry.entity.traits.food;

import net.minecraft.util.DamageSource;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.husbandry.entity.traits.AbstractMobTrait;
import uk.joshiejack.husbandry.entity.traits.types.INewDayTrait;
import uk.joshiejack.penguinlib.data.TimeUnitRegistry;

public class RequiresFoodTrait extends AbstractMobTrait implements INewDayTrait {
    public RequiresFoodTrait(String name) {
        super(name);
    }

    @Override
    public void onNewDay(MobStats<?> stats) {
        int hunger = stats.getHunger();
        if (hunger >= TimeUnitRegistry.get("require_food_max_days"))
            stats.getEntity().hurt(DamageSource.STARVE, hunger);
    }
}