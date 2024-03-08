package uk.joshiejack.husbandry.client.renderer;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class EggSupplierBakedModel extends BakedModelWrapper<BakedModel> {
    private final Map<Item, Map<Direction, List<BakedQuad>>> QUADS = new HashMap<>();
    private final Map<Item, ResourceLocation> overrides;
    private final ModelProperty<ItemStack> property;

    public EggSupplierBakedModel(BakedModel originalModel, Map<Item, ResourceLocation> overrides, ModelProperty<ItemStack> property) {
        super(originalModel);
        this.overrides = overrides;
        this.property = property;
    }

    private Item defaultItem(Item item) {
        return overrides.containsKey(item) ? item : Items.EGG;
    }

    private Map<Direction, List<BakedQuad>> getQuads(Item item) {
        if (!QUADS.containsKey(item))
            QUADS.put(item, new HashMap<>());
        return QUADS.get(item);
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData data, @Nullable RenderType renderType) {
        if (state != null && data.has(property)) {
            ItemStack stack = data.get(property);
            if (stack != null && !stack.isEmpty()) {
                Map<Direction, List<BakedQuad>> map = getQuads(defaultItem(stack.getItem()));
                if (map.containsKey(side)) return map.get(side);
                else {
                    List<BakedQuad> quads = Lists.newArrayList(super.getQuads(state, side, rand, data, renderType));
                    Minecraft mc = Minecraft.getInstance();
                    if (!overrides.containsKey(stack.getItem())) {
                        BakedModel item = mc.getItemRenderer().getModel(stack, mc.level, mc.player, 0);
                        quads.addAll(item.getQuads(null, side, rand, data, renderType));
                    } else quads.addAll(mc.getModelManager().getModel(overrides.get(stack.getItem())).getQuads(null, side, rand, data, renderType));
                    map.put(side, quads);
                    return quads;
                }
            }
        }

        return super.getQuads(state, side, rand, data, renderType);
    }
}