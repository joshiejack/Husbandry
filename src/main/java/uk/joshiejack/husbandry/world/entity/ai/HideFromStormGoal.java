package uk.joshiejack.husbandry.world.entity.ai;

import net.minecraft.world.entity.Mob;
import uk.joshiejack.husbandry.api.IMobStats;

public class HideFromStormGoal extends AbstractHideInsideGoal {
    public HideFromStormGoal(Mob entity, IMobStats<?> stats) {
        super(entity, stats);
    }

    @Override
    protected boolean shouldHide() {
        return entity.level().isThundering() && entity.level().isRainingAt(entity.blockPosition());
    }

    @Override
    protected boolean canStopHiding() {
        return !entity.level().isThundering();
    }
}
