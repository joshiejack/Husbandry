package uk.joshiejack.husbandry.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.husbandry.world.block.entity.BowlBlockEntity;
import uk.joshiejack.penguinlib.world.block.PenguinBlock;

import javax.annotation.Nonnull;
import java.util.Locale;

@SuppressWarnings("deprecation")
public class BowlBlock extends PenguinBlock implements EntityBlock {
    public static final EnumProperty<FoodType> TYPE = EnumProperty.create("type", FoodType.class);
    public static final IntegerProperty FILL = IntegerProperty.create("fill", 0, 4);
    private static final VoxelShape BOWL_SHAPE = Block.box(4D, 0D, 4D, 12D, 2D, 12D);

    public BowlBlock() {
        super(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(0.3F));
        setHasInventory();
    }

    @Override
    protected int getInsertAmount(IItemHandler handler, ItemStack held) {
        return 1;
    }

    @Nonnull
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return BOWL_SHAPE;
    }

    @Nonnull
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new BowlBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FILL, TYPE);
    }

    public enum FoodType implements StringRepresentable {
        CAT_FOOD, DOG_FOOD, RABBIT_FOOD;

        @Nonnull
        @Override
        public String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
