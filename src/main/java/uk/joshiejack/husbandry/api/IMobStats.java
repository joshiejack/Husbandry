package uk.joshiejack.husbandry.api;

import net.minecraft.world.entity.Mob;

public interface IMobStats<E extends Mob> {
    /**
     * Retrieves the species of this animal
     * @return  The species of this animal
     **/
    ISpecies getSpecies();

    /**
     * Retrieves the happiness of this animal
     * @return  The happiness of this animal
     **/
    int getHappiness();

    /**
     * Sets the happiness of this animal
     * @param mob   The mob entity
     * @param value The new happiness value
     **/
    void setHappiness(Mob mob, int value);

    /**
     * Retrieves the happiness modifier of this animal
     * @return  The happiness modifier of this animal
     **/
    int getHappinessModifier();

    /**
     * Sets the happiness modifier of this animal
     * @param value The new happiness modifier value
     **/
    void setHappinessModifier(int value);


    /**
     * Decreases the happiness of this animal
     * @param mob       The mob entity
     * @param happiness The amount to decrease by
     */
    void decreaseHappiness(Mob mob, int happiness);

    /**
     * Increases the happiness of this animal
     * @param mob       The mob entity
     * @param happiness The amount to increase by
     */
    void increaseHappiness(Mob mob, int happiness);

    /**
     * Calls the feed method on the animal
     **/
    void feed();

    /**
     * Retrieves the hunger of this animal
     * @return The hunger of this animal
     */
    int getHunger();

    /**
     * Retrieves whether this animal has been loved today
     * @return Whether this animal has been loved today
     */
    boolean isUnloved();

    /**
     * Marks the animal as loved
     * @param mob   The mob entity
     * @return true
     */
    boolean setLoved(Mob mob);

    /**
     * Returns whether this animal can produce a product
     * @param mob   The mob entity
     * @return  Whether this animal can produce a product
     */
    boolean canProduceProduct(Mob mob);

    /**
     * Sets the amount of product this animal has produced
     * @param mob       The mob entity
     * @param amount    The amount of product this animal has produced
     */
    void setProduced(Mob mob, int amount);

    /**
     * Returns whether this animal is hungry
     * @return  Whether this animal is hungry
     */
    boolean isHungry();

    /**
     * Returns the maximum number of hearts this animal can have
     * @return  The maximum number of hearts this animal can have
     */
    int getMaxHearts();

    /**
     * Returns the number of hearts this animal has
     * @return  The number of hearts this animal has
     */
    int getHearts();

    /**
     * Sets the number of hearts this animal has
     * @param mob      The mob entity
     * @param hearts    The number of hearts this animal has
     */
    void setHearts(Mob mob, int hearts);

    /**
     * Returns the maximum happiness this animal can have
     * @return  The maximum happiness this animal can have
     */
    int getMaxHappiness();

    /**
     * Returns the number of products this animal has produced
     * @param mob   The mob entity
     * @return  The number of products this animal has produced
     */
    void resetProduct(Mob mob);

    /**
     * Returns whether this animal is domesticated
     * @return  Whether this animal is domesticated
     */
    boolean isDomesticated();
}
