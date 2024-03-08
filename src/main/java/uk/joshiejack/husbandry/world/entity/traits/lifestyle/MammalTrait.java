package uk.joshiejack.husbandry.world.entity.traits.lifestyle;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.util.Lazy;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.HusbandryAPI;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IDataTrait;
import uk.joshiejack.husbandry.api.trait.IIconTrait;
import uk.joshiejack.husbandry.api.trait.IInteractiveTrait;
import uk.joshiejack.husbandry.api.trait.INewDayTrait;
import uk.joshiejack.husbandry.world.item.HusbandryItems;
import uk.joshiejack.penguinlib.util.icon.Icon;
import uk.joshiejack.penguinlib.util.icon.ItemIcon;

public class MammalTrait implements IDataTrait, IInteractiveTrait, INewDayTrait, IIconTrait {
    public static final Lazy<Icon> ICON = Lazy.of(() -> new ItemIcon(new ItemStack(HusbandryItems.MIRACLE_POTION.get())));
    public static final TagKey<Item> IMPREGNATES_MAMMALS = ItemTags.create(new ResourceLocation(Husbandry.MODID, "impregnates_mammals"));
    private int gestation;//How many days this mob has been pregnant
    private boolean pregnant; //If the mob is pregnant

    @OnlyIn(Dist.CLIENT)
    @Override
    public Icon getIcon(Mob mob, IMobStats<?> stats) {
        return pregnant ? ICON.get() : ItemIcon.EMPTY;
    }

    @Override
    public void onNewDay(Mob mob, IMobStats<?> stats) {
        if (pregnant) {
            gestation--;
            if (gestation <= 0) {
                pregnant = false;
                giveBirth(mob, stats);
            }
        }
    }

    @Override
    public boolean onEntityInteract(Mob mob, IMobStats<?> stats, Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (held.is(IMPREGNATES_MAMMALS) && !pregnant && stats.getSpecies().daysToBirth() != 0) {
            pregnant = true;
            gestation = stats.getSpecies().daysToBirth();
            held.shrink(1);
            return true;
        }

        return false;
    }

    public boolean isPregnant() {
        return pregnant;
    }

    private void giveBirth(Mob mob, IMobStats<?> stats) {
        stats.increaseHappiness(mob, 100); //Happy to have a child
        int chance = mob.level().random.nextInt(100);
        int offspring = chance >= 99 ? 3 : chance >= 90 ? 2 : 1;
        for (int i = 0; i < offspring; i++) {
            if (mob instanceof AgeableMob) {
                AgeableMob ageable = ((AgeableMob) mob).getBreedOffspring((ServerLevel) mob.level(), (AgeableMob) mob);
                if (ageable != null) {
                    ageable.setAge(-Integer.MAX_VALUE);
                    ageable.setPos(mob.xo, mob.yo, mob.zo);
                    IMobStats<?> babyStats = HusbandryAPI.instance.getStatsForEntity(ageable);
                    if (babyStats != null)
                        babyStats.increaseHappiness(mob, stats.getHappiness() / 2);
                    mob.level().addFreshEntity(ageable);
                }
            } else {
                mob.getType().spawn((ServerLevel) mob.level(), null, (Player) null, mob.blockPosition(), MobSpawnType.BREEDING, true, true);
            }
        }
    }

    @Override
    public void save(CompoundTag tag) {
        tag.putInt("Gestation", gestation);
        tag.putBoolean("Pregnant", pregnant);
    }

    @Override
    public void load(CompoundTag nbt) {
        gestation = nbt.getInt("Gestation");
        pregnant = nbt.getBoolean("Pregnant");
    }
}
