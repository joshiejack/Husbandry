//package uk.joshiejack.husbandry.plugin.kubejs;
//
//import net.minecraft.world.entity.Mob;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import uk.joshiejack.husbandry.api.IMobStats;
//
//import javax.annotation.Nullable;
//import java.util.ArrayList;
//import java.util.List;
//
//public class IMobStatsJS {
//    private final IMobStats<?> stats;
//    private final Mob mob;
//
//    public IMobStatsJS(Mob mob, IMobStats<?> stats) {
//        this.mob = mob;
//        this.stats = stats;
//    }
//
//    /* Product data */
//    public int getDaysBetweenProducts() {
//        return stats.getSpecies().getProducts().getDaysBetweenProducts();
//    }
//
//    public List<ItemStack> getProduct(@Nullable Player player) {
//        return new ArrayList<>(stats.getSpecies().getProducts().getProduct(mob, player));
//    }
//
//    /* Species data */
//    public int getMinAge() {
//        return stats.getSpecies().getMinAge();
//    }
//
//    public int getMaxAge() {
//        return stats.getSpecies().getMaxAge();
//    }
//
//    public int getGenericTreats() {
//        return stats.getSpecies().getGenericTreats();
//    }
//
//    public int getSpeciesTreats() {
//        return stats.getSpecies().getSpeciesTreats();
//    }
//
//    public int getDaysToBirth() {
//        return stats.getSpecies().getDaysToBirth();
//    }
//
//    public int getDaysToMaturity() {
//        return stats.getSpecies().getDaysToMaturity();
//    }
//
//    public Item getTreat() {
//        return stats.getSpecies().getTreat();
//    }
//
//    /* Mob Stats */
//    public int getHappiness() {
//        return stats.getHappiness();
//    }
//
//    public void setHappiness(int value) {
//        stats.setHappiness(mob, value);
//    }
//
//    public int getHappinessModifier() {
//        return stats.getHappinessModifier();
//    }
//
//    public void setHappinessModifier(int value) {
//        stats.setHappinessModifier(value);
//    }
//
//    public void decreaseHappiness(int happiness) {
//        stats.decreaseHappiness(mob, happiness);
//    }
//
//    public void increaseHappiness(int happiness) {
//        stats.increaseHappiness(mob, happiness);
//    }
//
//    public void feed() {
//        stats.feed();
//    }
//
//    public int getHunger() {
//        return stats.getHunger();
//    }
//
//    public boolean isUnloved() {
//        return stats.isUnloved();
//    }
//
//    public boolean setLoved() {
//        return stats.setLoved(mob);
//    }
//
//    public boolean canProduceProduct() {
//        return stats.canProduceProduct(mob);
//    }
//
//    public void setProduced(int amount) {
//        stats.setProduced(mob, amount);
//    }
//
//    public boolean isHungry() {
//        return stats.isHungry();
//    }
//
//    public int getMaxHearts() {
//        return stats.getMaxHearts();
//    }
//
//    public int getHearts() {
//        return stats.getHearts();
//    }
//
//    public void setHearts(int value) {
//        stats.setHearts(mob, value);
//    }
//
//    public int getMaxHappiness() {
//        return stats.getMaxHappiness();
//    }
//
//    public void resetProduct() {
//        stats.resetProduct(mob);
//    }
//
//    public boolean isDomesticated() {
//        return stats.isDomesticated();
//    }
//}
