package uk.joshiejack.husbandry.api.trait;

import net.minecraft.nbt.CompoundTag;

public interface IDataTrait extends IMobTrait {
    void load(CompoundTag nbt);

    void save(CompoundTag nbt);
}
