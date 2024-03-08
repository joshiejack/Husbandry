package uk.joshiejack.husbandry.world.entity.traits.product;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.penguinlib.util.helper.MathHelper;

//Mobs with this, have their products reset quicker based on happiness
public class MoreProductChanceTrait extends MoreProductTrait {
    @Override
    protected int recalculateProductsPerDay(RandomSource random, IMobStats<?> stats) {
        return MathHelper.convertRange(0, stats.getMaxHappiness(), random.nextInt(2),
                2 + random.nextInt(2), stats.getHappiness());
    }

    @Override
    public void onBihourlyTick(Mob mob, IMobStats<?> stats) {
        if ((productsProduced == 1 && (101 - MathHelper.convertRange(0, stats.getMaxHappiness(), 1, 100, stats.getHappiness())) == 0) ||
                (productsProduced == 2 && (201 - MathHelper.convertRange(0, stats.getMaxHappiness(), 1, 200, stats.getHappiness())) == 0)) {
            stats.resetProduct(mob);
        }
    }
}