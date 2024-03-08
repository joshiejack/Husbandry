package uk.joshiejack.husbandry.data;

import net.minecraft.ChatFormatting;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.apache.commons.lang3.text.WordUtils;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.world.item.HusbandryItems;

public class HusbandryLanguage extends LanguageProvider {
    public HusbandryLanguage(PackOutput output) {
        super(output, Husbandry.MODID, "en_us");
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void addTranslations() {
        add("itemGroup.husbandry", "Husbandry");
        add("gui.husbandry.stats", "Mob Stats");
        add("gui.husbandry.notes", "Notes");
        add("gui.husbandry.noentity", "No domesticated entities could be found nearby. Interact with an animal to add it to the book.");
        HusbandryItems.ITEMS.getEntries().forEach((item) -> addItem(item, WordUtils.capitalizeFully(item.getId().getPath().replace("_", " "))));
        add("note.category.husbandry.care_category", "Animal Care");
        //TITLES
        addNoteTitle("lifespan_note", "Lifespans");
        addNoteTitle("lifestyle_note", "Habits");
        addNoteTitle("happiness_note", "Happiness");
        addNoteTitle("cleanliness_note", "Cleanliness");
        addNoteTitle("hunger_note", "Hunger and Feeding");
        addNoteTitle("pregnancy_note", "Pregnancy");
        addNoteTitle("products_note", "Products");
        addNoteTitle("treats_note", "Treating");
        addNoteTitle("sickle_note", "Sickle and Fodder");
        addNoteTitle("bowl_note", "Food Bowl");
        addNoteTitle("trough_note", "Trough");
        addNoteTitle("feeding_tray_note", "Feeding Tray");
        addNoteTitle("nest_note", "Nest");
        addNoteTitle("incubator_note", "Incubator");
        addNoteTitle("domestication", "Domestication");
        //TEXT
        addNoteText("domestication", "In order for an animal to appear in your book to learn about their statistics. You must domesticate them. All you have to do is interact with them in some way, whether it be petting them, feeding them or milking them. Just something involving an interaction and then they should appear in the book.");
        addNoteText("lifespan_note", "Animals no longer will live forever. They will live out their years happily until they reach a certain age. From that point onwards they have an ever increasing chance of dying until they reach the max age. At that point they will be lost to the world. You can see the lifespans for each animal below and on the next page.\n\n" + ChatFormatting.BOLD + "Lifespans" + ChatFormatting.RESET);
        addNoteText("lifestyle_note", "Each animal has its own lifestyle it likes to live. Some absolutely hate all of the elements and will seek shelter in the rain or at night. While others may not mind the rain. All however will seek shelter when there is a thunderstorm. They will become happy if they cannot find the shelter they need after a while.");
        addNoteText("happiness_note", "Animals have a happiness level. You can improve this happiness by taking care of them. You can pet your large animals each day or carry the smaller ones to make them happy. Some animals like to be cleaned and all of them like to be treated. Happiness determines how much the animal will produce for you. Each animal only starts off with two hearts available. You can increase the heart limit up to ten. To do this you will need to feed your animals generic treats and type treats.");
        addNoteText("cleanliness_note", "The larger animals all liked to be cleaned daily. This will increase their happiness. To clean them you will need to get your hands on a brush. With a brush in hand simply hold down right click and keep brushing them until they display heart particles. You can check if an animal needs brushing in the stats section. The brush icon will be greyed out if they have not had a cleaning today.");
        addNoteText("hunger_note", "Animals need food in order to remain happy. They will not die of starvation by default but this can be enabled if you wish. Animals will not produce products if they have not fed been fed for a while. Feeding an animal by hand will increase their happiness while using the automated feeders will not but it will keep them producing products for you.");
        addNoteText("pregnancy_note", "Mammals can be impregnated by using the miracle potion. Each species has a different length of pregnancy. But after those days have passed they will have one to three babies.\n\n" + ChatFormatting.BOLD + "Gestation" + ChatFormatting.RESET);
        addNoteText("products_note", "You can no longer get infinite milk from your cows for example as they will only produce one bucket of milk per day. Well at least until you increase their happiness! Other animals will produce more often instead like the sheep whose wool will grow back quicker than the normal 7 days it will take! You do get more wool from each shearing to compensate. Chickens lay more often and pigs will find truffles more frequently. Rabbits will lose their feet more often. You will need to get the animals happiness up to achieve all of these!");
        addNoteText("treats_note", "If you love your animals you will give them some treats. They all like the generic treats but there are species specific ones that they love to. Treating animals increases the maximum hearts they can have, allowing for more products to be produced as they get happier. You can obtain the treats from a farmer villager. Each animal requires a certain combo of generic and type treats to level up. Really just feed them lots per day to find out how many it will take you!");
        addNoteText("sickle_note", "The sickle is a tool that can quickly cut through leaves, grass and plants. If you use it on tall grass then you will get drops of fodder to feed to your larger animals.");
        addNoteText("bowl_note", "This block is used to feed your cats, dogs and rabbits. Each animal needing a different source of food of course. Place down fish for your cat, raw meat for doggies and carrots for bunnies.");
        addNoteText("trough_note", "In the trough you can place food for pigs or for cows/sheep/horses. They will search out this food if they cannot find any natural grass to munch down upon. You can fill the trough with slop/carrots for the pigs or with wheat, fodder or seagrass for the others.");
        addNoteText("feeding_tray_note", "The feeding tray is a way to feed your chickens automatically. They will search for one when they are hungry and munch down. It's an easy way of keeping them producing eggs for you! You will need to restock it every 2 days with bird feed or seeds.");
        addNoteText("nest_note", "The nest is where your chickens will go to lay their eggs. They will no longer just lay them on the ground. They also find comfort in their nests at night and like to find their way to roost in them.");
        addNoteText("incubator_note", "The incubator is what you will use to hatch your chicken eggs in to baby chicks. Simply place an egg inside of it and after three days it should hatch in to a chick.");
        //TYPES
        addTypeText("lifespan", "%s %s to %s years");
        addTypeText("gestation", "%s %s days");
        addTypeText("litter", "%s to %s babies");
    }

    private void addNoteTitle(String note, String text) {
        add("note.title." + Husbandry.MODID + "." + note, text);
    }

    private void addNoteText(String note, String text) {
        add("note.text." + Husbandry.MODID + "." + note, text);
    }

    private void addTypeText(String type, String text) {
        add("note.type." + Husbandry.MODID + "." + type, text);
    }
}
