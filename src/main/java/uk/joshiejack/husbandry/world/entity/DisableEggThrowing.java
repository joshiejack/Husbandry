package uk.joshiejack.husbandry.world.entity;

import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import uk.joshiejack.husbandry.Husbandry;

@Mod.EventBusSubscriber(modid = Husbandry.MODID)
public class DisableEggThrowing {
    @SubscribeEvent
    public static void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getItemStack().getItem() == Items.EGG)
            event.setCanceled(true);
    }
}
