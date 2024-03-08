package uk.joshiejack.husbandry.world.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelReader;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.world.block.entity.NestTileEntity;

import javax.annotation.Nonnull;

import static uk.joshiejack.husbandry.world.entity.ai.AbstractMoveToBlockGoal.Orientation.ABOVE;

public class RoostGoal extends AbstractMoveToBlockGoal {
    public RoostGoal(Mob entity, IMobStats<?> stats) {
        super(entity, stats, ABOVE, 16);
    }

    @Override
    public boolean canUse() {
        return !entity.level().isDay() && super.canUse();
    }

    @Override
    protected boolean isValidTarget(@Nonnull LevelReader world, @Nonnull BlockPos pos) {
        return world.getBlockEntity(pos) instanceof NestTileEntity &&
                world.getEntityCollisions(entity, entity.getBoundingBox()).isEmpty(); //TODO: CHeck if this follows the rules
    }

    @Override
    public void tick() {
        super.tick();
        if (isNearDestination()) {
            if (entity.level().isDay()) tryTicks = 9999;
            entity.setPos(blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D);
        } else entity.getLookControl().setLookAt((double) blockPos.getX() + 0.5D, blockPos.getY(),
                (double) blockPos.getZ() + 0.5D, 10.0F, (float) entity.getMaxHeadXRot());
    }
}
