package uk.joshiejack.husbandry.world.note;

import net.minecraft.network.chat.Component;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.world.entity.stats.Species;
import uk.joshiejack.penguinlib.world.note.Note;
import uk.joshiejack.penguinlib.world.note.type.NoteType;

import javax.annotation.Nonnull;
import java.util.List;

public class LifespanNoteType extends NoteType {
    private Component component;
    public LifespanNoteType() {
        super("lifespan");
    }

    @Nonnull
    public Component getText(Note note) {
        if (component != null) return component;
        component = note.getText();
        List<Component> list = component.getSiblings();
        Species.TYPES.forEach((type, species) -> {
            list.add(Component.literal("\n ")); //newline before
            list.add(Component.translatable("note.type.husbandry.lifespan", type.getDescription(),
                    (species.minimumLifespan() / Husbandry.HusbandryConfig.daysPerYear.get()),
                    (species.maximumLifespan() / Husbandry.HusbandryConfig.daysPerYear.get())));
        });

        return component;
    }
}