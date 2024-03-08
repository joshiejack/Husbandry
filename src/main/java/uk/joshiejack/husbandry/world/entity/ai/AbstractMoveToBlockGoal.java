package uk.joshiejack.husbandry.world.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.LevelReader;
import uk.joshiejack.husbandry.api.IMobStats;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public abstract class AbstractMoveToBlockGoal extends Goal {
    protected final Mob entity;
    public final double speedModifier;
    protected int nextStartTick;
    protected int tryTicks;
    private int maxStayTicks;
    protected BlockPos blockPos = BlockPos.ZERO;
    private final int searchRange;
    protected int verticalSearchRange;
    protected int verticalSearchStart;
    protected int verticalOffset;
    protected final IMobStats<?> stats;
    protected final Orientation orientation;

    public AbstractMoveToBlockGoal(Mob entity, IMobStats<?> stats, Orientation orientation, int searchLength) {
        this.entity = entity;
        this.orientation = orientation;
        this.stats = stats;
        this.speedModifier = 1D;
        this.searchRange = searchLength;
        this.verticalSearchStart = 0;
        this.verticalSearchRange = 1;
        this.verticalOffset = 0;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
    }

    protected boolean isNearDestination() {
        return isNearDestination(blockPos);
    }

    private boolean isNearDestination(BlockPos pos) {
        switch (orientation) {
            case ABOVE:
                return entity.blockPosition().closerThan(pos, 1.5);
            case IN:
                return entity.blockPosition().closerThan(pos, 1);
            case BESIDE:
                return entity.blockPosition().closerThan(pos, 2.5);
            default:
                return false;
        }
    }

    @Override
    public void tick() {
        if (!isNearDestination(blockPos)) {
            ++this.tryTicks;
            if (this.tryTicks % 40 == 0) {
                moveMobToBlock();
            }
        } else
            --this.tryTicks;
    }

    public void resetRunTimer() {
        this.nextStartTick = 0;
    }

    public enum Orientation {
        ABOVE, IN, BESIDE
    }

    //Copying the underlying layer so we can use any Mob not just creature entity...
    @Override
    public boolean canUse() {
        if (this.nextStartTick > 0) {
            --this.nextStartTick;
            return false;
        } else {
            this.nextStartTick = this.nextStartTick(this.entity);
            return this.findNearestBlock();
        }
    }

    protected int nextStartTick(Mob entity) {
        return 200 + entity.getRandom().nextInt(200);
    }

    @Override
    public boolean canContinueToUse() {
        return this.tryTicks >= -this.maxStayTicks && this.tryTicks <= 1200 && this.isValidTarget(this.entity.level(), this.blockPos);
    }

    @Override
    public void start() {
        this.moveMobToBlock();
        this.tryTicks = 0;
        this.maxStayTicks = this.entity.getRandom().nextInt(this.entity.getRandom().nextInt(1200) + 1200) + 1200;
    }

    protected void moveMobToBlock() {
        entity.getNavigation().moveTo((double) ((float) blockPos.getX()) + 0.5D,
                blockPos.getY() + verticalOffset, (double) ((float) blockPos.getZ()) + 0.5D, speedModifier);
    }

    protected boolean findNearestBlock() {
        int j = this.verticalSearchRange;
        BlockPos blockpos = this.entity.blockPosition();
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

        for (int k = verticalSearchStart; k >= -j; k--) {
            for (int l = 0; l < this.searchRange; ++l) {
                for (int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
                    for (int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
                        blockpos$mutable.setWithOffset(blockpos, i1, k, j1);
                        if (this.entity.isWithinRestriction(blockpos$mutable) && this.isValidTarget(this.entity.level(), blockpos$mutable)) {
                            this.blockPos = blockpos$mutable;
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    protected abstract boolean isValidTarget(@Nonnull LevelReader world, @Nonnull BlockPos pos);
}
