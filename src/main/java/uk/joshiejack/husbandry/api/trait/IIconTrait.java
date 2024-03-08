package uk.joshiejack.husbandry.api.trait;

import net.minecraft.world.entity.Mob;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.penguinlib.util.icon.Icon;

public interface IIconTrait extends IMobTrait {
    @OnlyIn(Dist.CLIENT)
    Icon getIcon(Mob mob, IMobStats<?> stats);
}