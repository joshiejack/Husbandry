package uk.joshiejack.husbandry.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;
import uk.joshiejack.husbandry.world.entity.stats.MobStats;
import uk.joshiejack.penguinlib.client.gui.book.Book;
import uk.joshiejack.penguinlib.client.gui.book.page.AbstractMultiPage;
import uk.joshiejack.penguinlib.util.icon.EntityIcon;
import uk.joshiejack.penguinlib.util.icon.Icon;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("rawtypes")
@OnlyIn(Dist.CLIENT)
public class PageStats extends AbstractMultiPage.Both<Pair<Mob, MobStats>> {
    public static final Icon ICON = new EntityIcon(Holder.direct(EntityType.HORSE), 1, 6);
    public PageStats(Component name) {
        super(name, 10);
    }

    @Override
    protected Icon getIcon() {
        return ICON;
    }

    @Override
    public void initLeft(Book book, int left, int top) {
        super.initLeft(book, left, top);
        if (entries.isEmpty())
            book.addRenderableOnly(new InformationLabel(left, top, book.minecraft().font));
    }

    @Override
    protected void initEntry(Book book, int x, int y, int id, Pair<Mob, MobStats> stats) {
        book.addRenderableOnly(new MobStatsLabel(stats.getLeft(), stats.getRight(), x + 20, y + 10 + id * 30, stats.getLeft().getName()));
    }

    protected List<Pair<Mob, MobStats>> getEntries() {
        assert Minecraft.getInstance().level != null;
        assert Minecraft.getInstance().player != null;
        return Minecraft.getInstance().level.getEntitiesOfClass(Mob.class,
                        new AABB(Minecraft.getInstance().player.blockPosition()).inflate(64D)).stream()
                .filter(e -> MobStats.getStats(e) != null && Objects.requireNonNull(MobStats.getStats(e)).isDomesticated())
                .map((Mob entity) -> Pair.of((Mob) entity, (MobStats)MobStats.getStats(entity)))
                .collect(Collectors.toList());
    }
}
