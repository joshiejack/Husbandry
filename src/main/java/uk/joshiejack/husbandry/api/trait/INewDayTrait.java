package uk.joshiejack.husbandry.api.trait;

import net.minecraft.world.entity.Mob;
import uk.joshiejack.husbandry.api.IMobStats;

public interface INewDayTrait extends IMobTrait {
    void onNewDay(Mob mob, IMobStats<?> stats);
}