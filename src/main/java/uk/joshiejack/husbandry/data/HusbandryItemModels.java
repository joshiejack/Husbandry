package uk.joshiejack.husbandry.data;

import joptsimple.internal.Strings;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.world.item.HusbandryItems;

public class HusbandryItemModels extends ItemModelProvider {
    public HusbandryItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Husbandry.MODID, existingFileHelper);
    }

    private boolean isFeed(String path) {
        return path.contains("feed") || path.contains("food") || path.equals("fodder") | path.equals("slop");
    }

    @Override
    protected void registerModels() {
        HusbandryItems.ITEMS.getEntries().stream()
                .map(DeferredHolder::get)
                .forEach(item -> {
                    String path = BuiltInRegistries.ITEM.getKey(item).getPath();
                    if (item instanceof BlockItem)
                        getBuilder(path).parent(new ModelFile.UncheckedModelFile(modLoc("block/" + path)));
                    else {
                        if (path.contains("spawn_egg")) {
                            withExistingParent(path, mcLoc("item/template_spawn_egg"));
                        } else {
                            String subdir =
                                    item.getFoodProperties(new ItemStack(item), null) != null ? "food/"
                                            : path.contains("treat") ? "treat/"
                                            : isFeed(path) ? "feed/"
                                            : Strings.EMPTY;
                            singleTexture(path, mcLoc("item/generated"), "layer0", modLoc("item/" + subdir + path.replace("_treat", "")));
                        }
                    }
                });
    }
}
