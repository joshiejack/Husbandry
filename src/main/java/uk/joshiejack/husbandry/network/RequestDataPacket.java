package uk.joshiejack.husbandry.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import uk.joshiejack.husbandry.world.entity.stats.MobStats;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.network.packet.PenguinPacket;
import uk.joshiejack.penguinlib.util.registry.Packet;

import javax.annotation.Nonnull;

@Packet(PacketFlow.SERVERBOUND)
public class RequestDataPacket implements PenguinPacket {
    public static final ResourceLocation ID = PenguinLib.prefix("request_husbandry_data");
    @Override
    public @Nonnull ResourceLocation id() {
        return ID;
    }


    private final int entityID;

    public RequestDataPacket(int entityID) {
        this.entityID = entityID;
    }

    @SuppressWarnings("unused")
    public RequestDataPacket(FriendlyByteBuf from) {
        entityID = from.readInt();
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeInt(entityID);
    }


    @Override
    public void handleServer(ServerPlayer player) {
        Entity entity = player.level().getEntity(entityID);
        if (entity != null) {
            MobStats<?> stats = MobStats.getStats(entity);
            if (stats != null)
                PenguinNetwork.sendToClient(player, new SendDataPacket(entityID, stats));
        }
    }
}
