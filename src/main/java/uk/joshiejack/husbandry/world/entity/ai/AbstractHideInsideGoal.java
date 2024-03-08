package uk.joshiejack.husbandry.world.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.LevelReader;
import uk.joshiejack.husbandry.api.IMobStats;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class AbstractHideInsideGoal extends AbstractMoveToBlockGoal {
    public AbstractHideInsideGoal(Mob entity, IMobStats<?> stats) {
        super(entity, stats, Orientation.ABOVE, 8);
    }

    @Override
    public boolean canUse() {
        return shouldHide() && entity.getRandom().nextInt(5) == 0 && super.canUse();
    }

    @Override
    protected boolean findNearestBlock() {
        if (shouldHide()) return super.findNearestBlock();
        blockPos = entity instanceof PathfinderMob ? BlockPos.containing(Objects.requireNonNull(LandRandomPos.getPos((PathfinderMob) entity, 10, 7))) : blockPos;
        return isValidTarget(entity.level(), blockPos);
    }

    protected abstract boolean shouldHide();

    @Override
    protected boolean isValidTarget(@Nonnull LevelReader world, @Nonnull BlockPos pos) {
        return world.getBlockState(pos.above()).getCollisionShape(world, pos.above()).isEmpty()
            && world.getBlockState(pos.above(2)).getCollisionShape(world, pos.above(2)).isEmpty()
                && !world.canSeeSky(pos.above());
    }

    @Override
    public void tick() {
        super.tick();
        if (isNearDestination() && canStopHiding())
            tryTicks = 2000;
    }

    protected abstract boolean canStopHiding();
}
