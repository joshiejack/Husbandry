package uk.joshiejack.husbandry.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import uk.joshiejack.husbandry.world.entity.stats.MobStats;
import uk.joshiejack.husbandry.world.entity.traits.TraitType;
import uk.joshiejack.husbandry.world.entity.traits.happiness.CleanableTrait;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.packet.PenguinPacket;
import uk.joshiejack.penguinlib.util.registry.Packet;

import javax.annotation.Nonnull;

@Packet(PacketFlow.CLIENTBOUND)
public class SetCleanedStatusPacket implements PenguinPacket {
    public static final ResourceLocation ID = PenguinLib.prefix("set_cleaned_status");
    @Override
    public @Nonnull ResourceLocation id() {
        return ID;
    }

    private final int entityID;
    private final boolean cleaned;

    public SetCleanedStatusPacket(int entityID, boolean cleaned) {
        this.entityID = entityID;
        this.cleaned = cleaned;
    }

    @SuppressWarnings("unused")
    public SetCleanedStatusPacket(FriendlyByteBuf from) {
        entityID = from.readInt();
        cleaned = from.readBoolean();
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeInt(entityID);
        to.writeBoolean(cleaned);
    }

    @Override
    public void handle(Player player) {
        Entity entity = player.level().getEntity(entityID);
        if (entity instanceof Mob mob) {
            MobStats<?> stats = MobStats.getStats(entity);
            if (stats != null)
                stats.getTraits(TraitType.DATA).stream()
                        .filter(s -> s instanceof CleanableTrait)
                        .findFirst()
                        .ifPresent(t -> ((CleanableTrait) t).setCleaned(mob, stats, cleaned));
        }
    }
}