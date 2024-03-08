package uk.joshiejack.husbandry.api;

import net.minecraft.world.entity.Mob;
import uk.joshiejack.husbandry.api.trait.IMobTrait;

import javax.annotation.Nullable;

public class HusbandryAPI {
    public static IHusbandryAPI instance = null;

    public interface IHusbandryAPI {
        /**
         * Register a new type of mob trait that can be added to mobs
         * @param name      the name to register it as, used in the database
         * @param trait     the class to register as the trait
         */
        void registerMobTrait(String name, Class<? extends IMobTrait> trait);

        /**
         * Get the stats for this entity if they have one
         * @param entity    the entity
         * @param <E>       the type of mob entity
         * @return          the mob stats for this entity
         */
        @Nullable
        <E extends Mob> IMobStats<E> getStatsForEntity(E entity);
    }
}