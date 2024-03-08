package uk.joshiejack.husbandry.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import org.apache.commons.lang3.mutable.MutableBoolean;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.world.entity.stats.MobStats;

public class SetHappinessCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("happiness")
                .then(Commands.argument("entity", EntityArgument.entities())
                .then(Commands.argument("amount", IntegerArgumentType.integer(0, Integer.MAX_VALUE))
                .executes(ctx -> {
                    MutableBoolean success = new MutableBoolean(false);
                    EntityArgument.getEntities(ctx, "entity").forEach(entity -> {
                        if (!(entity instanceof Mob mob)) return;
                        int happiness = IntegerArgumentType.getInteger(ctx, "amount");
                        if (happiness > Husbandry.HusbandryConfig.maxHappiness.get()) {
                            ctx.getSource().sendFailure(Component.literal(Husbandry.HusbandryConfig.maxHappiness.get() + " is the maximum happiness")); //TODO: Translate
                        }
                        MobStats<?> stats = MobStats.getStats(entity);
                        if (stats == null) return;
                        stats.setHappiness(mob, happiness);
                        success.setTrue();
                    });

                    return success.isTrue() ? 1 : 0;
                })));
    }
}
