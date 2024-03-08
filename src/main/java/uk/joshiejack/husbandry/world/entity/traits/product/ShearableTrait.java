package uk.joshiejack.husbandry.world.entity.traits.product;

import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.Tags;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IInteractiveTrait;

public class ShearableTrait implements IInteractiveTrait {
    @Override
    public boolean onEntityInteract(Mob mob, IMobStats<?> stats, Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (held.is(Tags.Items.SHEARS) && stats.canProduceProduct(mob)) {
            if (!player.level().isClientSide) {
                RandomSource rand = mob.getRandom();
                for (ItemStack stack : stats.getSpecies().products().getProduct(mob, player)) {
                    ItemEntity ent = mob.spawnAtLocation(stack, 1.0F);
                    assert ent != null;
                    ent.setDeltaMovement(ent.getDeltaMovement().add((rand.nextFloat() -
                                    rand.nextFloat()) * 0.1F, rand.nextFloat() * 0.05F,
                            (rand.nextFloat() - rand.nextFloat()) * 0.1F));
                }

                stats.setProduced(mob, 5);
            }

            if (mob instanceof Sheep) {
                ((Sheep) mob).setSheared(true);
            }

            held.hurtAndBreak(1, mob, (e) -> e.broadcastBreakEvent(hand));

            return true;
        }

        return false;
    }
}
