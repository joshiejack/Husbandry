package uk.joshiejack.husbandry.world.entity.traits.happiness;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IInteractiveTrait;

public class CarriableTrait extends AbstractLoveableTrait implements IInteractiveTrait {
    @Override
    public boolean onEntityInteract(Mob mob, IMobStats<?> stats, Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (hand == InteractionHand.MAIN_HAND && !player.isVehicle() && held.isEmpty() && stats.isUnloved()) {
            mob.setInvulnerable(true);
            mob.startRiding(player, true);
            return stats.setLoved(mob);
        }

        return false;
    }
}
