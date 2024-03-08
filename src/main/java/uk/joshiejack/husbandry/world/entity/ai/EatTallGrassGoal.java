package uk.joshiejack.husbandry.world.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.event.EventHooks;
import uk.joshiejack.husbandry.api.IMobStats;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Predicate;

public class EatTallGrassGoal extends AbstractMoveToBlockGoal {
    private static final Predicate<BlockState> IS_TALL_GRASS = BlockStatePredicate.forBlock(Blocks.GRASS_BLOCK);
    private static final Predicate<BlockState> IS_DOUBLE_TALL_GRASS = BlockStatePredicate.forBlock(Blocks.TALL_GRASS).where(DoublePlantBlock.HALF, half -> Objects.equals(half, DoubleBlockHalf.LOWER));

    public EatTallGrassGoal(Mob entity, IMobStats<?> stats) {
        super(entity, stats, Orientation.IN, 8);
    }

    @Override
    public boolean canUse() {
        return stats.isHungry() && entity.getRandom().nextInt(50) == 0 && super.canUse();
    }

    @Override
    protected boolean isValidTarget(@Nonnull LevelReader world, @Nonnull BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return IS_TALL_GRASS.test(state) || IS_DOUBLE_TALL_GRASS.test(state); //TODO: Allow for custom grass eating?
    }

    @Override
    public void tick() {
        super.tick();
        entity.getLookControl().setLookAt((double) blockPos.getX() + 0.5D, blockPos.getY(),
                (double) blockPos.getZ() + 0.5D, 10.0F, (float) entity.getMaxHeadXRot());
        if (isNearDestination()) {
            BlockState state = entity.level().getBlockState(blockPos);
            if (IS_TALL_GRASS.test(state)) {
                if (EventHooks.getMobGriefingEvent(entity.level(), entity)) {
                    entity.level().destroyBlock(blockPos, false);
                }

                stats.feed();
                tryTicks = 9999;
            } else if (IS_DOUBLE_TALL_GRASS.test(state)) {
                if (EventHooks.getMobGriefingEvent(entity.level(), entity)) {
                    entity.level().destroyBlock(blockPos, false);
                    entity.level().setBlock(blockPos, Blocks.GRASS_BLOCK.defaultBlockState(), 2);
                }

                stats.feed();
                tryTicks = 9999;
            }
        }
    }
}
