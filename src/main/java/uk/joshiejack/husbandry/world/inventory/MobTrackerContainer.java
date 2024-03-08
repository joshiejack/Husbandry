package uk.joshiejack.husbandry.world.inventory;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.penguinlib.world.inventory.AbstractBookMenu;

import javax.annotation.Nonnull;

public class MobTrackerContainer extends AbstractBookMenu {
    public MobTrackerContainer(int windowID) {
        super(Husbandry.HusbandryContainers.BOOK.get(), windowID);
    }

    @Override
    public @Nonnull ItemStack quickMoveStack(@Nonnull Player player, int id) {
        return ItemStack.EMPTY;
    }
}
