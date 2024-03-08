package uk.joshiejack.husbandry.world.entity.traits.lifestyle;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Mob;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.ISpecies;
import uk.joshiejack.husbandry.api.trait.IDataTrait;
import uk.joshiejack.husbandry.api.trait.INewDayTrait;
import uk.joshiejack.penguinlib.util.helper.MathHelper;

public class MortalTrait implements INewDayTrait, IDataTrait {
    public static final DeferredRegister<DamageType> DAMAGE_SOURCES = DeferredRegister.create(Registries.DAMAGE_TYPE, Husbandry.MODID);
    public static final DeferredHolder<DamageType, DamageType> OLD_AGE = DAMAGE_SOURCES.register("oldage", () -> new DamageType("oldage", 0F));
    private static final int DEATH_CHANCE = 360;
    private int age;

    @Override
    public void onNewDay(Mob mob, IMobStats<?> stats) {
        int chance = MathHelper.constrainToRangeInt(DEATH_CHANCE, 1, Short.MAX_VALUE);
        ISpecies species = stats.getSpecies();
        if (age >= species.maximumLifespan() || (age >= species.minimumLifespan() && mob.getRandom().nextInt(chance) == 0)) {
            mob.hurt(new DamageSource(OLD_AGE, mob), Integer.MAX_VALUE);
        }
    }

    @Override
    public void load(CompoundTag nbt) {
        age = nbt.getInt("Age");
    }

    @Override
    public void save(CompoundTag nbt) {
        nbt.putInt("Age", age);
    }
}
