package uk.joshiejack.husbandry.world.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.IPlantable;
import uk.joshiejack.husbandry.api.IMobStats;

import javax.annotation.Nonnull;

public class FindProductGoal extends AbstractMoveToBlockGoal {
    public FindProductGoal(Mob entity, IMobStats<?> stats) {
        super(entity, stats, Orientation.BESIDE, 16);
    }

    @Override
    public boolean canUse() {
        return stats.canProduceProduct(entity) && entity.getRandom().nextInt(5) == 0 && super.canUse();
    }

    @Override
    protected boolean isValidTarget(@Nonnull LevelReader world, @Nonnull BlockPos pos) {
        BlockState below = world.getBlockState(pos.below());
        return below.getBlock().canSustainPlant(below, entity.level(), pos.below(), Direction.UP, (IPlantable) Blocks.DANDELION)
                && world.getBlockState(pos).isAir();
    }

    @Override
    public void tick() {
        super.tick();
        entity.getLookControl().setLookAt((double) blockPos.getX() + 0.5D, blockPos.getY() + 1,
                (double) blockPos.getZ() + 0.5D, 10.0F, (float) entity.getMaxHeadXRot());

        if (isNearDestination()) {
            ItemStack stack = stats.getSpecies().products().getProduct(entity, null).get(0);
            if (!stack.isEmpty()) {
                BlockState state = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
                entity.level().setBlock(blockPos, state, 2);
                stats.setProduced(entity, 1);
            }

            tryTicks = 9999;
        }
    }
}
