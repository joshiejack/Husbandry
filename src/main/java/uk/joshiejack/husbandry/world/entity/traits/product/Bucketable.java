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

public class Bucketable implements IInteractiveTrait {
    @Override
    public boolean onEntityInteract(Mob mob, IMobStats<?> stats, Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).getItem() == Items.BUCKET) {
            if (stats.canProduceProduct(mob)) {
                List<ItemStack> ret = stats.getSpecies().products().getProduct(mob, player);
                boolean replaced = false;
                for (ItemStack stack : ret) {
                    if (stack.getItem() == Items.MILK_BUCKET && !replaced) {
                        player.getItemInHand(hand).shrink(1);
                        replaced = true;
                    }

                    ItemHandlerHelper.giveItemToPlayer(player, stack);
                }

                stats.setProduced(mob, ret.size());
            }

            return true;
        }

        return false;
    }
}
