package uk.joshiejack.husbandry.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import uk.joshiejack.husbandry.world.entity.stats.MobStats;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.util.registry.Packet;

import javax.annotation.Nonnull;

@Packet(PacketFlow.CLIENTBOUND)
public class SetProducedProductPacket extends AbstractSetValuePacket {
    public static final ResourceLocation ID = PenguinLib.prefix("set_produced_product");
    @Override
    public @Nonnull ResourceLocation id() {
        return ID;
    }

    public SetProducedProductPacket(int entityID, int value) {
        super(entityID, value);
    }
    @SuppressWarnings("unused")
    public SetProducedProductPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    public void handle(Mob mob, MobStats<?> stats) {
        stats.setProduced(mob, value);
    }
}