package uk.joshiejack.husbandry.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import uk.joshiejack.husbandry.world.entity.stats.MobStats;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.util.PenguinLoader;

import javax.annotation.Nonnull;

@PenguinLoader.Packet(PacketFlow.CLIENTBOUND)
public class SetHappinessPacket extends AbstractSetValuePacket {
    public static final ResourceLocation ID = PenguinLib.prefix("set_happiness");
    @Override
    public @Nonnull ResourceLocation id() {
        return ID;
    }

    public SetHappinessPacket(int entityID, int value) {
        super(entityID, value);
    }
    @SuppressWarnings("unused")
    public SetHappinessPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    public void handle(Mob mob, MobStats<?> stats) {
        stats.setHappiness(mob, value);
    }
}