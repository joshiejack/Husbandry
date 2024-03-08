package uk.joshiejack.husbandry.world.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.husbandry.api.HusbandryAPI;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.world.block.HusbandryBlocks;
import uk.joshiejack.penguinlib.world.item.crafting.AbstractSimplePenguinRecipe;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class IncubatorRecipe extends AbstractSimplePenguinRecipe<IncubatorRecipe, IncubatorRecipe.Serializer, EntityType<?>> {
    private final int min;
    private final int max;

    public IncubatorRecipe(Ingredient ingredient, EntityType<?> entity, int min, int max) {
        super(HusbandryRegistries.INCUBATOR, HusbandryRegistries.INCUBATOR_SERIALIZER, ingredient, entity);
        this.min = min;
        this.max = max;
    }

    //TODO: Test this
    public void hatch(ServerLevel world, BlockPos pos, ItemStack stack) {
        for (int i = 0; i < world.getRandom().nextInt(min, max); i++) {
            Entity entity = output.create(world);
            if (!(entity instanceof Mob mob)) return;
            mob.setBaby(true);
            if (mob instanceof Slime) {
                try {
                    ObfuscationReflectionHelper.findMethod(Slime.class, "setSize", int.class, boolean.class).invoke(1, true);
                } catch (IllegalAccessException | InvocationTargetException ignored) {
                }
            }

            mob.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
            if (mob instanceof AgeableMob && stack.hasTag() && Objects.requireNonNull(stack.getTag()).contains("HeartLevel")) {
                IMobStats<?> babyStats = HusbandryAPI.instance.getStatsForEntity((AgeableMob) mob);
                if (babyStats != null)
                    babyStats.increaseHappiness(mob,stack.getTag().getInt("HeartLevel") / 2);
            }

            world.addFreshEntity(mob);
        }
    }

    @Nonnull
    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(HusbandryBlocks.INCUBATOR.get());
    }

    public EntityType<?> getEntity() {
        return output;
    }

    public static final class Serializer implements RecipeSerializer<IncubatorRecipe> {
        public static final Codec<IncubatorRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Ingredient.CODEC.fieldOf("ingredient").forGetter(s -> s.ingredient),
                BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("output_entity").forGetter(s -> s.output),
                Codec.INT.fieldOf("min").forGetter(s -> s.min),
                Codec.INT.fieldOf("max").forGetter(s -> s.max)
        ).apply(instance, IncubatorRecipe::new));

        @Override
        public @NotNull Codec<IncubatorRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull IncubatorRecipe fromNetwork(@NotNull FriendlyByteBuf buf) {
            return new IncubatorRecipe(Ingredient.fromNetwork(buf), buf.readById(BuiltInRegistries.ENTITY_TYPE), buf.readByte(), buf.readByte());
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf buffer, @Nonnull IncubatorRecipe recipe) {
            recipe.ingredient.toNetwork(buffer);
            buffer.writeId(BuiltInRegistries.ENTITY_TYPE, recipe.getEntity());
            buffer.writeByte(recipe.min);
            buffer.writeByte(recipe.max);
        }
    }
}
