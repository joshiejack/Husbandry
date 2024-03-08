package uk.joshiejack.husbandry.world.entity.ai;

import net.minecraft.world.entity.Mob;
import uk.joshiejack.husbandry.api.IMobStats;

public class HideFromRainGoal extends AbstractHideInsideGoal {
    public HideFromRainGoal(Mob entity, IMobStats<?> stats) {
        super(entity, stats);
    }

    @Override
    protected boolean shouldHide() {
        return entity.level().isRainingAt(entity.blockPosition());
    }

    @Override
    protected boolean canStopHiding() {
        return !entity.level().isRaining();
    }
}
