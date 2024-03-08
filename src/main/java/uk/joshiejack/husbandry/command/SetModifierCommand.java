package uk.joshiejack.husbandry.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Mob;
import org.apache.commons.lang3.mutable.MutableBoolean;
import uk.joshiejack.husbandry.world.entity.stats.MobStats;
import uk.joshiejack.husbandry.network.SetModifierPacket;
import uk.joshiejack.penguinlib.network.PenguinNetwork;

public class SetModifierCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("modifier")
                .then(Commands.argument("entity", EntityArgument.entities())
                .then(Commands.argument("amount", IntegerArgumentType.integer(1, 5))
                .executes(ctx -> {
                    MutableBoolean success = new MutableBoolean(false);
                    EntityArgument.getEntities(ctx, "entity").forEach(entity -> {
                        if (!(entity instanceof Mob)) return;
                        int amount = IntegerArgumentType.getInteger(ctx, "amount");
                        MobStats<?> stats = MobStats.getStats(entity);
                        if (stats == null) return;
                        stats.setHappinessModifier(amount);
                        PenguinNetwork.sendToNearby(entity, new SetModifierPacket(entity.getId(), amount));
                        success.setTrue();
                    });

                    return success.isTrue() ? 1 : 0;
                })));
    }
}
