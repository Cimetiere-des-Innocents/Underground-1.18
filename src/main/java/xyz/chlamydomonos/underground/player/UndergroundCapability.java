package xyz.chlamydomonos.underground.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import xyz.chlamydomonos.underground.api.player.GVRule;
import xyz.chlamydomonos.underground.api.player.IUndergroundCapability;
import xyz.chlamydomonos.underground.api.player.Punishment;
import xyz.chlamydomonos.underground.api.player.WealthRule;
import xyz.chlamydomonos.underground.loaders.RegistryLoader;
import xyz.chlamydomonos.underground.world.WorldRule;

import java.util.Objects;

public class UndergroundCapability implements IUndergroundCapability
{
    private final Player player;
    private int gv;
    private boolean inPunishment;
    private GVRule gvRule;
    private WealthRule wealthRule;
    private Punishment punishment;

    public UndergroundCapability(Player player, int gv)
    {
        this.player = player;
        this.gv = gv;
        this.inPunishment = false;
        gvRule = null;
        wealthRule = null;
        punishment = null;
    }

    @Override
    public Player getPlayer()
    {
        return player;
    }

    @Override
    public int getGV()
    {
        return gv;
    }

    @Override
    public void setGV(int gv)
    {
        this.gv = gv;
    }

    @Override
    public void updateGV(int lightLevel)
    {
        gv = getGVRule().getNewValue(gv, player, lightLevel);
    }

    @Override
    public long calculateWealth(int lightLevel)
    {
        return getWealthRule().calculate(player, lightLevel);
    }

    @Override
    public void applyPunishment(int lightLevel)
    {
        getPunishment().apply(player, lightLevel);
    }

    @Override
    public void stopPunishment()
    {
        getPunishment().stop(player);
    }

    @Override
    public boolean isInPunishment()
    {
        return inPunishment;
    }

    @Override
    public void setInPunishment(boolean value)
    {
        inPunishment = value;
    }

    @Override
    public GVRule getGVRule()
    {
        if(gvRule != null)
            return gvRule;
        var worldRule = WorldRule.getInstance(player.level);
        return worldRule.getGvRule();
    }

    @Override
    public void setGVRule(GVRule rule)
    {
        gvRule = rule;
    }

    @Override
    public WealthRule getWealthRule()
    {
        if(wealthRule != null)
            return wealthRule;
        var worldRule = WorldRule.getInstance(player.level);
        return worldRule.getWealthRule();
    }

    @Override
    public void setWealthRule(WealthRule rule)
    {
        wealthRule = rule;
    }

    @Override
    public Punishment getPunishment()
    {
        if(punishment != null)
            return punishment;
        var worldRule = WorldRule.getInstance(player.level);
        return worldRule.getPunishment();
    }

    @Override
    public void setPunishment(Punishment punishment)
    {
        this.punishment = punishment;
    }

    @Override
    public CompoundTag serializeNBT()
    {
        CompoundTag tag = new CompoundTag();
        tag.putInt("gv", gv);
        tag.putBoolean("in_punishment", inPunishment);

        if(gvRule != null)
            tag.putString("gv_rule", Objects.requireNonNull(gvRule.getRegistryName()).toString());

        if(wealthRule != null)
            tag.putString("wealth_rule", Objects.requireNonNull(wealthRule.getRegistryName()).toString());

        if(punishment != null)
            tag.putString("punishment", Objects.requireNonNull(punishment.getRegistryName()).toString());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
        gv = nbt.getInt("gv");
        inPunishment = nbt.getBoolean("in_punishment");

        if(nbt.contains("gv_rule"))
            gvRule = RegistryLoader.GV_RULE.get().getValue(ResourceLocation.tryParse(nbt.getString("gv_rule")));

        if(nbt.contains("wealth_rule"))
            wealthRule = RegistryLoader.WEALTH_RULE.get().getValue(ResourceLocation.tryParse(nbt.getString("wealth_rule")));

        if(nbt.contains("punishment"))
            punishment = RegistryLoader.PUNISHMENT.get().getValue(ResourceLocation.tryParse(nbt.getString("punishment")));
    }
}
