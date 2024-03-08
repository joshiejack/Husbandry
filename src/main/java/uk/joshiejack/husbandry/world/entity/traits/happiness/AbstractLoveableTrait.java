package uk.joshiejack.husbandry.world.entity.traits.happiness;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IIconTrait;
import uk.joshiejack.husbandry.api.trait.IRenderTrait;
import uk.joshiejack.penguinlib.util.icon.Icon;
import uk.joshiejack.penguinlib.util.icon.TextureIcon;

public class AbstractLoveableTrait implements IRenderTrait, IIconTrait {
    private static final ResourceLocation MINECRAFT_ICONS = new ResourceLocation("minecraft", "textures/gui/icons.png");
    public static final ResourceLocation HUSBANDRY_ICONS = new ResourceLocation(Husbandry.MODID, "textures/gui/icons.png");
    public static final Icon ICON = new TextureIcon(HUSBANDRY_ICONS, 32, 0, 1);

    @OnlyIn(Dist.CLIENT)
    @Override
    public Icon getIcon(Mob mob, IMobStats<?> stats) {
        return stats.isUnloved() ? ICON.shadowed() : ICON;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, AbstractWidget widget, int x, int y, IMobStats<?> stats) {
        for (int i = 0; i < 10; i++) {
            ResourceLocation rl = i >= stats.getMaxHearts() ? HUSBANDRY_ICONS : MINECRAFT_ICONS;
            graphics.blit(rl, x + 24 + 10 * i, y + 6, 16, 0, 9, 9);
            if (i < stats.getHearts())
                graphics.blit(rl, x + 24 + 10 * i, y + 6, 52, 0, 9, 9);
        }
    }
}
