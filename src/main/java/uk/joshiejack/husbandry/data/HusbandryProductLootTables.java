package uk.joshiejack.husbandry.data;

import com.google.common.collect.Maps;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.world.block.HusbandryBlocks;

import java.util.Map;
import java.util.function.BiConsumer;

public class HusbandryProductLootTables implements LootTableSubProvider {
    private final Map<ResourceLocation, LootTable.Builder> map = Maps.newHashMap();

    @Override
    public void generate(@NotNull BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        this.generate();
        this.map.forEach(consumer);
    }


    protected void add(ResourceLocation id, LootTable.Builder builder) {
        if (this.map.put(id, builder) != null) {
            throw new IllegalStateException("Duplicate loot table " + id);
        }
    }
    public void generate() {
        addProducts("mushroom_stew", LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.MUSHROOM_STEW))));
        addProducts("milk", LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.MILK_BUCKET))));
        addProducts("truffle", LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(HusbandryBlocks.TRUFFLE_BLOCK.get()))));
        addProducts("chicken_egg", LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.EGG))));
        addProducts("rabbit_foot", LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.RABBIT_FOOT).when(LootItemRandomChanceCondition.randomChance(0.1F)))));
        addProducts("wool", LootTable.lootTable().withPool(LootPool.lootPool().setRolls(UniformGenerator.between(5, 11))
                .add(LootItem.lootTableItem(Items.WHITE_WOOL).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().nbt(new NbtPredicate(color(DyeColor.WHITE))))))
                .add(LootItem.lootTableItem(Items.ORANGE_WOOL).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().nbt(new NbtPredicate(color(DyeColor.ORANGE))))))
                .add(LootItem.lootTableItem(Items.MAGENTA_WOOL).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().nbt(new NbtPredicate(color(DyeColor.MAGENTA))))))
                .add(LootItem.lootTableItem(Items.LIGHT_BLUE_WOOL).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().nbt(new NbtPredicate(color(DyeColor.LIGHT_BLUE))))))
                .add(LootItem.lootTableItem(Items.YELLOW_WOOL).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().nbt(new NbtPredicate(color(DyeColor.YELLOW))))))
                .add(LootItem.lootTableItem(Items.LIME_WOOL).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().nbt(new NbtPredicate(color(DyeColor.LIME))))))
                .add(LootItem.lootTableItem(Items.PINK_WOOL).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().nbt(new NbtPredicate(color(DyeColor.PINK))))))
                .add(LootItem.lootTableItem(Items.GRAY_WOOL).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().nbt(new NbtPredicate(color(DyeColor.GRAY))))))
                .add(LootItem.lootTableItem(Items.LIGHT_GRAY_WOOL).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().nbt(new NbtPredicate(color(DyeColor.LIGHT_GRAY))))))
                .add(LootItem.lootTableItem(Items.CYAN_WOOL).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().nbt(new NbtPredicate(color(DyeColor.CYAN))))))
                .add(LootItem.lootTableItem(Items.PURPLE_WOOL).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().nbt(new NbtPredicate(color(DyeColor.PURPLE))))))
                .add(LootItem.lootTableItem(Items.BLUE_WOOL).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().nbt(new NbtPredicate(color(DyeColor.BLUE))))))
                .add(LootItem.lootTableItem(Items.BROWN_WOOL).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().nbt(new NbtPredicate(color(DyeColor.BROWN))))))
                .add(LootItem.lootTableItem(Items.GREEN_WOOL).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().nbt(new NbtPredicate(color(DyeColor.GREEN))))))
                .add(LootItem.lootTableItem(Items.RED_WOOL).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().nbt(new NbtPredicate(color(DyeColor.RED))))))
                .add(LootItem.lootTableItem(Items.BLACK_WOOL).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().nbt(new NbtPredicate(color(DyeColor.BLACK))))))
        ));
    }

    protected CompoundTag color(DyeColor color) {
        CompoundTag tag = new CompoundTag();
        tag.putByte("Color", (byte) color.getId());
        return tag;
    }

    protected void addProducts(String type, LootTable.Builder builder) {
        ResourceLocation lootTable = new ResourceLocation(Husbandry.MODID, "entities/products/" + type);
        add(lootTable, builder);
    }
}
