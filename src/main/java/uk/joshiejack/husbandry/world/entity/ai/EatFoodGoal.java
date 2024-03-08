package uk.joshiejack.husbandry.world.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.world.block.entity.FoodSupplyBlockEntity;

import javax.annotation.Nonnull;

public class EatFoodGoal extends AbstractMoveToBlockGoal {
    private final TagKey<Item> food;

    public EatFoodGoal(Mob entity, IMobStats<?> stats, TagKey<Item> food) {
        this(entity, stats, food, Orientation.BESIDE, 8);

    }
    public EatFoodGoal(Mob entity, IMobStats<?> stats, TagKey<Item> food, Orientation orientation, int distance) {
        super(entity, stats, orientation, distance);
        this.food = food;
    }

    @Override
    public boolean canUse() {
        return stats.isHungry() && entity.getRandom().nextInt(5) == 0 && super.canUse();
    }

    @Override
    protected boolean isValidTarget(@Nonnull LevelReader world, @Nonnull BlockPos pos) {
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof FoodSupplyBlockEntity) {
            ItemStack contents = ((FoodSupplyBlockEntity)tile).getItem(0);
            return contents.getCount() > 0 && contents.is(food);
        } else return false;
    }

    @Override
    public void tick() {
        super.tick();
        entity.getLookControl().setLookAt((double) blockPos.getX() + 0.5D, blockPos.getY() + 1,
                (double) blockPos.getZ() + 0.5D, 10.0F, (float) entity.getMaxHeadXRot());

        if (isNearDestination()) {
            BlockEntity tile = entity.level().getBlockEntity(blockPos);
            if (tile instanceof FoodSupplyBlockEntity) {
                ((FoodSupplyBlockEntity) tile).consume();
                stats.feed();
                entity.playAmbientSound();
                tryTicks = 9999;
            }
        }
    }
}
