package uk.joshiejack.husbandry.world.entity.ai;

import net.minecraft.world.entity.Mob;
import uk.joshiejack.husbandry.api.IMobStats;

public class ShelterAtNightGoal extends AbstractHideInsideGoal {
    public ShelterAtNightGoal(Mob entity, IMobStats<?> stats) {
        super(entity, stats);
    }

    @Override
    protected boolean shouldHide() {
        return !entity.level().isDay();
    }

    @Override
    protected boolean canStopHiding() {
        return entity.level().isDay();
    }
}