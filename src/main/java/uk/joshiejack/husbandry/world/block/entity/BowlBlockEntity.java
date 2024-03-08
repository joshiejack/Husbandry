package uk.joshiejack.husbandry.world.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import uk.joshiejack.husbandry.world.block.BowlBlock;
import uk.joshiejack.husbandry.world.block.HusbandryBlocks;
import uk.joshiejack.husbandry.world.entity.traits.food.EatsCatFoodTrait;
import uk.joshiejack.husbandry.world.entity.traits.food.EatsDogFoodTrait;
import uk.joshiejack.husbandry.world.entity.traits.food.EatsRabbitFoodTrait;

import javax.annotation.Nonnull;

@SuppressWarnings("ConstantConditions")
public class BowlBlockEntity extends FoodSupplyBlockEntity {
    public BowlBlockEntity(BlockPos pos, BlockState state) {
        super(HusbandryBlockEntities.BOWL.get(), pos, state);
    }

    @Override
    public boolean canPlaceItem(int slot, @Nonnull ItemStack stack) {
        return stack.is(EatsRabbitFoodTrait.RABBIT_FOOD) || stack.is(EatsDogFoodTrait.DOG_FOOD) || stack.is(EatsCatFoodTrait.CAT_FOOD);
    }

    private BowlBlock.FoodType getFoodTypeFromStack(ItemStack stack) {
        return stack.is(EatsRabbitFoodTrait.RABBIT_FOOD) ? BowlBlock.FoodType.RABBIT_FOOD
                : stack.is(EatsCatFoodTrait.CAT_FOOD) ? BowlBlock.FoodType.CAT_FOOD : BowlBlock.FoodType.DOG_FOOD;
    }

    @Override
    public void setItem(int slot, @Nonnull ItemStack stack) {
        super.setItem(slot, stack);
        if (stack.isEmpty())
            level.setBlock(worldPosition, HusbandryBlocks.BOWL.get().defaultBlockState().setValue(BowlBlock.FILL, 0), 2);
        else level.setBlock(worldPosition, HusbandryBlocks.BOWL.get().defaultBlockState()
                .setValue(BowlBlock.TYPE, getFoodTypeFromStack(stack))
                .setValue(BowlBlock.FILL, stack.getCount()), 2);
    }
}
