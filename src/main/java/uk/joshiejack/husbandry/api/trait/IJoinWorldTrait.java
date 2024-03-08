package uk.joshiejack.husbandry.api.trait;

import net.minecraft.world.entity.Mob;
import uk.joshiejack.husbandry.api.IMobStats;

public interface IJoinWorldTrait extends IMobTrait {
    void onJoinWorld(Mob mob, IMobStats<?> stats);
}