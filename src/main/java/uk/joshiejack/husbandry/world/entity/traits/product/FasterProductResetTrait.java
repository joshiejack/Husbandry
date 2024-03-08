package uk.joshiejack.husbandry.world.entity.traits.product;

import net.minecraft.world.entity.Mob;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.INewDayTrait;
import uk.joshiejack.penguinlib.util.helper.MathHelper;

//Mobs with this, have their products reset quicker based on happiness
public class FasterProductResetTrait extends AbstractMobProductTrait implements INewDayTrait {
    @Override
    public void onNewDay(Mob mob, IMobStats<?> stats) {
        productReset++;
        int resetTarget = (1 - MathHelper.convertRange(0, stats.getMaxHappiness(), 0, 0.4, stats.getHappiness())) * stats.getSpecies().products().getDaysBetweenProducts();
        if (productReset >= resetTarget) {
            stats.resetProduct(mob);
            productReset = 0;
        }
    }
}
