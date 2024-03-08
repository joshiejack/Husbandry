package uk.joshiejack.husbandry.world.entity.stats;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.*;
import uk.joshiejack.husbandry.network.SendDataPacket;
import uk.joshiejack.husbandry.network.SetHappinessPacket;
import uk.joshiejack.husbandry.network.SpawnHeartsPacket;
import uk.joshiejack.husbandry.world.entity.MobDataLoader;
import uk.joshiejack.husbandry.world.entity.ai.AbstractMoveToBlockGoal;
import uk.joshiejack.husbandry.world.entity.traits.TraitType;
import uk.joshiejack.husbandry.world.entity.traits.product.AbstractMobProductTrait;
import uk.joshiejack.husbandry.world.entity.traits.product.MoreProductChanceTrait;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.util.helper.MathHelper;
import uk.joshiejack.penguinlib.util.helper.ReflectionHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MobStats<E extends Mob> implements INBTSerializable<CompoundTag>, IMobStats<E> {
    private static final RandomSource RANDOM = RandomSource.create();
    private static final Lazy<Integer> MAX_RELATIONSHIP = Lazy.of(() -> Husbandry.HusbandryConfig.maxHappiness.get());
    private final ListMultimap<TraitType, IMobTrait> traits;
    protected final Species species;
    private int happiness; //Current mob happiness
    private int happinessDivisor = 5; //Maximum happiness for this mob currently, increased by treats
    private int hunger; //How many days the mob has been without food
    private boolean hasProduct = true; ///If the mob has a product
    private int childhood;//How many days of childhood so far
    protected boolean loved; //If the mob has been loved today
    private boolean eaten; //If the mob has eaten today
    private final AbstractMobProductTrait products;
    private boolean domesticated;

    public MobStats(@Nonnull Species species) {
        this.species = species;
        this.traits = LinkedListMultimap.create();
        this.species.traits().forEach(trait -> {
            IMobTrait copy = trait instanceof IDataTrait ? ReflectionHelper.newInstance(trait.getClass()) : trait;
            if (copy instanceof IJoinWorldTrait) this.traits.get(TraitType.ON_JOIN).add(copy);
            if (copy instanceof IInteractiveTrait) this.traits.get(TraitType.ACTION).add(copy);
            if (copy instanceof IBiHourlyTrait) this.traits.get(TraitType.BI_HOURLY).add(copy);
            if (copy instanceof IDataTrait) this.traits.get(TraitType.DATA).add(copy);
            if (copy instanceof INewDayTrait) this.traits.get(TraitType.NEW_DAY).add(copy);
            if (copy instanceof IRenderTrait) this.traits.get(TraitType.RENDER).add(copy);
            if (copy instanceof IInitTrait) this.traits.get(TraitType.INIT).add(copy);
            if (copy instanceof IIconTrait) this.traits.get(TraitType.ICON).add(copy);
        });

        this.traits.get(TraitType.INIT).forEach(trait -> ((IInitTrait)trait).initTrait(RANDOM, this));
        this.products = (AbstractMobProductTrait) this.traits.get(TraitType.DATA).stream()
                .filter(t -> t instanceof AbstractMobProductTrait).findFirst().orElse(new MoreProductChanceTrait());
    }

    @Override
    public Species getSpecies() {
        return species;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getTraits(TraitType type) {
        return (List<T>) traits.get(type);
    }

    @Override
    public void resetProduct(Mob mob) {
        mob.ate();
        hasProduct = true;
    }

    public void onNewDay(Mob mob) {
        List<INewDayTrait> traits = getTraits(TraitType.NEW_DAY);
        traits.forEach(trait -> trait.onNewDay(mob, this));

        if (!eaten) {
            hunger++;
            decreaseHappiness(mob, Husbandry.HusbandryConfig.hungerHappinessLoss.get());
        }

        loved = false;
        eaten = false;

        if (mob.isBaby()) {
            childhood++;

            if (childhood >= species.daysToMaturity()) {
                mob.setBaby(false);
                if (mob instanceof Slime) {
                    try {
                        ObfuscationReflectionHelper.findMethod(Slime.class, "setSize", int.class, boolean.class).invoke(2, true);
                    } catch (IllegalAccessException | InvocationTargetException ignored) {}
                }
            }
        }

        //Force the mobs to execute important ai tasks?
        mob.goalSelector.getRunningGoals()
                .filter(ai -> ai.getGoal() instanceof AbstractMoveToBlockGoal)
                .forEach(ai -> ((AbstractMoveToBlockGoal) ai.getGoal()).resetRunTimer());
        PenguinNetwork.sendToNearby(new SendDataPacket(mob.getId(), this), mob);
    }

    @Override
    public boolean isDomesticated() {
        return domesticated;
    }

    @Override
    public int getHappiness() {
        return happiness;
    }

    @Override
    public void setHappiness(Mob mob, int happiness) {
        this.happiness = happiness;
        if (!mob.level().isClientSide)
            PenguinNetwork.sendToNearby(new SetHappinessPacket(mob.getId(), happiness), mob);
    }

    @Override
    public int getHappinessModifier() {
        return happinessDivisor;
    }

    @Override
    public void setHappinessModifier(int value) {
        happinessDivisor = Math.max(1, Math.min(5, value));
    }

    @Override
    public void decreaseHappiness(Mob mob, int happiness) {
        this.happiness = MathHelper.constrainToRangeInt(this.happiness - happiness, 0, MAX_RELATIONSHIP.get() / happinessDivisor);
        if (!mob.level().isClientSide) {
            PenguinNetwork.sendToNearby(new SpawnHeartsPacket(mob.getId(), false), mob);
        }
    }

    @Override
    public void increaseHappiness(Mob mob, int happiness) {
        this.happiness = MathHelper.constrainToRangeInt(this.happiness + happiness, 0, MAX_RELATIONSHIP.get() / happinessDivisor);
        if (!mob.level().isClientSide) {
            PenguinNetwork.sendToNearby(new SpawnHeartsPacket(mob.getId(), true), mob);
        }
    }

    /**
     * @return true if the event should be canceled
     */
    public boolean onEntityInteract(Mob mob, Player player, InteractionHand hand) {
        domesticated = true; //Interaction started, so mark them as domesticated, maybe make this more complicated later
        List<IInteractiveTrait> traits = getTraits(TraitType.ACTION);
        return traits.stream().anyMatch(trait ->
                trait.onEntityInteract(mob, this, player, hand));
    }

    @Override
    public void feed() {
        hunger = 0;
        eaten = true;
    }

    @Override
    public int getHunger() {
        return hunger;
    }

    @Override
    public boolean isUnloved() {
        return !loved;
    }

    @Override
    public boolean setLoved(Mob mob) {
        this.loved = true;
        increaseHappiness(mob, Husbandry.HusbandryConfig.lovedGain.get());
        return this.loved;
    }

    @Override
    public boolean canProduceProduct(Mob mob) {
        return hasProduct && !mob.isBaby();
    }

    @Override
    public void setProduced(Mob mob, int amount) {
        this.products.setProduced(mob, this, amount);
        this.hasProduct = false;
    }

    @Override
    public boolean isHungry() {
        return !eaten;
    }

    @Override
    public int getMaxHearts() {
        return (5 - (happinessDivisor - 1)) * 2;
    }

    @Override
    public int getHearts() {
        return (int) ((((double) happiness) / MAX_RELATIONSHIP.get()) * 10); //0 > 10
    }

    @Override
    public void setHearts(Mob mob, int hearts) {
        hearts = Math.max(0, Math.min(10, hearts));
        this.happiness = hearts * (MAX_RELATIONSHIP.get()/ 10);
        if (!mob.level().isClientSide)
            PenguinNetwork.sendToNearby(new SetHappinessPacket(mob.getId(), happiness), mob);
    }

    @Override
    public int getMaxHappiness() {
        return MAX_RELATIONSHIP.get();
    }

    @Nullable
    public static MobStats<?> getStats(Entity entity) {
        if (!Species.TYPES.containsKey(entity.getType()) || !(entity instanceof Mob)) return null;
        if (entity.hasData(MobDataLoader.MOB_STATS.get())) return entity.getData(MobDataLoader.MOB_STATS.get());
        else {
            MobStats<Mob> stats = new MobStats<>(Species.TYPES.get(entity.getType()));
            entity.setData(MobDataLoader.MOB_STATS.get(), stats);
            return stats;
        }
    }

    @Override
    public @NotNull CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("Domesticated", domesticated);
        tag.putInt("Happiness", happiness);
        tag.putByte("HappinessDivisor", (byte) happinessDivisor);
        tag.putInt("Hunger", hunger);
        tag.putInt("Childhood", childhood);
        tag.putBoolean("HasProduct", hasProduct);
        tag.putBoolean("Loved", loved);
        tag.putBoolean("Eaten", eaten);
        List<IDataTrait> data = getTraits(TraitType.DATA);
        data.forEach(d -> d.save(tag));
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        domesticated = nbt.getBoolean("Domesticated");
        happiness = nbt.getInt("Happiness");
        happinessDivisor = nbt.getByte("HappinessDivisor");
        if (happinessDivisor <= 1) happinessDivisor = 1;
        if (happinessDivisor >= 5) happinessDivisor = 5;
        hunger = nbt.getInt("Hunger");
        childhood = nbt.getInt("Childhood");
        hasProduct = nbt.getBoolean("HasProduct");
        loved = nbt.getBoolean("Loved");
        eaten = nbt.getBoolean("Eaten");
        List<IDataTrait> data = getTraits(TraitType.DATA);
        data.forEach(d -> d.load(nbt));
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof MobStats<?>;
    }

    @Override
    public int hashCode() {
        return 123456789;
    }
}
