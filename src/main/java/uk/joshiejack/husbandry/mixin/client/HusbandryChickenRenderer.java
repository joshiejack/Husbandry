package uk.joshiejack.husbandry.mixin.client;

import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Chicken;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChickenRenderer.class)
public class HusbandryChickenRenderer {
    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Chicken;)Lnet/minecraft/resources/ResourceLocation;", at = @At("HEAD"))
    private void getTextureLocation(Chicken chicken, CallbackInfoReturnable<ResourceLocation> cir) {
        //TODO: Add different breeds
    }
}
