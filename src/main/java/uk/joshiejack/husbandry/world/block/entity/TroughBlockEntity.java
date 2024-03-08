package uk.joshiejack.husbandry.world.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import uk.joshiejack.husbandry.world.block.HusbandryBlocks;
import uk.joshiejack.husbandry.world.block.TroughBlock;
import uk.joshiejack.husbandry.world.entity.traits.food.EatsGrassTrait;
import uk.joshiejack.husbandry.world.entity.traits.food.EatsSlopTrait;

import javax.annotation.Nonnull;

public class TroughBlockEntity extends FoodSupplyBlockEntity {
    public TroughBlockEntity(BlockPos pos, BlockState state) {
        super(HusbandryBlockEntities.TROUGH.get(), pos, state);
    }

    @Override
    public int getMaxStackSize() {
        return 4;
    }

    @Override
    public boolean canPlaceItem(int slot, @Nonnull ItemStack stack) {
        return stack.is(EatsGrassTrait.HAY) || stack.is(EatsSlopTrait.SLOP);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void setItem(int slot, @Nonnull ItemStack stack) {
        super.setItem(slot, stack);
        if (stack.isEmpty())
            level.setBlock(worldPosition, HusbandryBlocks.TROUGH.get().defaultBlockState().setValue(TroughBlock.FILL, 0), 2);
        else level.setBlock(worldPosition, HusbandryBlocks.TROUGH.get().defaultBlockState()
                .setValue(TroughBlock.TYPE, stack.is(EatsGrassTrait.HAY) ? TroughBlock.FoodType.HAY : TroughBlock.FoodType.SLOP)
                .setValue(TroughBlock.FILL, stack.getCount()), 2);
    }
}