package uk.joshiejack.husbandry.world.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.joshiejack.husbandry.Husbandry;

public class HusbandryCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Husbandry.MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> HUSBANDRY = CREATIVE_MODE_TABS.register(Husbandry.MODID, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + Husbandry.MODID))
            .icon(() -> new ItemStack(HusbandryItems.BRUSH.asItem()))
            .displayItems((params, output) -> HusbandryItems.ITEMS.getEntries().stream()
                    .map(DeferredHolder::get)
                    .forEach(output::accept)).build());
}
