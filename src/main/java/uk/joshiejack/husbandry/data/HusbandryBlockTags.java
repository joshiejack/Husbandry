package uk.joshiejack.husbandry.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.world.entity.MobEventsHandler;

import java.util.concurrent.CompletableFuture;

public class HusbandryBlockTags extends BlockTagsProvider {
    public HusbandryBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Husbandry.MODID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(MobEventsHandler.PREVENTS_PASSENGER_DROP).addTags(BlockTags.BUTTONS, BlockTags.DOORS, BlockTags.FENCE_GATES, Tags.Blocks.CHESTS).add(Blocks.LEVER);
    }
}
