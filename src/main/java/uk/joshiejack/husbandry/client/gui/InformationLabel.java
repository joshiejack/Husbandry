package uk.joshiejack.husbandry.client.gui;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractStringWidget;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class InformationLabel extends AbstractStringWidget {
    public InformationLabel(int x, int y, Font font) {
        super(x, y, 120, 24, Component.translatable("gui.husbandry.noentity"), font);
    }

    @Override
    public void renderWidget(@Nonnull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.drawWordWrap(getFont(), getMessage(), getX() + 22, getY() + 8, 120, 4210752);
    }
}