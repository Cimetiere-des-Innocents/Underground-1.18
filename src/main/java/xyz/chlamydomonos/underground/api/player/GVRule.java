package xyz.chlamydomonos.underground.api.player;

import net.minecraftforge.common.extensions.IForgePlayer;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class GVRule extends ForgeRegistryEntry<GVRule>
{
    public abstract int getNewValue(int originalValue, IForgePlayer player, int lightLevel);
}
