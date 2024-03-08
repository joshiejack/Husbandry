package uk.joshiejack.husbandry.data;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.world.block.*;

public class HusbandryBlockStates extends BlockStateProvider {
    public HusbandryBlockStates(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Husbandry.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        model(HusbandryBlocks.NEST.get());
        model(HusbandryBlocks.TRUFFLE_BLOCK.get());
        ModelFile file = models().getExistingFile(BuiltInRegistries.BLOCK.getKey(HusbandryBlocks.INCUBATOR.get()));
        VariantBlockStateBuilder builder = getVariantBuilder(HusbandryBlocks.INCUBATOR.get());
        builder.partialState().with(IncubatorBlock.FACING, Direction.EAST).modelForState().modelFile(file).rotationY(90).addModel();
        builder.partialState().with(IncubatorBlock.FACING, Direction.WEST).modelForState().modelFile(file).rotationY(270).addModel();
        builder.partialState().with(IncubatorBlock.FACING, Direction.NORTH).modelForState().modelFile(file).rotationY(0).addModel();
        builder.partialState().with(IncubatorBlock.FACING, Direction.SOUTH).modelForState().modelFile(file).rotationY(180).addModel();
        bowl(HusbandryBlocks.BOWL.get());
        trough(HusbandryBlocks.TROUGH.get());
        feedingtray(HusbandryBlocks.FEEDING_TRAY.get());
    }

    private void feedingtray(Block block) {
        getMultipartBuilder(block)
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "feeding_tray"))).addModel().end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "bird_feed_semi")))
                .addModel().condition(FeedingTrayBlock.FILL, 1).end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "bird_feed_full")))
                .addModel().condition(FeedingTrayBlock.FILL, 2).end();
    }

    private void trough(Block block) {
        getMultipartBuilder(block)
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "trough"))).addModel().end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "hay_one_quarter")))
                .addModel().condition(TroughBlock.TYPE, TroughBlock.FoodType.HAY).condition(TroughBlock.FILL, 1).end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "hay_two_quarters")))
                .addModel().condition(TroughBlock.TYPE, TroughBlock.FoodType.HAY).condition(TroughBlock.FILL, 2).end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "hay_three_quarters")))
                .addModel().condition(TroughBlock.TYPE, TroughBlock.FoodType.HAY).condition(TroughBlock.FILL, 3).end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "hay_four_quarters")))
                .addModel().condition(TroughBlock.TYPE, TroughBlock.FoodType.HAY).condition(TroughBlock.FILL, 4).end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "slop_one_quarter")))
                .addModel().condition(TroughBlock.TYPE, TroughBlock.FoodType.SLOP).condition(TroughBlock.FILL, 1).end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "slop_two_quarters")))
                .addModel().condition(TroughBlock.TYPE, TroughBlock.FoodType.SLOP).condition(TroughBlock.FILL, 2).end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "slop_three_quarters")))
                .addModel().condition(TroughBlock.TYPE, TroughBlock.FoodType.SLOP).condition(TroughBlock.FILL, 3).end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "slop_four_quarters")))
                .addModel().condition(TroughBlock.TYPE, TroughBlock.FoodType.SLOP).condition(TroughBlock.FILL, 4).end();
    }

    private void bowl(Block block) {
        getMultipartBuilder(block)
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "bowl"))).addModel().end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "cat_food_semi")))
                .addModel().condition(BowlBlock.TYPE, BowlBlock.FoodType.CAT_FOOD).condition(TroughBlock.FILL, 1).end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "cat_food_full")))
                .addModel().condition(BowlBlock.TYPE, BowlBlock.FoodType.CAT_FOOD).condition(TroughBlock.FILL, 2).end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "dog_food_semi")))
                .addModel().condition(BowlBlock.TYPE, BowlBlock.FoodType.DOG_FOOD).condition(TroughBlock.FILL, 1).end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "dog_food_full")))
                .addModel().condition(BowlBlock.TYPE, BowlBlock.FoodType.DOG_FOOD).condition(TroughBlock.FILL, 2).end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "rabbit_food_semi")))
                .addModel().condition(BowlBlock.TYPE, BowlBlock.FoodType.RABBIT_FOOD).condition(TroughBlock.FILL, 1).end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "rabbit_food_full")))
                .addModel().condition(BowlBlock.TYPE, BowlBlock.FoodType.RABBIT_FOOD).condition(TroughBlock.FILL, 2).end();
    }

    protected void model(Block block) {
        ModelFile file = models().getExistingFile(BuiltInRegistries.BLOCK.getKey(block));
        getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(file).build());
    }
}
