package uk.joshiejack.husbandry.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Mob;
import org.apache.commons.lang3.mutable.MutableBoolean;
import uk.joshiejack.husbandry.world.entity.stats.MobStats;

public class SetHeartsCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("hearts")
                .then(Commands.argument("entity", EntityArgument.entities())
                .then(Commands.argument("amount", IntegerArgumentType.integer(0, 10))
                .executes(ctx -> {
                    MutableBoolean success = new MutableBoolean(false);
                    EntityArgument.getEntities(ctx, "entity").forEach(entity -> {
                        if (!(entity instanceof Mob mob) ) return;
                        int hearts = IntegerArgumentType.getInteger(ctx, "amount");
                        MobStats<?> stats = MobStats.getStats(entity);
                        if (stats == null) return;
                        stats.setHearts(mob, hearts);
                        success.setTrue();
                    });

                    return success.isTrue() ? 1 : 0;
                })));
    }
}
