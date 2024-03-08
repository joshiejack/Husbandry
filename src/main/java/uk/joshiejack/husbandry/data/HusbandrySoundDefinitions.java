package uk.joshiejack.husbandry.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import uk.joshiejack.husbandry.Husbandry;

public class HusbandrySoundDefinitions extends SoundDefinitionsProvider {
    public HusbandrySoundDefinitions(PackOutput output, ExistingFileHelper helper) {
        super(output, Husbandry.MODID, helper);
    }

    @Override
    public void registerSounds() {
        this.add(Husbandry.HusbandrySounds.BRUSH, definition().subtitle("subtitles.brush").with(sound("brush1"), sound("brush2"), sound("brush3")));
    }

    protected static SoundDefinition.Sound sound(final String name) {
        return sound(new ResourceLocation(Husbandry.MODID, name));
    }
}
