package uk.joshiejack.husbandry.world.entity.traits.product;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IDataTrait;
import uk.joshiejack.husbandry.api.trait.IIconTrait;
import uk.joshiejack.husbandry.network.SetProducedProductPacket;
import uk.joshiejack.husbandry.world.entity.stats.MobStats;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.util.icon.Icon;

public abstract class AbstractMobProductTrait implements IIconTrait, IDataTrait {
    protected int productReset; //The counter for the product reset
    protected int productsProduced; //How many produced

    @OnlyIn(Dist.CLIENT)
    @Override
    public Icon getIcon(Mob mob, IMobStats<?> stats) {
        return stats.canProduceProduct(mob) ? stats.getSpecies().products().getIcon().shadowed() :
                stats.getSpecies().products().getIcon();
    }
    
    public void setProduced(Mob mob, MobStats<?> stats, int amount) {
        if (mob.level().isClientSide) {
            this.productsProduced = amount;
        } else {
            productsProduced += amount;
            PenguinNetwork.sendToNearby(mob, new SetProducedProductPacket(mob.getId(), productsProduced));
        }
    }

    @Override
    public void save(CompoundTag tag) {
        tag.putInt("ProductReset", productReset);
        tag.putInt("ProductsProduced", productsProduced);
    }

    @Override
    public void load(CompoundTag nbt) {
        productReset = nbt.getInt("ProductReset");
        productsProduced = nbt.getInt("ProductsProduced");
    }
}
