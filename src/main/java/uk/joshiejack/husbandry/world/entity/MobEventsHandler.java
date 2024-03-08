package uk.joshiejack.husbandry.world.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.trait.IJoinWorldTrait;
import uk.joshiejack.husbandry.world.entity.stats.MobStats;
import uk.joshiejack.husbandry.world.entity.traits.TraitType;

import java.util.List;

import static uk.joshiejack.husbandry.Husbandry.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
public class MobEventsHandler {
    public static final TagKey<Block> PREVENTS_PASSENGER_DROP = BlockTags.create(new ResourceLocation(MODID, "prevents_passenger_drop"));

    @SubscribeEvent
    public static void onEntityJoinedWorld(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (!entity.level().isClientSide && entity instanceof Mob mob) {
            MobStats<?> stats = MobStats.getStats(entity);
            if (stats != null) {
                List<IJoinWorldTrait> traits = stats.getTraits(TraitType.ON_JOIN);
                traits.forEach(trait -> trait.onJoinWorld(mob, stats));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Mob mob) {
            MobStats<?> stats = MobStats.getStats(event.getEntity());
            if (stats != null)
                stats.decreaseHappiness(mob, (int) (Husbandry.HusbandryConfig.hurtHappinessLossModifier.get() * event.getAmount()));
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!event.getEntity().getPassengers().isEmpty() && event.getHand() == InteractionHand.MAIN_HAND) {
            dismountMobs(event.getEntity());
        } else if (event.getTarget() instanceof Mob mob) {
            MobStats<?> stats = MobStats.getStats(mob);
            if (stats != null) {
                int happiness = stats.getHappiness();
                boolean canceled = stats.onEntityInteract(mob, event.getEntity(), event.getHand());
                if (canceled) {
                    int newHappiness = stats.getHappiness();
                    if (newHappiness != happiness)
                        event.getEntity().giveExperiencePoints((int) Math.ceil((newHappiness - happiness)/100D));
                    event.setCancellationResult(InteractionResult.SUCCESS);
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRightClickGround(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getLevel().getBlockState(event.getPos()).is(PREVENTS_PASSENGER_DROP))
            dismountMobs(event.getEntity());
    }

    @SubscribeEvent //Automatically dismount any entities than are on top of a player
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        dismountMobs(event.getEntity());
    }

    private static void dismountMobs(Player player) {
        player.getPassengers().stream()
                .filter(entity -> MobStats.getStats(entity) != null)
                .forEach(entity -> {
                    entity.stopRiding();
                    entity.setYRot(player.getYRot());
                    entity.setXRot(player.getXRot());
//                    entity.yRot = player.yRot;
//                    entity.xRot = player.xRot;
                    entity.moveRelative(0F, new Vec3(0.1F, 1.05F, 0.1F));
                    entity.setInvulnerable(false);
                });
    }
}
