package uk.joshiejack.husbandry.data;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.world.block.entity.HusbandryBlockEntities;
import uk.joshiejack.husbandry.world.item.HusbandryItems;
import uk.joshiejack.penguinlib.data.TimeUnitRegistry;
import uk.joshiejack.penguinlib.data.database.CSVUtils;
import uk.joshiejack.penguinlib.data.generator.AbstractDatabaseProvider;
import uk.joshiejack.penguinlib.data.generator.builder.TradeBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HusbandryDatabase extends AbstractDatabaseProvider {
    public HusbandryDatabase(PackOutput output) {
        super(output, Husbandry.MODID);
    }

    @Override
    protected void addDatabaseEntries() {
        addTimeUnit("require_food_max_days", 3);
        addTimeUnitForMachine(HusbandryBlockEntities.INCUBATOR.get(), TimeUnitRegistry.Defaults.WEEK.getValue());
        new TradeBuilder(VillagerProfession.SHEPHERD, 1, HusbandryItems.GENERIC_TREAT.get()).setOutputAmount(5).build(this);
        new TradeBuilder(VillagerProfession.SHEPHERD, 2, HusbandryItems.CAT_TREAT.get()).setOutputAmount(3).setInputAmount(2).build(this);
        new TradeBuilder(VillagerProfession.SHEPHERD, 2, HusbandryItems.CHICKEN_TREAT.get()).setOutputAmount(3).setInputAmount(2).build(this);
        new TradeBuilder(VillagerProfession.SHEPHERD, 2, HusbandryItems.COW_TREAT.get()).setOutputAmount(3).setInputAmount(2).build(this);
        new TradeBuilder(VillagerProfession.SHEPHERD, 2, HusbandryItems.DOG_TREAT.get()).setOutputAmount(3).setInputAmount(2).build(this);
        new TradeBuilder(VillagerProfession.SHEPHERD, 2, HusbandryItems.HORSE_TREAT.get()).setOutputAmount(3).setInputAmount(2).build(this);
        new TradeBuilder(VillagerProfession.SHEPHERD, 2, HusbandryItems.PIG_TREAT.get()).setOutputAmount(3).setInputAmount(2).build(this);
        new TradeBuilder(VillagerProfession.SHEPHERD, 2, HusbandryItems.RABBIT_TREAT.get()).setOutputAmount(3).setInputAmount(2).build(this);
        new TradeBuilder(VillagerProfession.SHEPHERD, 2, HusbandryItems.SHEEP_TREAT.get()).setOutputAmount(3).setInputAmount(2).build(this);
        new TradeBuilder(VillagerProfession.SHEPHERD, 2, HusbandryItems.PARROT_TREAT.get()).setOutputAmount(3).setInputAmount(2).build(this);
        new TradeBuilder(VillagerProfession.SHEPHERD, 2, HusbandryItems.LLAMA_TREAT.get()).setOutputAmount(3).setInputAmount(2).build(this);
        MobType.createMammal("cow").withLifespan(12, 20).withTreat(HusbandryItems.COW_TREAT.get()).levelUpWith(7, 24).withGestationPeriod(9).withMaturityAt(14).withProducts(1, "milk", Items.MILK_BUCKET)
                .assign(EntityType.COW).withTraits("diurnal", "eats_grass", "cleanable", "bucketable", "more_product").build(this);
        MobType.createBird("chicken").withLifespan(3, 10).withTreat(HusbandryItems.CHICKEN_TREAT.get()).levelUpWith(5,26).withGestationPeriod(3).withMaturityAt(3).withProducts(1, "chicken_egg", Items.EGG)
                .assign(EntityType.CHICKEN).withTraits("aquaphobic", "diurnal", "lays_egg", "more_product").build(this);
        MobType.createMammal("pig").withLifespan(6, 10).withTreat(HusbandryItems.PIG_TREAT.get()).levelUpWith(20, 10).withGestationPeriod(4).withMaturityAt(4).withProducts(1, "truffle", HusbandryItems.TRUFFLE_BLOCK.get())
                .assign(EntityType.PIG).withTraits("aquaphobic", "diurnal", "eats_slop", "cleanable", "pettable", "finds_product", "more_product_chance").build(this);
        MobType.createMammal("sheep").withLifespan(8,12).withTreat(HusbandryItems.SHEEP_TREAT.get()).levelUpWith(2,29).withGestationPeriod(5).withMaturityAt(4).withProducts(7, "wool", Items.WHITE_WOOL)
                .assign(EntityType.SHEEP).withTraits("diurnal", "eats_grass", "cleanable", "shearable", "faster_product_reset").build(this);
        MobType.createMammal("horse").withLifespan(25,30).withTreat(HusbandryItems.HORSE_TREAT.get()).levelUpWith(10,22).withGestationPeriod(24).withMaturityAt(12).assign(EntityType.HORSE)
                .withTraits("aquaphobic", "diurnal", "eats_grass", "cleanable", "pet").build(this);
        MobType.createMammal("llama").withLifespan(15,25).withTreat(HusbandryItems.LLAMA_TREAT.get()).levelUpWith(7,24).withGestationPeriod(11).withMaturityAt(10).assign(EntityType.LLAMA)
                .withTraits("diurnal", "eats_grass", "cleanable", "pet").build(this);
        MobType.createSmallMammal("rabbit").withLifespan(8,12).withTreat(HusbandryItems.RABBIT_TREAT.get()).levelUpWith(14,14).withGestationPeriod(4).withMaturityAt(3).withProducts(4, "rabbit_foot", Items.RABBIT_FOOT)
                .withTraits("eats_rabbit_food", "drops_product", "more_product_chance").assign(EntityType.RABBIT).build(this);
        MobType.createSmallMammal("cat").withLifespan(10,20).withTreat(HusbandryItems.CAT_TREAT.get()).levelUpWith(3,24).withGestationPeriod(5).withMaturityAt(7)
                .assign(EntityType.CAT).withTraits("aquaphobic", "eats_cat_food", "pet").build(this);
        MobType.createMammal("dog").withLifespan(10,13).withTreat(HusbandryItems.DOG_TREAT.get()).levelUpWith(24,3).withGestationPeriod(5).withMaturityAt(7)
                .assign(EntityType.WOLF).withTraits("diurnal", "eats_dog_food", "cleanable", "pet").build(this);
        MobType.createMammal("donkey").withLifespan(25,30).withTreat(HusbandryItems.HORSE_TREAT.get()).levelUpWith(22,11).withGestationPeriod(24).withMaturityAt(12)
                .assign(EntityType.DONKEY).assign(EntityType.MULE).withTraits("aquaphobic", "diurnal", "eats_grass", "cleanable", "pet").build(this);
        MobType.createBird("parrot").withLifespan(25,50).withTreat(HusbandryItems.PARROT_TREAT.get()).levelUpWith(3,24).withGestationPeriod(4).withMaturityAt(36)
                .assign(EntityType.PARROT).withTraits("aquaphobic", "diurnal", "pet").build(this);
        MobType.createMammal("mooshroom").withLifespan(13, 21).withTreat(HusbandryItems.COW_TREAT.get()).levelUpWith(24, 7).withGestationPeriod(11).withMaturityAt(21).withProducts(1, "mushroom_stew", Items.MUSHROOM_STEW)
                .assign(EntityType.MOOSHROOM).withTraits("diurnal", "eats_grass", "cleanable", "bowlable", "more_product").build(this);
    }

    private static class MobType {
        public static final ResourceLocation NO_PRODUCTS = new ResourceLocation(Husbandry.MODID, "none");
        private final String name;
        private int minAge = 0, maxAge = 0;
        private Item treat = HusbandryItems.GENERIC_TREAT.get();
        private int generic = 10, type = 10, gestation = 9, maturity = 10, productsFrequency = 0;
        private ResourceLocation productTable = NO_PRODUCTS;
        private Item productsIcon = Items.AIR;
        private final List<EntityType<?>> entities = new ArrayList<>();
        private final List<String> traits = new ArrayList<>();

        private MobType(String name) {
            this.name = name;
        }

        public static MobType createBird(String name) {
            return new MobType(name).withTraits("mortal", "eats_bird_feed", "carriable", "treatable", "astraphobic");
        }

        public static MobType createSmallMammal(String name) {
            return new MobType(name).withTraits("mortal", "mammal", "carriable", "treatable", "astraphobic");
        }

        public static MobType createMammal(String name) {
            return new MobType(name).withTraits("mortal", "mammal", "pettable", "treatable", "astraphobic");
        }

        public MobType withTraits(String... traits) {
            this.traits.addAll(Arrays.asList(traits));
            return this;
        }

        public MobType assign(EntityType<?> entity) {
            this.entities.add(entity);
            return this;
        }

        public MobType withLifespan(int min, int max) {
            this.minAge = min;
            this.maxAge = max;
            return this;
        }

        public MobType withTreat(Item item) {
            this.treat = item;
            return this;
        }

        public MobType levelUpWith(int generic, int type) {
            this.generic = generic;
            this.type = type;
            return this;
        }

        public MobType withGestationPeriod(int gestation) {
            this.gestation = gestation;
            return this;
        }

        public MobType withMaturityAt(int maturity) {
            this.maturity = maturity;
            return this;
        }

        public MobType withProducts(int productFrequency, String table, Item icon) {
            this.productsFrequency = productFrequency;
            this.productTable = new ResourceLocation(Husbandry.MODID, "entities/products/" + table);
            this.productsIcon = icon;
            return this;
        }

        @SuppressWarnings("all")
        public void build(HusbandryDatabase database) {
            database.addEntry("mob_species", "Name,Min Age,Max Age,Treat Item,Generic Treats,Species Treats,Days to Birth,Days to Maturity,Product Frequency,Products Loot Table,Product Icon",
                    CSVUtils.join(name, minAge, maxAge, BuiltInRegistries.ITEM.getKey(treat), generic, type, gestation, maturity, productsFrequency, productTable == NO_PRODUCTS ? "none" : productTable.toString(), BuiltInRegistries.ITEM.getKey(productsIcon)));
            entities.forEach(entity -> database.addEntry("mob_entities", "Entity,Species", CSVUtils.join(Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE.getKey(entity)).toString(), name)));
            traits.forEach(trait -> database.addEntry("mob_traits", "Species,Trait", CSVUtils.join(name, trait)));
        }
    }
}
