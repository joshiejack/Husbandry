package uk.joshiejack.husbandry.network;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.packet.PenguinPacket;
import uk.joshiejack.penguinlib.util.PenguinLoader;

@PenguinLoader.Packet(PacketFlow.CLIENTBOUND)
public class SpawnHeartsPacket extends PenguinPacket {
    public static final ResourceLocation ID = PenguinLib.prefix("spawn_hearts");
    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    private final int entityID;
    private final boolean positive;

    public SpawnHeartsPacket(int entityID, boolean positive) {
        this.entityID = entityID;
        this.positive = positive;
    }

    @SuppressWarnings("unused")
    public SpawnHeartsPacket(FriendlyByteBuf from) {
        entityID = from.readInt();
        positive = from.readBoolean();
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeInt(entityID);
        to.writeBoolean(positive);
    }

    @Override
    public void handle(Player player) {
        Level world = player.level();
        Entity entity = world.getEntity(entityID);
        if (entity != null) {
            SimpleParticleType type = positive ? ParticleTypes.HEART : ParticleTypes.DAMAGE_INDICATOR;
            int times = positive ? 3 : 16;
            double offset = positive ? -0.125D : 0D;
            for (int j = 0; j < times; j++) {
                double x = (entity.xo - 0.5D) + world.random.nextFloat();
                double y = (entity.yo - 0.5D) + world.random.nextFloat();
                double z = (entity.zo - 0.5D) + world.random.nextFloat();
                world.addParticle(type, x, 1D + y + offset, z, 0, 0, 0);
            }
        }
    }
}
