package uk.joshiejack.husbandry.world.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import uk.joshiejack.husbandry.world.block.FeedingTrayBlock;
import uk.joshiejack.husbandry.world.block.HusbandryBlocks;
import uk.joshiejack.husbandry.world.entity.traits.food.EatsBirdFeedTrait;

import javax.annotation.Nonnull;

@SuppressWarnings("ConstantConditions")
public class FeedingTrayBlockEntity extends FoodSupplyBlockEntity {
    public FeedingTrayBlockEntity(BlockPos pos, BlockState state) {
        super(HusbandryBlockEntities.FEEDING_TRAY.get(), pos, state);
    }

    @Override
    public boolean canPlaceItem(int slot, @Nonnull ItemStack stack) {
        return stack.is(EatsBirdFeedTrait.BIRD_FEED);
    }

    @Override
    public void setItem(int slot, @Nonnull ItemStack stack) {
        super.setItem(slot, stack);
        if (stack.isEmpty()) level.setBlock(worldPosition, HusbandryBlocks.FEEDING_TRAY.get().defaultBlockState().setValue(FeedingTrayBlock.FILL, 0), 2);
        else level.setBlock(worldPosition, HusbandryBlocks.FEEDING_TRAY.get().defaultBlockState().setValue(FeedingTrayBlock.FILL, stack.getCount()), 2);
    }
}
