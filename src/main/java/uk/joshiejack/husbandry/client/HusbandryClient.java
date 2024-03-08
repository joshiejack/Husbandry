package uk.joshiejack.husbandry.client;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.model.data.ModelProperty;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.client.gui.PageStats;
import uk.joshiejack.husbandry.client.renderer.EggSupplierBakedModel;
import uk.joshiejack.husbandry.world.block.entity.IncubatorBlockEntity;
import uk.joshiejack.husbandry.world.block.entity.NestTileEntity;
import uk.joshiejack.penguinlib.client.gui.book.Book;
import uk.joshiejack.penguinlib.client.gui.book.tab.NotesTab;
import uk.joshiejack.penguinlib.client.gui.book.tab.Tab;
import uk.joshiejack.penguinlib.util.icon.Icon;
import uk.joshiejack.penguinlib.util.icon.TextureIcon;
import uk.joshiejack.penguinlib.world.inventory.AbstractBookMenu;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Husbandry.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HusbandryClient {
    public static final ResourceLocation FODDER = new ResourceLocation(Husbandry.MODID, "block/fodder");
    public static final ResourceLocation SLOP = new ResourceLocation(Husbandry.MODID, "block/slop");

    //TODO: DATA GEN THESE?
//    @SubscribeEvent
//    public static void onMapping(TextureStitchEvent.Pre event) {
//        event.addSprite(FODDER);
//        event.addSprite(SLOP);
//    }

    @SubscribeEvent
    public static void setup(RegisterMenuScreensEvent event) {
        event.register(Husbandry.HusbandryContainers.BOOK.get(), ((AbstractBookMenu container, Inventory inv, Component text) ->
                Book.getInstance(Husbandry.MODID, container, inv, text, (Book bs) -> {
                    Component stats = Component.translatable("gui." + Husbandry.MODID + ".stats");
                    Component notes = Component.translatable("gui." + Husbandry.MODID + ".notes");
                    bs.withTab(new Tab(stats, PageStats.ICON)).withPage(new PageStats(stats));
                    bs.withTab(new NotesTab(notes, new TextureIcon(Icon.DEFAULT_LOCATION, 0, 0, 1))
                            .withCategory(new ResourceLocation(Husbandry.MODID, "care_category")));
                })
        ));

        //TODO: ADD TO THE MODEL FILES
//        RenderType cutout = RenderType.cutout();
//        RenderTypeLookup.setRenderLayer(HusbandryBlocks.FEEDING_TRAY.get(), cutout);
//        RenderTypeLookup.setRenderLayer(HusbandryBlocks.NEST.get(), cutout);
//        RenderTypeLookup.setRenderLayer(HusbandryBlocks.BOWL.get(), cutout);
//        RenderTypeLookup.setRenderLayer(HusbandryBlocks.INCUBATOR.get(), cutout);
//        RenderTypeLookup.setRenderLayer(HusbandryBlocks.TRUFFLE_BLOCK.get(), cutout);
//        RenderTypeLookup.setRenderLayer(HusbandryBlocks.TROUGH.get(), cutout);
    }

    public static final ResourceLocation DEFAULT_NEST_EGG = new ResourceLocation(Husbandry.MODID, "block/nest_egg");
    public static final ResourceLocation DEFAULT_INCUBATOR_EGG = new ResourceLocation(Husbandry.MODID, "block/incubator_egg");

    @SubscribeEvent
    public static void loadModels(ModelEvent.RegisterAdditional event) {
        event.register(DEFAULT_NEST_EGG);
        event.register(DEFAULT_INCUBATOR_EGG);
    }

    private static void replaceWithEggSupplier(String model, ModelEvent.ModifyBakingResult event, Map<Item, ResourceLocation> overrides, ModelProperty<ItemStack> property) {
        ResourceLocation key = ModelResourceLocation.tryParse(model);
        assert key != null;
        BakedModel nest = event.getModels().get(key);
        if (!(event.getModels().get(key) instanceof EggSupplierBakedModel))
            event.getModels().put(key, new EggSupplierBakedModel(nest, overrides, property));
    }

    @SubscribeEvent
    public static void onBaking(ModelEvent.ModifyBakingResult event) {
        //TODO: Allow for collecting custom model resources for each item from somewhere
        Map<Item, ResourceLocation> nest = new HashMap<>();
        nest.put(Items.EGG, DEFAULT_NEST_EGG);
        Map<Item, ResourceLocation> incubator = new HashMap<>();
        incubator.put(Items.EGG, DEFAULT_INCUBATOR_EGG);
        replaceWithEggSupplier("husbandry:nest#", event, nest, NestTileEntity.ITEM_STACK);
        replaceWithEggSupplier("husbandry:incubator#facing=north", event, incubator, IncubatorBlockEntity.ITEM_STACK);
        replaceWithEggSupplier("husbandry:incubator#facing=east", event, incubator, IncubatorBlockEntity.ITEM_STACK);
        replaceWithEggSupplier("husbandry:incubator#facing=west", event, incubator, IncubatorBlockEntity.ITEM_STACK);
        replaceWithEggSupplier("husbandry:incubator#facing=south", event, incubator, IncubatorBlockEntity.ITEM_STACK);
    }
}