package xyz.chlamydomonos.underground.registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryBuilder;
import xyz.chlamydomonos.underground.Underground;
import xyz.chlamydomonos.underground.api.player.Punishment;

public class PunishmentRegistry extends RegistryBuilder<Punishment>
{
    public PunishmentRegistry()
    {
        setName(new ResourceLocation(Underground.MODID, "punishment"));
        setType(Punishment.class);
    }
}
