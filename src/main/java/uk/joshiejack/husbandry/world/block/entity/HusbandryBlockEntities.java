package uk.joshiejack.husbandry.world.block.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.world.block.HusbandryBlocks;

@SuppressWarnings("ConstantConditions")
public class HusbandryBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Husbandry.MODID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BowlBlockEntity>> BOWL = BLOCK_ENTITIES.register("bowl", () -> BlockEntityType.Builder.of(BowlBlockEntity::new, HusbandryBlocks.BOWL.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FeedingTrayBlockEntity>> FEEDING_TRAY = BLOCK_ENTITIES.register("feeding_tray", () -> BlockEntityType.Builder.of(FeedingTrayBlockEntity::new, HusbandryBlocks.FEEDING_TRAY.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<IncubatorBlockEntity>> INCUBATOR = BLOCK_ENTITIES.register("incubator", () -> BlockEntityType.Builder.of(IncubatorBlockEntity::new, HusbandryBlocks.INCUBATOR.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NestTileEntity>> NEST = BLOCK_ENTITIES.register("nest", () -> BlockEntityType.Builder.of(NestTileEntity::new, HusbandryBlocks.NEST.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TroughBlockEntity>> TROUGH = BLOCK_ENTITIES.register("trough", () -> BlockEntityType.Builder.of(TroughBlockEntity::new, HusbandryBlocks.TROUGH.get()).build(null));
}
