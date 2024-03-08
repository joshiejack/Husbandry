package uk.joshiejack.husbandry.world.entity.stats;

import com.google.common.collect.Maps;
import joptsimple.internal.Strings;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.ISpecies;
import uk.joshiejack.husbandry.api.trait.IMobTrait;
import uk.joshiejack.penguinlib.util.icon.ItemIcon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record Species(int minimumLifespan, int maximumLifespan, Item treat, int genericTreats, int speciesTreats, int daysToBirth, int daysToMaturity, @Nonnull Products products, List<IMobTrait> traits) implements ISpecies {
    public static final Map<EntityType<?>, Species> TYPES = Maps.newHashMap();
    public static final Species NONE = new Species(0, 0, Items.AIR, 0, 0, 0, 0, new Products(0, new ResourceLocation(Strings.EMPTY), ItemIcon.EMPTY), new ArrayList<>());

    public int getDaysBetweenProduct() {
        return products.getDaysBetweenProducts();
    }

    public List<ItemStack> getProduct(@Nonnull Mob entity, @Nullable Player player) {
        return products.getProduct(entity, player);
    }

    @Override
    public int minimumLifespan() {
        return minimumLifespan * Husbandry.HusbandryConfig.daysPerYear.get();
    }

    @Override
    public int maximumLifespan() {
        return maximumLifespan * Husbandry.HusbandryConfig.daysPerYear.get();
    }
}
