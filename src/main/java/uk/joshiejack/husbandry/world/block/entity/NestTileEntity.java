package uk.joshiejack.husbandry.world.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.network.packet.SetInventorySlotPacket;
import uk.joshiejack.penguinlib.world.block.entity.inventory.InventoryBlockEntity;

import javax.annotation.Nonnull;

public class NestTileEntity extends InventoryBlockEntity {
    public static final ModelProperty<ItemStack> ITEM_STACK = new ModelProperty<>();

    public NestTileEntity(BlockPos pos, BlockState state) {
        super(HusbandryBlockEntities.NEST.get(), pos, state,1);
    }

    @Override
    public boolean canPlaceItem(int slot, @Nonnull ItemStack stack) {
        return false;
    }

    @Override
    public void setItem(int slot, @Nonnull ItemStack stack) {
        super.setItem(slot, stack);
        assert level != null;
        if (level.isClientSide) {
            requestModelDataUpdate();
            level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }

    @Nonnull
    public ItemStack removeItem(int slot, int amount) {
        ItemStack ret = super.removeItem(slot, amount);
        setChanged();
        return ret;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        assert level != null;
        if (!level.isClientSide)
            PenguinNetwork.sendToNearby(this, new SetInventorySlotPacket(worldPosition, 0, items.get(0)));
    }

    @Override
    @Nonnull
    public ModelData getModelData() {
        return ModelData.builder().with(ITEM_STACK, items.get(0)).build();
    }
}
