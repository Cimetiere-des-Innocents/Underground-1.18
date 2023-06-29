package xyz.chlamydomonos.underground.api.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;

public interface IUndergroundCapability extends INBTSerializable<CompoundTag>
{
    Player getPlayer();
    int getGV();
    void setGV(int gv);
    void updateGV(int lightLevel);
    long calculateWealth(int lightLevel);
    void applyPunishment(int lightLevel);
    void stopPunishment();
    boolean isInPunishment();
    void setInPunishment(boolean value);
    GVRule getGVRule();
    void setGVRule(GVRule rule);
    WealthRule getWealthRule();
    void setWealthRule(WealthRule rule);
    Punishment getPunishment();
    void setPunishment(Punishment punishment);
}
