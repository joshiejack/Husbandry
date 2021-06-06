package uk.joshiejack.husbandry.entity.traits.happiness;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.husbandry.entity.traits.types.IInteractiveTrait;

public class PettableTrait extends AbstractLoveableTrait implements IInteractiveTrait {
    public PettableTrait(String name) {
        super(name);
    }

    @Override
    public boolean onRightClick(MobStats<?> stats, PlayerEntity player, Hand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (held.isEmpty() && stats.isUnloved()) {
            return stats.setLoved();
        }

        return false;
    }
}