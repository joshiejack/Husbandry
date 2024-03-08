package uk.joshiejack.husbandry.data;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.husbandry.world.block.HusbandryBlocks;
import uk.joshiejack.husbandry.world.item.HusbandryItems;

import java.util.Set;
import java.util.stream.Collectors;

public class HusbandryBlockLootTables extends BlockLootSubProvider {
    protected HusbandryBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return HusbandryBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::value).collect(Collectors.toList());
    }

    @Override
    protected void generate() {
        dropSelf(HusbandryBlocks.FEEDING_TRAY.get());
        dropSelf(HusbandryBlocks.BOWL.get());
        dropSelf(HusbandryBlocks.NEST.get());
        dropSelf(HusbandryBlocks.INCUBATOR.get());
        dropSelf(HusbandryBlocks.TROUGH.get());
        add(HusbandryBlocks.TRUFFLE_BLOCK.get(), (block) -> createSilkTouchDispatchTable(block, applyExplosionDecay(block, LootItem.lootTableItem(HusbandryItems.TRUFFLE.get())
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
    }
}
