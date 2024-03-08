package uk.joshiejack.husbandry.api;

import net.minecraft.world.item.Item;
import uk.joshiejack.husbandry.api.trait.IMobTrait;

import java.util.List;

public interface ISpecies {
    IProducts products();

    int minimumLifespan();

    int maximumLifespan();

    int genericTreats();

    int speciesTreats();

    int daysToBirth();

    int daysToMaturity();

    Item treat();

    List<IMobTrait> traits();
}
