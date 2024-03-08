package uk.joshiejack.husbandry.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import uk.joshiejack.husbandry.Husbandry;

@Mod.EventBusSubscriber(modid = Husbandry.MODID)
public class HusbandryCommands {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                LiteralArgumentBuilder.<CommandSourceStack>literal(Husbandry.MODID)
                        .requires(cs -> cs.hasPermission(2))
                        .then(SetHeartsCommand.register())
                        .then(SetHappinessCommand.register())
                        .then(SetModifierCommand.register())

        );
    }
}