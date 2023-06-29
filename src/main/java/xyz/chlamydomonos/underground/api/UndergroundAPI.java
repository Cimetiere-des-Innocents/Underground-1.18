package xyz.chlamydomonos.underground.api;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import xyz.chlamydomonos.underground.api.player.IUndergroundCapability;

import java.lang.reflect.Field;

public class UndergroundAPI
{
    public static final String MODID;
    public static final Capability<IUndergroundCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
    public static final DamageSource ON_GROUND;
    static {
        try
        {
            Class<?> mainClass = Class.forName("xyz.chlamydomonos.underground.Underground");
            Field modid = mainClass.getField("MODID");
            MODID = (String) modid.get(null);

            Class<?> damageSourceClass = Class.forName("xyz.chlamydomonos.underground.player.UndergroundDamageSource");
            Field damageSource = damageSourceClass.getField("ON_GROUND");
            ON_GROUND = (DamageSource) damageSource.get(null);
        }
        catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e)
        {
            throw new RuntimeException("Cannot find mod underground", e);
        }
    }
}
