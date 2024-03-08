package uk.joshiejack.husbandry.api.trait;

import net.minecraft.world.entity.Mob;
import uk.joshiejack.husbandry.api.IMobStats;

public interface IBiHourlyTrait extends IMobTrait {
    void onBihourlyTick(Mob mob, IMobStats<?> stats);
}