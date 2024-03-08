package uk.joshiejack.husbandry.mixin.client;

import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Pig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PigRenderer.class)
public class HusbandryPigRenderer {
    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Pig;)Lnet/minecraft/resources/ResourceLocation;", at = @At("HEAD"))
    private void getTextureLocation(Pig pig, CallbackInfoReturnable<ResourceLocation> cir) {
        //TODO: Add different breeds of pigs

    }
}
