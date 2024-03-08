package uk.joshiejack.husbandry.world.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.EffectCure;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.world.block.HusbandryBlocks;
import uk.joshiejack.husbandry.world.inventory.MobTrackerContainer;
import uk.joshiejack.penguinlib.world.item.BookItem;
import uk.joshiejack.penguinlib.world.item.PenguinItem;
import uk.joshiejack.penguinlib.world.item.SickleItem;

import java.util.function.BiConsumer;


@SuppressWarnings("unused")
public class HusbandryItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Husbandry.MODID);
    private static final BiConsumer<ItemStack, LivingEntity> removePotionEffects = (stack, entity) ->  {
        if (!entity.level().isClientSide)
            EffectCure.getActions().forEach(entity::removeEffectsCuredBy);
    };

    // Food
    public static final DeferredItem<Item> BUTTER = ITEMS.register("butter", () -> new Item(new Item.Properties().food(HusbandryFoods.BUTTER)));
    public static final DeferredItem<Item> DINNER_ROLL = ITEMS.register("dinner_roll", () -> new Item(new Item.Properties().food(HusbandryFoods.DINNER_ROLL)));
    public static final DeferredItem<Item> BOILED_EGG = ITEMS.register("boiled_egg", () -> new Item(new Item.Properties().food(HusbandryFoods.BOILED_EGG)));
    public static final DeferredItem<Item> ICE_CREAM = ITEMS.register("ice_cream", () -> new Item(new Item.Properties().food(HusbandryFoods.ICE_CREAM)));
    public static final DeferredItem<Item> MUG_OF_MILK = ITEMS.register("mug_of_milk", () -> new PenguinItem(new PenguinItem.Properties().useAction(UseAnim.DRINK).finishUsing(removePotionEffects).food(HusbandryFoods.MUG_OF_MILK)));
    public static final DeferredItem<Item> HOT_MILK = ITEMS.register("hot_milk", () -> new PenguinItem(new PenguinItem.Properties().useAction(UseAnim.DRINK).finishUsing(removePotionEffects).food(HusbandryFoods.HOT_MILK)));
    public static final DeferredItem<Item> TRUFFLE = ITEMS.register("truffle", () -> new Item(new Item.Properties().food(HusbandryFoods.TRUFFLE)));
    public static final DeferredItem<Item> MOB_TRACKER = ITEMS.register("mob_tracker", () -> new BookItem(new Item.Properties().stacksTo(1), () -> new SimpleMenuProvider((id, inv, p) -> new MobTrackerContainer(id), Component.translatable("husbandry.item.mob_tracker"))));
    public static final DeferredItem<Item> BRUSH = ITEMS.register("brush", () -> new Item(new Item.Properties().stacksTo(1).durability(64)));
    public static final DeferredItem<Item> MIRACLE_POTION = ITEMS.register("miracle_potion", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> SICKLE = ITEMS.register("sickle", () -> new SickleItem(Tiers.STONE, new Item.Properties()));

    // Animal Food
    public static final DeferredItem<Item> FODDER = ITEMS.register("fodder", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BIRD_FEED = ITEMS.register("bird_feed", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SLOP = ITEMS.register("slop", () -> new Item(new Item.Properties()));
    // Animal Treats
    public static final DeferredItem<Item> GENERIC_TREAT = ITEMS.register("generic_treat", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CAT_TREAT = ITEMS.register("cat_treat", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CHICKEN_TREAT = ITEMS.register("chicken_treat", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> COW_TREAT = ITEMS.register("cow_treat", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DOG_TREAT = ITEMS.register("dog_treat", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> HORSE_TREAT = ITEMS.register("horse_treat", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PIG_TREAT = ITEMS.register("pig_treat", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RABBIT_TREAT = ITEMS.register("rabbit_treat", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SHEEP_TREAT = ITEMS.register("sheep_treat", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PARROT_TREAT = ITEMS.register("parrot_treat", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LLAMA_TREAT = ITEMS.register("llama_treat", () -> new Item(new Item.Properties()));
    // Blocks
    public static final DeferredItem<Item> FEEDING_TRAY = ITEMS.register("feeding_tray", () -> new BlockItem(HusbandryBlocks.FEEDING_TRAY.get(), new Item.Properties()));
    public static final DeferredItem<Item> BOWL = ITEMS.register("bowl", () -> new BlockItem(HusbandryBlocks.BOWL.get(), new Item.Properties()));
    public static final DeferredItem<Item> NEST = ITEMS.register("nest", () -> new BlockItem(HusbandryBlocks.NEST.get(), new Item.Properties()));
    public static final DeferredItem<Item> TROUGH = ITEMS.register("trough", () -> new BlockItem(HusbandryBlocks.TROUGH.get(), new Item.Properties()));
    public static final DeferredItem<Item> INCUBATOR = ITEMS.register("incubator", () -> new BlockItem(HusbandryBlocks.INCUBATOR.get(), new Item.Properties()));
    public static final DeferredItem<Item> TRUFFLE_BLOCK = ITEMS.register("truffle_block", () -> new BlockItem(HusbandryBlocks.TRUFFLE_BLOCK.get(), new Item.Properties()));
}
