package uk.joshiejack.husbandry.api;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import uk.joshiejack.penguinlib.util.icon.Icon;

import javax.annotation.Nullable;
import java.util.List;

public interface IProducts {
    Icon getIcon();

    int getDaysBetweenProducts();

    List<ItemStack> getProduct(Mob entity, @Nullable Player player);
}
