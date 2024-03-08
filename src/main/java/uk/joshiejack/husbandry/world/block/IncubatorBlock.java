package uk.joshiejack.husbandry.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.husbandry.world.block.entity.IncubatorBlockEntity;
import uk.joshiejack.penguinlib.world.block.RotatableBlock;

import javax.annotation.Nonnull;
import java.util.EnumMap;

@SuppressWarnings("deprecation")
public class IncubatorBlock extends RotatableBlock implements EntityBlock {
    private static final EnumMap<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    static {
        SHAPES.put(Direction.NORTH, Block.box(1D, 0D, 3D, 15D, 11D, 13D));
        SHAPES.put(Direction.SOUTH, Block.box(1D, 0D, 3D, 15D, 11D, 13D));
        SHAPES.put(Direction.WEST, Block.box(3D, 0D, 1D, 13D, 11D, 15D));
        SHAPES.put(Direction.EAST, Block.box(3D, 0D, 1D, 13D, 11D, 15D));
    }

    public IncubatorBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(1.5F));
        hasInventory = true;
    }

    @Nonnull
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Nonnull
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new IncubatorBlockEntity(pos, state);
    }
}
