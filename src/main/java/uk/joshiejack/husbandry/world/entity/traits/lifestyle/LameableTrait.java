package uk.joshiejack.husbandry.world.entity.traits.lifestyle;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IDataTrait;
import uk.joshiejack.husbandry.api.trait.IIconTrait;
import uk.joshiejack.husbandry.api.trait.IInteractiveTrait;
import uk.joshiejack.husbandry.api.trait.IJoinWorldTrait;
import uk.joshiejack.husbandry.world.entity.traits.happiness.AbstractLoveableTrait;
import uk.joshiejack.penguinlib.util.icon.Icon;
import uk.joshiejack.penguinlib.util.icon.ItemIcon;
import uk.joshiejack.penguinlib.util.icon.TextureIcon;

import java.util.Objects;

public class LameableTrait implements IIconTrait, IJoinWorldTrait, IInteractiveTrait, IDataTrait {
    public static final Icon ICON = new TextureIcon(AbstractLoveableTrait.HUSBANDRY_ICONS, 0, 0, 1);
    private boolean lamed;

    @OnlyIn(Dist.CLIENT)
    @Override
    public Icon getIcon(Mob mob, IMobStats<?> stats) {
        return lamed ? ICON : ItemIcon.EMPTY;
    }

    @Override
    public void onJoinWorld(Mob mob, IMobStats<?> stats) {
        if (lamed)
           lame(mob);
    }

    @Override
    public boolean onEntityInteract(Mob mob, IMobStats<?> stats, Player player, InteractionHand hand) {
        lame(mob);
        return lamed = true;
    }

    private void lame(Mob entity) {
        Objects.requireNonNull(entity.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(0D);
        entity.targetSelector.disableControlFlag(Goal.Flag.TARGET);
        entity.setPersistenceRequired();
    }

    @Override
    public void load(CompoundTag nbt) {
        lamed = nbt.getBoolean("Lamed");
    }

    @Override
    public void save(CompoundTag nbt) {
        nbt.putBoolean("Lamed", lamed);
    }
}
