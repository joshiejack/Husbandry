package uk.joshiejack.husbandry.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.husbandry.entity.traits.types.IJoinWorldTrait;
import uk.joshiejack.husbandry.entity.traits.types.IMobTrait;

import java.util.stream.Stream;

import static uk.joshiejack.husbandry.Husbandry.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
public class MobEventsHandler {
    public static final ITag.INamedTag<Block> PREVENTS_PASSENGER_DROP = BlockTags.createOptional(new ResourceLocation(MODID, "prevents_passenger_drop"));

    @SubscribeEvent
    public static void onEntityJoinedWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (!entity.level.isClientSide && entity instanceof MobEntity) {
            MobStats<?> stats = MobStats.getStats(entity);
            if (stats != null) {
                MobEntity mob = ((MobEntity) entity);
                Stream<IJoinWorldTrait> traits = stats.getTraits(IMobTrait.Type.ON_JOIN);
                traits.forEach(trait -> trait.onJoinWorld(stats));
            }
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getPlayer().getPassengers().size() > 0 && event.getHand() == Hand.MAIN_HAND) {
            dismountMobs(event.getPlayer());
        } else {
            MobStats<?> stats = MobStats.getStats(event.getTarget());
            if (stats != null) {
                int happiness = stats.getHappiness();
                boolean canceled = stats.onRightClick(event.getPlayer(), event.getHand());
                if (canceled) {
                    int newHappiness = stats.getHappiness();
                    if (newHappiness != happiness)
                        event.getPlayer().giveExperiencePoints((int) Math.ceil((newHappiness - happiness)/100D));
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRightClickGround(PlayerInteractEvent.RightClickBlock event) {
        if (!PREVENTS_PASSENGER_DROP.contains(event.getWorld().getBlockState(event.getPos()).getBlock()))
            dismountMobs(event.getPlayer());
    }

    @SubscribeEvent //Automatically dismount any entities than are on top of a player
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        dismountMobs(event.getPlayer()); //TODO TEST
    }

    private static void dismountMobs(PlayerEntity player) {
        player.getPassengers().stream()
                .filter(entity -> MobStats.getStats(entity) != null)
                .forEach(entity -> {
                    entity.stopRiding();
                    entity.yRot = player.yRot;
                    entity.xRot = player.xRot;
                    entity.moveRelative(0F, new Vector3d(0.1F, 1.05F, 0.1F));
                    entity.setInvulnerable(false);
                });
    }
}