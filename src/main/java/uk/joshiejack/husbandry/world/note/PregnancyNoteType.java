package uk.joshiejack.husbandry.world.note;

import net.minecraft.network.chat.Component;
import uk.joshiejack.husbandry.world.entity.stats.Species;
import uk.joshiejack.husbandry.world.entity.traits.lifestyle.MammalTrait;
import uk.joshiejack.penguinlib.world.note.Note;
import uk.joshiejack.penguinlib.world.note.type.NoteType;

import javax.annotation.Nonnull;
import java.util.List;

public class PregnancyNoteType extends NoteType {
    private Component component;
    public PregnancyNoteType() {
        super("pregnancy");
    }

    @Nonnull
    public Component getText(Note note) {
        if (component != null) return component;
        component = note.getText();
        List<Component> list = component.getSiblings();
        Species.TYPES.forEach((type, species) -> {
            if (species.traits().stream().anyMatch(t -> t instanceof MammalTrait)) {
                list.add(Component.literal("\n ")); //newline before
                list.add(Component.translatable("note.type.husbandry.gestation", type.getDescription(), species.daysToBirth()));
            }
        });
        return component;
    }
}