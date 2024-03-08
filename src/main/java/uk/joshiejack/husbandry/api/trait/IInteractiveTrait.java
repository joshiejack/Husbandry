package uk.joshiejack.husbandry.api.trait;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import uk.joshiejack.husbandry.api.IMobStats;

public interface IInteractiveTrait extends IMobTrait {
    boolean onEntityInteract(Mob mob, IMobStats<?> stats, Player player, InteractionHand hand);
}