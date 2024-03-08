package uk.joshiejack.husbandry.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.world.entity.traits.food.*;
import uk.joshiejack.husbandry.world.entity.traits.happiness.TreatableTrait;
import uk.joshiejack.husbandry.world.entity.traits.lifestyle.MammalTrait;
import uk.joshiejack.husbandry.world.item.HusbandryItems;
import uk.joshiejack.penguinlib.util.PenguinTags;

import java.util.concurrent.CompletableFuture;

public class HusbandryItemTags extends ItemTagsProvider {
    public static final TagKey<Item> BUTTER = PenguinTags.forgeItemTag("butter");
    public static final TagKey<Item> MOB_PRODUCT = ItemTags.create(new ResourceLocation(Husbandry.MODID, "mob_product"));

    public HusbandryItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagLookup<Block>> blockLookup, ExistingFileHelper existingFileHelper) {
        super(output, provider, blockLookup, Husbandry.MODID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(BUTTER).add(HusbandryItems.BUTTER.get());
        tag(TreatableTrait.TREATS)
                .add(HusbandryItems.GENERIC_TREAT.get(), HusbandryItems.CAT_TREAT.get(), HusbandryItems.CHICKEN_TREAT.get()
                        , HusbandryItems.COW_TREAT.get(), HusbandryItems.DOG_TREAT.get(), HusbandryItems.HORSE_TREAT.get()
                        , HusbandryItems.PIG_TREAT.get(), HusbandryItems.RABBIT_TREAT.get(), HusbandryItems.SHEEP_TREAT.get()
                        , HusbandryItems.PARROT_TREAT.get(), HusbandryItems.LLAMA_TREAT.get());
        tag(EatsBirdFeedTrait.BIRD_FEED).add(Items.WHEAT_SEEDS, HusbandryItems.BIRD_FEED.get());
        tag(EatsSlopTrait.SLOP).add(Items.CARROT, HusbandryItems.SLOP.get());
        tag(MammalTrait.IMPREGNATES_MAMMALS).add(HusbandryItems.MIRACLE_POTION.get());
        tag(EatsGrassTrait.HAY).add(Items.WHEAT, Items.SEAGRASS, HusbandryItems.FODDER.get());
        tag(PenguinTags.RAW_FISHES).add(Items.COD); //Only because the tag below complains else
        tag(EatsCatFoodTrait.CAT_FOOD).addTag(PenguinTags.RAW_FISHES);
        tag(EatsDogFoodTrait.DOG_FOOD).add(Items.RABBIT, Items.BEEF, Items.PORKCHOP, Items.MUTTON, Items.CHICKEN);
        tag(EatsRabbitFoodTrait.RABBIT_FOOD).add(Items.CARROT);
        tag(HusbandryItemTags.MOB_PRODUCT).add(Items.FEATHER, Items.CHICKEN, Items.PORKCHOP, Items.BEEF, Items.LEATHER,
                Items.RABBIT, Items.RABBIT_FOOT, Items.RABBIT_HIDE, Items.MUTTON, Items.MILK_BUCKET).addTags(ItemTags.WOOL);
        tag(PenguinTags.SICKLES).add(HusbandryItems.SICKLE.get());
    }
}
