package uk.joshiejack.husbandry.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import uk.joshiejack.husbandry.api.trait.IIconTrait;
import uk.joshiejack.husbandry.api.trait.IRenderTrait;
import uk.joshiejack.husbandry.world.entity.stats.MobStats;
import uk.joshiejack.husbandry.world.entity.traits.TraitType;
import uk.joshiejack.penguinlib.util.icon.ItemIcon;

import javax.annotation.Nonnull;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
public class MobStatsLabel extends AbstractWidget {
    private static final Vector3f TRANSLATION = new Vector3f();
    private static final Quaternionf ANGLE = (new Quaternionf()).rotationXYZ(-65.0F, 0.0F, 3.1415927F);
    private final MobStats<?> stats;
    private final WeakReference<Mob> mobReference;
    private final List<IIconTrait> iconTraits;

    public MobStatsLabel(Mob mob, MobStats<?> stats, int x, int y, Component name) {
        super(x, y, 120, 24, name);
        this.stats = stats;
        this.mobReference = new WeakReference<>(mob);
        List<IIconTrait> unfiltered = stats.getTraits(TraitType.ICON);
        iconTraits = unfiltered.stream()
                .filter(icon -> icon.getIcon(mob, stats) != ItemIcon.EMPTY)
                .collect(Collectors.toList());
    }

    @Override
    public void renderWidget(@Nonnull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        Mob mob = mobReference.get();
        if (mob == null) return;
        Minecraft mc = Minecraft.getInstance();
        InventoryScreen.renderEntityInInventory(graphics, (float)(getX() + 10), (float)(getY() + 16), 1, TRANSLATION, ANGLE, null, mob);
        //InventoryScreen.renderEntityInInventory(graphics,getX() + 10, getY() + 16, 10, -180, 0, stats.getEntity());
        List<IRenderTrait> traits = stats.getTraits(TraitType.RENDER);
        traits.forEach(trait -> trait.render(graphics, this, getX(), getY(), stats));
        PoseStack pose = graphics.pose();
        for (int i = 0; i < iconTraits.size(); i++) {
            pose.pushPose();
            pose.scale(0.5F, 0.5F, 0.5F);
            iconTraits.get(i).getIcon(mob, stats).render(mc, graphics,(getX() + (115 - (i * 9))) * 2, (getY() - 3) * 2);
            pose.popPose();
        }

        pose.pushPose();
        pose.translate(0D, 0D, 110D);
        graphics.drawString(mc.font, mob.getName(), getX() + 4, getY() - 8, 0xFFFFFF);
        pose.popPose();
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput p_259858_) {

    }
}