package uk.joshiejack.husbandry.api.trait;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.api.IMobStats;

public interface IRenderTrait extends IMobTrait {
    @OnlyIn(Dist.CLIENT)
    void render(GuiGraphics graphics, AbstractWidget widget, int x, int y, IMobStats<?> stats);
}