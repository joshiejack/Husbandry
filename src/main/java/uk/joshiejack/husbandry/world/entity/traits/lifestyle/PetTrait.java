package uk.joshiejack.husbandry.world.entity.traits.lifestyle;

import net.minecraft.nbt.CompoundTag;
import uk.joshiejack.husbandry.api.trait.IDataTrait;

public class PetTrait implements IDataTrait {
    private int skill;

    @Override
    public void save(CompoundTag tag) {
        tag.putInt("Skill", skill);
    }

    @Override
    public void load(CompoundTag nbt) {
        skill = nbt.getInt("Skill");
    }
}
