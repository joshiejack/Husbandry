package uk.joshiejack.husbandry.world.entity.stats;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import org.apache.commons.lang3.tuple.Pair;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.trait.IBiHourlyTrait;
import uk.joshiejack.husbandry.world.entity.traits.TraitType;
import uk.joshiejack.penguinlib.event.NewDayEvent;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Husbandry.MODID)
public class MobStatsTicker {
    private static final Multimap<ResourceKey<Level>, Pair<Mob, MobStats<?>>> stats = HashMultimap.create();

    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel level)
            stats.get((level).dimension()).clear();
    }

    @SubscribeEvent
    public static void onNewDay(NewDayEvent event) {
        try {
            Lists.newArrayList(stats.get(event.getLevel().dimension())).stream()
                    .filter(p -> p.getLeft().isAlive())
                    .forEach(p -> p.getRight().onNewDay(p.getLeft()));
        } catch (Exception ex) { Husbandry.LOGGER.warn("Husbandry encountered an issue when ticking animals for the day... Try not to worry.");}
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        if (event.level.isClientSide) return;
        if (event.level.getGameTime() % 2000 == 0) //Every 2 hours
            try {
                Lists.newArrayList(stats.get(event.level.dimension())).stream()
                        .filter(s -> s.getLeft().isAlive())
                        .forEach(stats -> {
                            List<IBiHourlyTrait> traits = stats.getRight().getTraits(TraitType.BI_HOURLY);
                            traits.forEach(trait -> trait.onBihourlyTick(stats.getLeft(), stats.getRight()));
                        });
            } catch (Exception ex) { Husbandry.LOGGER.warn("Husbandry encountered an issue when ticking animals every two hours... No reason to be concerned!"); }
    }

    private static void run(Entity mob, Consumer<MobStats<?>> consumer) {
        if (mob.level().isClientSide) return;
        MobStats<?> stats = MobStats.getStats(mob);
        if (stats == null) return;
        consumer.accept(stats);
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Mob mob)
            run(mob, (ms) -> stats.get(event.getLevel().dimension()).add(Pair.of(mob, ms)));
    }

    @SubscribeEvent
    public static void onEntityLeaveWorld(EntityLeaveLevelEvent event) {
        if (event.getEntity() instanceof Mob mob)
            run(mob, (ms) -> stats.get(event.getLevel().dimension()).remove(Pair.of(mob, ms)));
    }
}