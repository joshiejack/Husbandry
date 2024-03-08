package uk.joshiejack.husbandry.world.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.network.packet.SetInventorySlotPacket;
import uk.joshiejack.penguinlib.world.block.entity.inventory.InventoryBlockEntity;

import javax.annotation.Nonnull;

public abstract class FoodSupplyBlockEntity extends InventoryBlockEntity {
    public FoodSupplyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state,1);
    }

    @Override
    public int getMaxStackSize() {
        return 2;
    }

    @Nonnull
    public ItemStack removeItem(int slot, int amount) {
        return ItemStack.EMPTY;
    }

    public void consume() {
        ItemStack copied = items.get(0).copy();
        copied.shrink(1);
        setItem(0, copied);
        PenguinNetwork.sendToNearby(new SetInventorySlotPacket(worldPosition, 0, items.get(0)), this);
    }
}
