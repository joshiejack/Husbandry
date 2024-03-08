package uk.joshiejack.husbandry.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.husbandry.world.block.entity.NestTileEntity;
import uk.joshiejack.penguinlib.world.block.PenguinBlock;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class NestBlock extends PenguinBlock implements EntityBlock {
    private static final VoxelShape COLLISION = Shapes.box(0.15D, 0D, 0.15D, 0.85D, 0.2D, 0.85D);
    private static final VoxelShape SHAPE = Shapes.box(0.15D, 0D, 0.15D, 0.85D, 0.35D, 0.85D);

    public NestBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(0.5F));
        setHasInventory();
    }

    @Nonnull
    @Override
    public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull BlockGetter reader, @Nonnull BlockPos pos, @Nonnull CollisionContext ctx) {
        return COLLISION;
    }

    @Nonnull
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new NestTileEntity(pos, state);
    }
}
