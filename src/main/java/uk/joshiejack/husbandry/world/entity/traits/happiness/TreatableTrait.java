package uk.joshiejack.husbandry.world.entity.traits.happiness;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IDataTrait;
import uk.joshiejack.husbandry.api.trait.IIconTrait;
import uk.joshiejack.husbandry.api.trait.IInteractiveTrait;
import uk.joshiejack.husbandry.api.trait.INewDayTrait;
import uk.joshiejack.husbandry.world.item.HusbandryItems;
import uk.joshiejack.penguinlib.util.icon.Icon;
import uk.joshiejack.penguinlib.util.icon.ItemIcon;

public class TreatableTrait implements IDataTrait, IInteractiveTrait, INewDayTrait, IIconTrait {
    public static final TagKey<Item> TREATS = ItemTags.create(new ResourceLocation(Husbandry.MODID, "treat"));
    private boolean treated; //If the mob has been treated
    private int genericTreatsGiven;
    private int speciesTreatsGiven;
    private boolean annoyed; //If the player has insulted the mob today
    private Icon icon;


    @OnlyIn(Dist.CLIENT)
    @Override
    public Icon getIcon(Mob mob, IMobStats<?> stats) {
        if (icon == null)
            icon = new ItemIcon(new ItemStack(stats.getSpecies().treat()));
        return treated ? icon : icon.shadowed();
    }

    @Override
    public void onNewDay(Mob mob, IMobStats<?> stats) {
        if (stats.getHappinessModifier() > 1 && genericTreatsGiven >= stats.getSpecies().genericTreats()
                && speciesTreatsGiven >= stats.getSpecies().speciesTreats()) {
            genericTreatsGiven -= stats.getSpecies().genericTreats();
            speciesTreatsGiven -= stats.getSpecies().speciesTreats();
            stats.setHappinessModifier(stats.getHappinessModifier() - 1);
        }

        treated = false;
        annoyed = false;
    }

    @Override
    public boolean onEntityInteract(Mob mob, IMobStats<?> stats, Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (!held.is(TREATS)) return false;
        boolean generic = held.getItem() == HusbandryItems.GENERIC_TREAT.get();
        if (!generic) { //Feeding the wrong treat will upset them!
            if (stats.getSpecies().treat() != held.getItem() && !annoyed) {
                annoyed = true;
                stats.decreaseHappiness(mob, Husbandry.HusbandryConfig.wrongTreatLoss.get());
                held.shrink(1);
                return true;
            }
        }

        //Feeding the correct treat makes them happy
        //But only if they haven't been fed already today
        if (!treated) {
            if (generic) {
                genericTreatsGiven++;
            } else speciesTreatsGiven++;

            held.shrink(1); //Remove it
            stats.increaseHappiness(mob, generic ? Husbandry.HusbandryConfig.typeTreatGain.get() : Husbandry.HusbandryConfig.genericTreatGain.get());
            treated = true;
            return true;
        }

        return false;
    }

    @Override
    public void load(CompoundTag nbt) {
        treated = nbt.getBoolean("Treated");
        genericTreatsGiven = nbt.getInt("GenericTreats");
        speciesTreatsGiven = nbt.getInt("TypeTreats");
        annoyed = nbt.getBoolean("Annoyed");
    }

    @Override
    public void save(CompoundTag nbt) {
        nbt.putBoolean("Treated", treated);
        nbt.putBoolean("Annoyed", annoyed);
        nbt.putInt("GenericTreats", genericTreatsGiven);
        nbt.putInt("TypeTreats", speciesTreatsGiven);
    }
}