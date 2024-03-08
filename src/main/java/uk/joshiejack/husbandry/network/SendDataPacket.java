package uk.joshiejack.husbandry.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import uk.joshiejack.husbandry.world.entity.stats.MobStats;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.packet.SyncCompoundTagPacket;
import uk.joshiejack.penguinlib.util.PenguinLoader;

import javax.annotation.Nonnull;

@PenguinLoader.Packet(PacketFlow.CLIENTBOUND)
public class SendDataPacket extends SyncCompoundTagPacket {
    public static final ResourceLocation ID = PenguinLib.prefix("send_husbandry_data");
    @Override
    public @Nonnull ResourceLocation id() {
        return ID;
    }

    private final int entityID;

    public SendDataPacket(int entityID, MobStats<?> stats) {
        super(stats.serializeNBT());
        this.entityID = entityID;
    }

    @SuppressWarnings("unused")
    public SendDataPacket(FriendlyByteBuf buf) {
        super(buf);
        entityID = buf.readInt();
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        super.write(buf);
        buf.writeInt(entityID);
    }



    @Override
    public void handle(Player player) {
        Entity entity = player.level().getEntity(entityID);
        if (entity != null) {
            MobStats<?> stats = MobStats.getStats(entity);
            if (stats != null)
                stats.deserializeNBT(tag);
        }
    }
}
