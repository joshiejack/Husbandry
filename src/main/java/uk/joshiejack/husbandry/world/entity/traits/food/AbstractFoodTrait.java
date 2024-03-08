package uk.joshiejack.husbandry.world.entity.traits.food;

import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.util.Lazy;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IIconTrait;
import uk.joshiejack.husbandry.api.trait.IInteractiveTrait;
import uk.joshiejack.husbandry.api.trait.IJoinWorldTrait;
import uk.joshiejack.husbandry.world.entity.ai.EatFoodGoal;
import uk.joshiejack.penguinlib.util.icon.Icon;
import uk.joshiejack.penguinlib.util.icon.TagIcon;

public abstract class AbstractFoodTrait implements IJoinWorldTrait, IInteractiveTrait, IIconTrait {
    private final Lazy<Icon> icon = Lazy.of(() -> new TagIcon(getFoodTag()));

    protected abstract TagKey<Item> getFoodTag();

    @OnlyIn(Dist.CLIENT)
    @Override
    public Icon getIcon(Mob mob, IMobStats<?> stats) {
        return stats.isHungry() ? icon.get().shadowed() : icon.get();
    }

    @Override
    public void onJoinWorld(Mob mob, IMobStats<?> stats) {
        mob.goalSelector.addGoal(3, new EatFoodGoal(mob, stats, getFoodTag()));
    }

    @Override
    public boolean onEntityInteract(Mob mob, IMobStats<?> stats, Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (!held.is(getFoodTag()))
            return false;
        if (stats.isHungry()) {
            stats.feed();
            stats.increaseHappiness(mob, Husbandry.HusbandryConfig.fedGain.get());
        }
        
        held.shrink(1);
        return true;
    }
}
