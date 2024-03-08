package uk.joshiejack.husbandry.world.entity.traits.happiness;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.Lazy;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IDataTrait;
import uk.joshiejack.husbandry.api.trait.IIconTrait;
import uk.joshiejack.husbandry.api.trait.IInteractiveTrait;
import uk.joshiejack.husbandry.api.trait.INewDayTrait;
import uk.joshiejack.husbandry.network.SetCleanedStatusPacket;
import uk.joshiejack.husbandry.world.item.HusbandryItems;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.util.helper.MathHelper;
import uk.joshiejack.penguinlib.util.icon.Icon;
import uk.joshiejack.penguinlib.util.icon.ItemIcon;

public class CleanableTrait implements IDataTrait, IInteractiveTrait, INewDayTrait, IIconTrait {
    private static final Lazy<Icon> ICON = Lazy.of(() -> new ItemIcon(new ItemStack(HusbandryItems.BRUSH.get())));
    private int cleanliness;
    private boolean cleaned;

    @Override
    public void onNewDay(Mob mob, IMobStats<?> stats) {
        setCleaned(mob, stats, false);
        cleanliness = MathHelper.constrainToRangeInt(cleanliness - 10, -100, 100);
        if (cleanliness <= 0) {
            stats.decreaseHappiness(mob, Husbandry.HusbandryConfig.dirtyHappinessLoss.get()); //We dirty, so we no happy
        }
    }

    @Override
    public Icon getIcon(Mob mob, IMobStats<?> stats) {
        return cleaned ? ICON.get() : ICON.get().shadowed();
    }

    @Override
    public boolean onEntityInteract(Mob mob, IMobStats<?> stats, Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).getItem() == HusbandryItems.BRUSH.get() && clean(mob, stats)) {
            Level world = player.level();
            if (world.isClientSide) {
                if (world.getDayTime() % 3 ==0) player.swing(hand);
                for (int j = 0; j < 30D; j++) {
                    double x = (mob.xo - 0.5D) + world.random.nextFloat();
                    double y = (mob.yo - 0.5D) + world.random.nextFloat();
                    double z = (mob.zo - 0.5D) + world.random.nextFloat();
                    world.addParticle(ParticleTypes.CRIT, x, 1D + y, z, 0, 0, 0);
                }
            }

            world.playSound(player, player.xo, player.yo, player.zo, Husbandry.HusbandrySounds.BRUSH.get(), SoundSource.PLAYERS, 1.5F, player.level().random.nextFloat() * 0.1F + 0.9F);
            return true;
        }

        return false;
    }

    private boolean clean(Mob mob, IMobStats<?> stats) {
        if (!cleaned) {
            cleanliness++;
            if (cleanliness == 50) {
                setCleaned(mob, stats, true);
            }

            return true;
        }

        return false;
    }

    public void setCleaned(Mob mob, IMobStats<?> stats, boolean cleaned) {
        if (mob.level().isClientSide) this.cleaned = cleaned;
        else {
            this.cleaned = cleaned;
            if (cleaned) {
                stats.increaseHappiness(mob, Husbandry.HusbandryConfig.cleanedGain.get());
            }

            PenguinNetwork.sendToNearby(new SetCleanedStatusPacket(mob.getId(), cleaned), mob);
        }
    }

    @Override
    public void load(CompoundTag nbt) {
        cleanliness = nbt.getInt("Cleanliness");
        cleaned = nbt.getBoolean("Cleaned");
    }

    @Override
    public void save(CompoundTag tag) {
        tag.putInt("Cleanliness", cleanliness);
        tag.putBoolean("Cleaned", cleaned);
    }
}