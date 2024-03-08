package uk.joshiejack.husbandry.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.husbandry.world.block.entity.FeedingTrayBlockEntity;
import uk.joshiejack.penguinlib.world.block.PenguinBlock;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class FeedingTrayBlock extends PenguinBlock implements EntityBlock {
    public static final IntegerProperty FILL = IntegerProperty.create("fill", 0, 2);
    private static final VoxelShape SHAPE = Shapes.box(0.0D, 0D, 0.0D, 1.0D, 0.075D, 1.0D);

    public FeedingTrayBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(0.4F).noCollission());
        registerDefaultState(defaultBlockState().setValue(FILL, 0));
        setHasInventory();
    }

    @Override
    protected int getInsertAmount(IItemHandler handler, ItemStack held) {
        return 1;
    }

    @Nonnull
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new FeedingTrayBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FILL);
    }
}
