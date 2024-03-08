package uk.joshiejack.husbandry.world.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.penguinlib.world.block.ShapedBlock;


@SuppressWarnings("unused")

public class HusbandryBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Husbandry.MODID);
    public static final DeferredBlock<Block> FEEDING_TRAY = BLOCKS.register("feeding_tray", FeedingTrayBlock::new);
    public static final DeferredBlock<Block> BOWL = BLOCKS.register("bowl", BowlBlock::new);
    public static final DeferredBlock<Block> NEST = BLOCKS.register("nest", NestBlock::new);
    public static final DeferredBlock<Block> INCUBATOR = BLOCKS.register("incubator", IncubatorBlock::new);
    public static final DeferredBlock<Block> TRUFFLE_BLOCK = BLOCKS.register("truffle_block", () -> new ShapedBlock(Block.Properties.of().pushReaction(PushReaction.DESTROY).strength(0.3F).sound(SoundType.FUNGUS), Block.box(3D, 0D, 3D, 13D, 10D, 13D)));
    public static final DeferredBlock<Block> TROUGH = BLOCKS.register("trough", TroughBlock::new);
}
