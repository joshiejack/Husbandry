package uk.joshiejack.husbandry.world.entity.traits.product;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IBiHourlyTrait;
import uk.joshiejack.husbandry.api.trait.IInitTrait;
import uk.joshiejack.husbandry.api.trait.INewDayTrait;
import uk.joshiejack.penguinlib.util.helper.MathHelper;

//Mobs with this, have their products reset quicker based on happiness
public class MoreProductTrait extends AbstractMobProductTrait implements IInitTrait, INewDayTrait, IBiHourlyTrait {
    private int productsPerDay = 1; //How many products the mobs give every 24 hours

    protected int recalculateProductsPerDay(RandomSource random, IMobStats<?> stats) {
        return MathHelper.convertRange(0, stats.getMaxHappiness(), 1, 5, stats.getHappiness());
    }

    @Override
    public void initTrait(RandomSource random, IMobStats<?> stats) {
        productsPerDay = recalculateProductsPerDay(random, stats);
    }

    @Override
    public void onBihourlyTick(Mob mob, IMobStats<?> stats) {
        if (productsProduced < productsPerDay) {
            stats.resetProduct(mob); //Reset the product every two hours
        }
    }

    @Override
    public void onNewDay(Mob mob, IMobStats<?> stats) {
        productReset++;
        if (productReset >= stats.getSpecies().products().getDaysBetweenProducts()) {
            stats.resetProduct(mob);
            productReset = 0;
        }

        //Recalculate products per day
        productsPerDay = recalculateProductsPerDay(mob.getRandom(), stats);
    }
}
