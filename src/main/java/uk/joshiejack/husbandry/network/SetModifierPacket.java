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
public class SetModifierPacket extends AbstractSetValuePacket {
    public static final ResourceLocation ID = PenguinLib.prefix("set_modifier");
    @Override
    public @Nonnull ResourceLocation id() {
        return ID;
    }

    public SetModifierPacket(int entityID, int value) {
        super(entityID, value);
    }
    @SuppressWarnings("unused")
    public SetModifierPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    public void handle(Mob mob, MobStats<?> stats) {
        stats.setHappinessModifier(value);
    }
}