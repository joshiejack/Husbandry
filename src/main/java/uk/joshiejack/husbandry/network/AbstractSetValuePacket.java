package uk.joshiejack.husbandry.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.world.entity.stats.MobStats;
import uk.joshiejack.penguinlib.network.packet.PenguinPacket;

public abstract class AbstractSetValuePacket implements PenguinPacket {
    private final int entityID;
    protected final int value;

    public AbstractSetValuePacket(int entityID, int value) {
        this.entityID = entityID;
        this.value = value;
    }

    public AbstractSetValuePacket(FriendlyByteBuf buf) {
        entityID = buf.readInt();
        value = buf.readInt();
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityID);
        buf.writeInt(value);
    }

    private void handlePacket(Player player) {
        Entity entity = player.level().getEntity(entityID);
        if (entity instanceof Mob mob) {
            MobStats<?> stats = MobStats.getStats(entity);
            if (stats != null)
                handle(mob, stats);
        }
    }

    @Override
    public void handleServer(ServerPlayer player) {
        handlePacket(player);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleClient() {
        assert Minecraft.getInstance().player != null;
        handlePacket(Minecraft.getInstance().player);
    }

    protected abstract void handle(Mob mob, MobStats<?> stats);
}
