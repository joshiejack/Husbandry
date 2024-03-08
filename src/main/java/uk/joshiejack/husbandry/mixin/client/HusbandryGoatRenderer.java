package uk.joshiejack.husbandry.mixin.client;

import net.minecraft.client.renderer.entity.GoatRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.goat.Goat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GoatRenderer.class)
public class HusbandryGoatRenderer {
    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/animal/goat/Goat;)Lnet/minecraft/resources/ResourceLocation;", at = @At("HEAD"))
    private void getTextureLocation(Goat goat, CallbackInfoReturnable<ResourceLocation> cir) {
        //TODO: Add different breeds
    }
}
