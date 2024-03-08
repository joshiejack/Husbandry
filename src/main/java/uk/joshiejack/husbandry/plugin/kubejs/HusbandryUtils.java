//package uk.joshiejack.husbandry.plugin.kubejs;
//
//import net.minecraft.world.entity.Mob;
//import net.minecraft.world.entity.Entity;
//import uk.joshiejack.husbandry.api.HusbandryAPI;
//import uk.joshiejack.husbandry.api.IMobStats;
//
//import javax.annotation.Nullable;
//
//public class HusbandryUtils {
//    @Nullable
//    public static IMobStatsJS getMobStats(Entity entity) {
//        if (!(entity instanceof Mob mob)) return null;
//        IMobStats<?> stats = HusbandryAPI.instance.getStatsForEntity(mob);
//        return stats == null ? null : new IMobStatsJS(mob, stats);
//    }
//}