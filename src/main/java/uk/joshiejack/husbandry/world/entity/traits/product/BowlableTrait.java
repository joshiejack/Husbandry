package uk.joshiejack.husbandry.world.entity.traits.product;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IInteractiveTrait;

import java.util.List;

public class BowlableTrait implements IInteractiveTrait {
    @Override
    public boolean onEntityInteract(Mob mob, IMobStats<?> stats, Player player, InteractionHand hand) {
        if (stats.canProduceProduct(mob) && player.getItemInHand(hand).getItem() == Items.BOWL) {
            List<ItemStack> ret = stats.getSpecies().products().getProduct(mob, player);
            ret.forEach(stack -> ItemHandlerHelper.giveItemToPlayer(player, stack));
            player.getItemInHand(hand).shrink(ret.size());
            stats.setProduced(mob, ret.size());
            return !ret.isEmpty();
        }

        return false;
    }
}
