package uk.joshiejack.husbandry.world.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.apache.logging.log4j.Level;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.trait.IMobTrait;
import uk.joshiejack.husbandry.network.RequestDataPacket;
import uk.joshiejack.husbandry.world.entity.stats.MobStats;
import uk.joshiejack.husbandry.world.entity.stats.Products;
import uk.joshiejack.husbandry.world.entity.stats.Species;
import uk.joshiejack.penguinlib.event.DatabaseLoadedEvent;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.util.icon.ItemIcon;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Husbandry.MODID)
public class MobDataLoader {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Husbandry.MODID);
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<MobStats<Mob>>> MOB_STATS = ATTACHMENT_TYPES.register("mob_stats",() -> AttachmentType.serializable(() -> new MobStats<>(Species.NONE)).build());

    public static final Map<String, IMobTrait> TRAITS = Maps.newHashMap();

    @SubscribeEvent
    public static void onDatabaseLoaded(DatabaseLoadedEvent event) {
        Map<String, Species> types = Maps.newHashMap();
        event.table("mob_species").rows().forEach(row -> {
            String name = row.get("name");
            List<IMobTrait> traits = Lists.newArrayList();
            event.table("mob_traits").where("species=" + name)
                    .forEach(trait -> traits.add(TRAITS.get(trait.get("trait").toString())));
            Products products = row.get("products loot table").equals("none") ? Products.NONE :
                    new Products(row.get("product frequency"), row.getRL("products loot table"), new ItemIcon(new ItemStack(row.item("product icon"))));
            Species type = new Species(row.get("min age"), row.get("max age"),
                    row.item("treat item"),
                    row.get("generic treats"), row.get("species treats")
                    , row.get("days to birth"), row.get("days to maturity"),
                    products, traits);
            types.put(name, type);
            Husbandry.LOGGER.log(Level.INFO, "Registered a new mob species: " + name);
        });

        event.table("mob_entities").rows().forEach(entities -> {
            String name = entities.get("species");
            EntityType<?> type = entities.entity();
            if (type != null) {
                Species.TYPES.put(type, types.get(name));
                Husbandry.LOGGER.log(Level.INFO, "Registered the entity " + entities.get("entity") + " as a " + name);
            }
        });
    }

    @SubscribeEvent
    public static void onEntityJoinedWorld(EntityJoinLevelEvent event) {
        //Request data about this mob from the server
        if (event.getLevel().isClientSide)
            PenguinNetwork.sendToServer(new RequestDataPacket(event.getEntity().getId()));
    }
}
