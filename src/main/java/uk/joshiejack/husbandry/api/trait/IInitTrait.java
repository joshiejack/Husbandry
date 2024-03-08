package uk.joshiejack.husbandry.api.trait;

import net.minecraft.util.RandomSource;
import uk.joshiejack.husbandry.api.IMobStats;

public interface IInitTrait extends IMobTrait {
    default void initTrait(RandomSource random, IMobStats<?> stats) {}
}