package uk.joshiejack.husbandry.world.entity.stats;

import com.google.common.collect.Lists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import uk.joshiejack.husbandry.api.IProducts;
import uk.joshiejack.penguinlib.util.helper.FakePlayerHelper;
import uk.joshiejack.penguinlib.util.icon.Icon;
import uk.joshiejack.penguinlib.util.icon.ItemIcon;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class Products implements IProducts {
    public static final Products NONE = new Products(Integer.MAX_VALUE, null, ItemIcon.EMPTY);
    private static final List<ItemStack> EMPTY = Lists.newArrayList(ItemStack.EMPTY);
    private final ResourceLocation lootTable;
    private final int dayBetween;
    private final Icon icon;

    public Products(int daysBetween, ResourceLocation lootTable, Icon icon) {
        this.icon = icon;
        this.dayBetween = daysBetween;
        this.lootTable = lootTable;
    }

    @Override
    public Icon getIcon() {
        return icon;
    }

    @Override
    public int getDaysBetweenProducts() {
        return dayBetween;
    }

    /** Size will be 0, 1 or 2 **/
    @Override
    public List<ItemStack> getProduct(Mob entity, @Nullable Player player) {
        if (lootTable == null || !(entity.level() instanceof ServerLevel serverLevel))
            return EMPTY;
        LootParams.Builder lootparams = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, entity.position())
                .withParameter(LootContextParams.THIS_ENTITY, entity);
        if (player != null) lootparams.withParameter(LootContextParams.KILLER_ENTITY, player);
        else lootparams.withParameter(LootContextParams.KILLER_ENTITY, FakePlayerHelper.getFakePlayerWithPosition(serverLevel, entity.blockPosition()));
        LootTable loottable = Objects.requireNonNull(entity.level().getServer()).getLootData().getLootTable(lootTable);
        return loottable.getRandomItems(lootparams.create(LootContextParamSets.CHEST));
    }
}
