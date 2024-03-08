package uk.joshiejack.husbandry;

import com.google.common.base.CaseFormat;
import net.minecraft.DetectedVersion;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.InclusiveRange;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.joshiejack.husbandry.api.HusbandryAPI;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IMobTrait;
import uk.joshiejack.husbandry.data.*;
import uk.joshiejack.husbandry.world.block.HusbandryBlocks;
import uk.joshiejack.husbandry.world.block.entity.HusbandryBlockEntities;
import uk.joshiejack.husbandry.world.entity.MobDataLoader;
import uk.joshiejack.husbandry.world.entity.stats.MobStats;
import uk.joshiejack.husbandry.world.entity.traits.food.*;
import uk.joshiejack.husbandry.world.entity.traits.happiness.CarriableTrait;
import uk.joshiejack.husbandry.world.entity.traits.happiness.CleanableTrait;
import uk.joshiejack.husbandry.world.entity.traits.happiness.PettableTrait;
import uk.joshiejack.husbandry.world.entity.traits.happiness.TreatableTrait;
import uk.joshiejack.husbandry.world.entity.traits.lifestyle.*;
import uk.joshiejack.husbandry.world.entity.traits.product.*;
import uk.joshiejack.husbandry.world.inventory.MobTrackerContainer;
import uk.joshiejack.husbandry.world.item.HusbandryCreativeTab;
import uk.joshiejack.husbandry.world.item.HusbandryItems;
import uk.joshiejack.husbandry.world.item.crafting.HusbandryRegistries;
import uk.joshiejack.husbandry.world.note.LifespanNoteType;
import uk.joshiejack.husbandry.world.note.PregnancyNoteType;
import uk.joshiejack.penguinlib.util.helper.ReflectionHelper;
import uk.joshiejack.penguinlib.world.inventory.AbstractBookMenu;
import uk.joshiejack.penguinlib.world.note.type.NoteType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@Mod(Husbandry.MODID)
public class Husbandry {
    public static final String MODID = "husbandry";
    public static final Logger LOGGER = LogManager.getLogger();

    public Husbandry(IEventBus eventBus) {
        eventBus.addListener(this::setup);
        HusbandryContainers.CONTAINERS.register(eventBus);
        HusbandryBlocks.BLOCKS.register(eventBus);
        HusbandryItems.ITEMS.register(eventBus);
        HusbandryCreativeTab.CREATIVE_MODE_TABS.register(eventBus);
        HusbandryRegistries.RECIPE_TYPES.register(eventBus);
        HusbandryRegistries.SERIALIZERS.register(eventBus);
        HusbandrySounds.SOUNDS.register(eventBus);
        HusbandryBlockEntities.BLOCK_ENTITIES.register(eventBus);
        MobDataLoader.ATTACHMENT_TYPES.register(eventBus);
        MortalTrait.DAMAGE_SOURCES.register(eventBus);
        HusbandryAPI.instance = new HusbandryAPIImpl();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, HusbandryConfig.create());
    }

    private void registerTrait(Class<? extends IMobTrait> data) {
        HusbandryAPI.instance.registerMobTrait(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, data.getSimpleName().replace("Trait", "")), data);
    }

    private void registerNoteType(Class<? extends NoteType> type) {
        ReflectionHelper.newInstance(type);
    }

    private void setup(FMLCommonSetupEvent event) {
        //Register Note Types
        registerNoteType(LifespanNoteType.class);
        registerNoteType(PregnancyNoteType.class);

        //Register Traits
        registerTrait(EatsBirdFeedTrait.class);
        registerTrait(EatsCatFoodTrait.class);
        registerTrait(EatsDogFoodTrait.class);
        registerTrait(EatsGrassTrait.class);
        registerTrait(EatsRabbitFoodTrait.class);
        registerTrait(EatsSlopTrait.class);
        registerTrait(CarriableTrait.class);
        registerTrait(CleanableTrait.class);
        registerTrait(PettableTrait.class);
        registerTrait(TreatableTrait.class);
        registerTrait(AstraphobicTrait.class);
        registerTrait(AquaphobicTrait.class);
        registerTrait(DiurnalTrait.class);
        registerTrait(MammalTrait.class);
        registerTrait(MortalTrait.class);
        registerTrait(PetTrait.class);
        registerTrait(BowlableTrait.class);
        registerTrait(DropsProductTrait.class);
        registerTrait(FasterProductResetTrait.class);
        registerTrait(FindsProductTrait.class);
        registerTrait(LaysEggTrait.class);
        registerTrait(Bucketable.class);
        registerTrait(MoreProductChanceTrait.class);
        registerTrait(MoreProductTrait.class);
        registerTrait(ShearableTrait.class);
        //Unused traits
        registerTrait(NocturnalTrait.class);
        registerTrait(RequiresFoodTrait.class);
        registerTrait(LameableTrait.class);
    }

    @SubscribeEvent
    public static void onDataGathering(final GatherDataEvent event) {
        final DataGenerator generator = event.getGenerator();
        final PackOutput output = event.getGenerator().getPackOutput();
        //Add the datapack entries
        //Client
        generator.addProvider(event.includeClient(), new HusbandryBlockStates(output, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new HusbandryItemModels(output, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new HusbandryLanguage(output));
        generator.addProvider(event.includeClient(), new HusbandrySoundDefinitions(output, event.getExistingFileHelper()));

        //Server
        HusbandryBlockTags blocktags = new HusbandryBlockTags(output, event.getLookupProvider(), event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), blocktags);
        generator.addProvider(event.includeServer(), new HusbandryLootTables(output));
        generator.addProvider(event.includeServer(), new HusbandryItemTags(output, event.getLookupProvider(), blocktags.contentsGetter(), event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new HusbandryRecipes(output));
        generator.addProvider(event.includeServer(), new HusbandryDatabase(output));
        generator.addProvider(event.includeServer(), new HusbandryNotes(output));
        generator.addProvider(true, new PackMetadataGenerator(output).add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.literal("Resources for Husbandry"),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA),
                Optional.of(new InclusiveRange<>(0, Integer.MAX_VALUE)))));
    }

    public static class HusbandryContainers {
        public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Registries.MENU, Husbandry.MODID);
        public static final DeferredHolder<MenuType<?>, MenuType<AbstractBookMenu>> BOOK = CONTAINERS.register("mob_tracker", () -> IMenuTypeExtension.create((id, inv, data) -> new MobTrackerContainer(id)));
    }

    public static class HusbandrySounds {
        public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, MODID);
        public static final DeferredHolder<SoundEvent, SoundEvent> BRUSH = createSoundEvent("brush");

        private static DeferredHolder<SoundEvent, SoundEvent> createSoundEvent(@Nonnull String name) {
            return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, name)));
        }
    }

    /** API **/
    public static class HusbandryAPIImpl implements HusbandryAPI.IHusbandryAPI {
        @Override
        public void registerMobTrait(String name, Class<? extends IMobTrait> trait) {
            MobDataLoader.TRAITS.put(name, ReflectionHelper.newInstance(trait));
        }

        @Nullable
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Mob> IMobStats<E> getStatsForEntity(E entity) {
            return (IMobStats<E>) MobStats.getStats(entity);
        }
    }

    public static class HusbandryConfig {
        public static ModConfigSpec.IntValue maxHappiness;
        public static ModConfigSpec.IntValue hungerHappinessLoss;
        public static ModConfigSpec.IntValue hurtHappinessLossModifier;
        public static ModConfigSpec.IntValue dirtyHappinessLoss;
        public static ModConfigSpec.IntValue wrongTreatLoss;
        public static ModConfigSpec.IntValue lovedGain;
        public static ModConfigSpec.IntValue fedGain;
        public static ModConfigSpec.IntValue cleanedGain;
        public static ModConfigSpec.IntValue genericTreatGain;
        public static ModConfigSpec.IntValue typeTreatGain;
        public static ModConfigSpec.IntValue outsideGain;
        public static ModConfigSpec.IntValue birthGain;
        public static ModConfigSpec.IntValue daysPerYear;

        HusbandryConfig(ModConfigSpec.Builder builder) {
            builder.push("General Settings");
            daysPerYear = builder.defineInRange("Animal lifespan days per year", 112, 7, 700000);
            builder.pop();
            builder.push("Happiness Settings");
            maxHappiness = builder.defineInRange("Maximum happiness", 30000, 100, 100000000);
            hungerHappinessLoss = builder.defineInRange("Happiness lost from hunger", 1, 0, 100000);
            hurtHappinessLossModifier = builder.defineInRange("Happiness loss from hurt multiplier", 5, 0, 100);
            dirtyHappinessLoss = builder.defineInRange("Happiness lost from being unclean", 1, 0, 100000);
            wrongTreatLoss = builder.defineInRange("Happiness lost from incorrect treat type", 500, 0, 100000);
            lovedGain = builder.defineInRange("Happiness gained from petting/carrying", 100, 0, 100000);
            fedGain = builder.defineInRange("Happiness gained from hand feeding", 100, 0, 100000);
            cleanedGain = builder.defineInRange("Happiness gained from cleaning", 30, 0, 100000);
            genericTreatGain = builder.defineInRange("Happiness gained from generic treats", 100, 0, 100000);
            typeTreatGain = builder.defineInRange("Happiness gained from type treats", 250, 0, 100000);
            outsideGain = builder.defineInRange("Happiness gained from being outside in the sun", 2, 0, 100000);
            birthGain = builder.defineInRange("Happiness gained from giving birth", 100, 0, 100000);
            builder.pop();
        }

        public static ModConfigSpec create() {
            return new ModConfigSpec.Builder().configure(HusbandryConfig::new).getValue();
        }
    }
}
