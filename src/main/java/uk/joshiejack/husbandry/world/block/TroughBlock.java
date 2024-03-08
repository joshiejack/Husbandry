package uk.joshiejack.husbandry.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.husbandry.world.block.entity.TroughBlockEntity;
import uk.joshiejack.penguinlib.world.block.PenguinBlock;

import javax.annotation.Nonnull;
import java.util.Locale;

@SuppressWarnings("deprecation")
public class TroughBlock extends PenguinBlock implements EntityBlock {
    private static final VoxelShape COLLISION = Block.box(1D, 0D, 1D, 15D, 24D, 15D);
    private static final VoxelShape SHAPE = Block.box(1D, 0D, 1D, 15D, 12D, 15D);
    public static final EnumProperty<FoodType> TYPE = EnumProperty.create("type", FoodType.class);
    public static final IntegerProperty FILL = IntegerProperty.create("fill", 0, 4);

    public TroughBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(0.8F));
        registerDefaultState(defaultBlockState().setValue(FILL, 0).setValue(TYPE, FoodType.HAY));
        setHasInventory();
    }

    @Override
    protected int getInsertAmount(IItemHandler handler, ItemStack held) {
        return 1;
    }

    @Nonnull
    @Override
    public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull BlockGetter reader, @Nonnull BlockPos pos, @Nonnull CollisionContext ctx) {
        return COLLISION;
    }

    @Deprecated
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new TroughBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FILL, TYPE);
    }

    public enum FoodType implements StringRepresentable {
        HAY, SLOP;

        @Nonnull
        @Override
        public String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
