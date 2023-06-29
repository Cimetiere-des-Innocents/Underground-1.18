package xyz.chlamydomonos.underground.api.player;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class WealthRule extends ForgeRegistryEntry<WealthRule>
{
    public abstract long calculate(Player player, int lightLevel);
}
