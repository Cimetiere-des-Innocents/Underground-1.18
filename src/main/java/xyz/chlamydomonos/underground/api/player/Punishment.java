package xyz.chlamydomonos.underground.api.player;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class Punishment extends ForgeRegistryEntry<Punishment>
{
    public abstract void apply(Player player, int lightLevel);
    public abstract void stop(Player player);
}
