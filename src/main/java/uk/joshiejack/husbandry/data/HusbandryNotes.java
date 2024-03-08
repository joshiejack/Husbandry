package uk.joshiejack.husbandry.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.world.item.HusbandryItems;
import uk.joshiejack.penguinlib.data.generator.AbstractNoteProvider;
import uk.joshiejack.penguinlib.data.generator.builder.CategoryBuilder;
import uk.joshiejack.penguinlib.world.note.Category;
import uk.joshiejack.penguinlib.world.note.Note;

import java.util.Map;

public class HusbandryNotes extends AbstractNoteProvider {
    public HusbandryNotes(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildNotes(Map<ResourceLocation, Category> categories, Map<ResourceLocation, Note> notes) {
        //Guide book
        CategoryBuilder.category().withItemIcon(HusbandryItems.BRUSH.get())
                .withNote("domestication").withNoteIcon().end()
                .withNote("lifespan_note").withNoteIcon().setNoteType("lifespan").end()
                .withNote("lifestyle_note").withNoteIcon().end()
                .withNote("happiness_note").withNoteIcon().end()
                .withNote("cleanliness_note").withItemIcon(HusbandryItems.BRUSH.get()).end()
                .withNote("hunger_note").withItemIcon(HusbandryItems.BIRD_FEED.get()).end()
                .withNote("pregnancy_note").withNoteIcon().setNoteType("pregnancy").end()
                .withNote("products_note").withItemIcon(Items.EGG).end()
                .withNote("treats_note").withItemIcon(HusbandryItems.GENERIC_TREAT.get()).end()
                .withNote("sickle_note").withItemIcon(HusbandryItems.SICKLE.get()).end()
                .withNote("bowl_note").withItemIcon(HusbandryItems.BOWL.get()).end()
                .withNote("trough_note").withItemIcon(HusbandryItems.TROUGH.get()).end()
                .withNote("feeding_tray_note").withItemIcon(HusbandryItems.FEEDING_TRAY.get()).end()
                .withNote("nest_note").withItemIcon(HusbandryItems.NEST.get()).end()
                .withNote("incubator_note").withItemIcon(HusbandryItems.INCUBATOR.get()).end()
                .save(categories, notes, new ResourceLocation(Husbandry.MODID, "care_category"));
    }
}
