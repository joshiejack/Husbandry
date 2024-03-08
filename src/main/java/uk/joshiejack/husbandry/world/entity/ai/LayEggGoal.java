package uk.joshiejack.husbandry.world.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.world.block.entity.NestTileEntity;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.network.packet.SetInventorySlotPacket;

import javax.annotation.Nonnull;

public class LayEggGoal extends AbstractMoveToBlockGoal {
    private int eggTimer;

    public LayEggGoal(Mob entity, IMobStats<?> stats) {
        super(entity, stats, Orientation.ABOVE, 8);
    }

    @Override
    public boolean canUse() {
        return stats.canProduceProduct(entity) && entity.getRandom().nextInt(5) == 0 && super.canUse();
    }

    @Override
    protected boolean isValidTarget(@Nonnull LevelReader world, @Nonnull BlockPos pos) {
        BlockEntity tile = world.getBlockEntity(pos);
        return tile instanceof NestTileEntity && ((NestTileEntity)tile).getItem(0).isEmpty();
    }

    @Override
    public void tick() {
        super.tick();
        entity.getLookControl().setLookAt((double) blockPos.getX() + 0.5D, blockPos.getY() + 1,
                (double) blockPos.getZ() + 0.5D, 10.0F, (float) entity.getMaxHeadXRot());

        if (isNearDestination()) {
            eggTimer++;

            entity.setPos(blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D);
            if (eggTimer > 60) {
                BlockEntity tile = entity.level().getBlockEntity(blockPos);
                if (tile instanceof NestTileEntity) {
                    NestTileEntity nest = ((NestTileEntity) tile);
                    if (nest.getItem(0).isEmpty()) {
                        ItemStack product = stats.getSpecies().products().getProduct(entity, null).get(0);
                        CompoundTag data = new CompoundTag();
                        data.putInt("HatchTime", stats.getSpecies().daysToBirth());
                        data.putInt("HeartLevel", stats.getHearts());
                        product.setTag(data);
                        nest.setItem(0, product);
                        stats.setProduced(entity, 1);
                        entity.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (entity.getRandom().nextFloat() - entity.getRandom().nextFloat()) * 0.2F + 1.0F);
                        tryTicks = 9999;
                        PenguinNetwork.sendToNearby(tile, new SetInventorySlotPacket(blockPos, 0, product));
                    }
                }

                eggTimer = 0;
            }
        }
    }
}
