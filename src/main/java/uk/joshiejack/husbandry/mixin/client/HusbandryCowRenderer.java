package uk.joshiejack.husbandry.mixin.client;

import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CowRenderer.class)
public class HusbandryCowRenderer {
    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Cow;)Lnet/minecraft/resources/ResourceLocation;", at = @At("HEAD"))
    private void getTextureLocation(Cow cow, CallbackInfoReturnable<ResourceLocation> cir) {
        //TODO: Add different breeds of cow
    }
}
