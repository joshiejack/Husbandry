package uk.joshiejack.husbandry.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class HusbandryLootTables extends LootTableProvider {
    public HusbandryLootTables(PackOutput output) {
        super(output, Set.of(), List.of(new SubProviderEntry(HusbandryBlockLootTables::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(HusbandryProductLootTables::new, LootContextParamSets.ENTITY)));
    }
}