package xyz.chlamydomonos.underground.world;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;
import xyz.chlamydomonos.underground.api.player.GVRule;
import xyz.chlamydomonos.underground.api.player.Punishment;
import xyz.chlamydomonos.underground.api.player.WealthRule;
import xyz.chlamydomonos.underground.loaders.RegistryLoader;
import xyz.chlamydomonos.underground.player.DefaultPunishment;
import xyz.chlamydomonos.underground.player.DefaultWealthRule;
import xyz.chlamydomonos.underground.player.SafeGVRule;

import java.util.Objects;

public class WorldRule extends SavedData
{
    private GVRule gvRule = new SafeGVRule();
    private WealthRule wealthRule = new DefaultWealthRule();
    private Punishment punishment = new DefaultPunishment();

    public WorldRule()
    {
        super();
    }

    public WorldRule(@NotNull CompoundTag tag)
    {
        gvRule = RegistryLoader.GV_RULE.get().getValue(ResourceLocation.tryParse(tag.getString("gvRule")));
        wealthRule = RegistryLoader.WEALTH_RULE.get().getValue(ResourceLocation.tryParse(tag.getString("wealthRule")));
        punishment = RegistryLoader.PUNISHMENT.get().getValue(ResourceLocation.tryParse(tag.getString("punishment")));
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag pCompoundTag)
    {
        pCompoundTag.putString("gvRule", Objects.requireNonNull(gvRule.getRegistryName()).toString());
        pCompoundTag.putString("wealthRule", Objects.requireNonNull(wealthRule.getRegistryName()).toString());
        pCompoundTag.putString("punishment", Objects.requireNonNull(punishment.getRegistryName()).toString());
        return pCompoundTag;
    }

    public static WorldRule getInstance(Level level) {
        if(level.isClientSide) {
            throw new RuntimeException("Trying to get data from a client level");
        }
        var serverLevel = Objects.requireNonNull(level.getServer()).getLevel(Level.OVERWORLD);
        return Objects.requireNonNull(serverLevel).getDataStorage().computeIfAbsent(
                WorldRule::new,
                WorldRule::new,
                "underground:rule"
                                                                                   );
    }

    public Punishment getPunishment()
    {
        return punishment;
    }

    public void setPunishment(Punishment punishment)
    {
        this.punishment = punishment;
    }

    public WealthRule getWealthRule()
    {
        return wealthRule;
    }

    public void setWealthRule(WealthRule wealthRule)
    {
        this.wealthRule = wealthRule;
    }

    public GVRule getGvRule()
    {
        return gvRule;
    }

    public void setGvRule(GVRule gvRule)
    {
        this.gvRule = gvRule;
    }
}
