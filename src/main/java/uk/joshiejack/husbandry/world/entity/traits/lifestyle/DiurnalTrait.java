package uk.joshiejack.husbandry.world.entity.traits.lifestyle;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IBiHourlyTrait;
import uk.joshiejack.husbandry.api.trait.IDataTrait;
import uk.joshiejack.husbandry.api.trait.IJoinWorldTrait;
import uk.joshiejack.husbandry.world.entity.ai.ShelterAtNightGoal;

public class DiurnalTrait implements IBiHourlyTrait, IDataTrait, IJoinWorldTrait {
    private boolean wasOutsideInSun; //If the mob was outside last time

    @Override
    public void onBihourlyTick(Mob mob, IMobStats<?> stats) {
        Level world = mob.level();
        BlockPos pos = mob.blockPosition();
        boolean dayTime = world.isDay();
        boolean isRaining = world.isRaining();
        boolean isOutside = world.canSeeSky(pos);
        boolean isOutsideInSun = !isRaining && isOutside && dayTime && !world.getBiome(pos).value().shouldSnow(world, pos);
        if (isOutsideInSun && wasOutsideInSun) {
            stats.increaseHappiness(mob, 2);
        }

        //Mark the past value
        wasOutsideInSun = isOutsideInSun;
    }

    @Override
    public void onJoinWorld(Mob mob, IMobStats<?> stats) {
        mob.goalSelector.addGoal(4, new ShelterAtNightGoal(mob, stats));
    }

    @Override
    public void load(CompoundTag nbt) {
        wasOutsideInSun = nbt.getBoolean("InSun");
    }

    @Override
    public void save(CompoundTag tag) {
        tag.putBoolean("InSun", wasOutsideInSun);
    }
}