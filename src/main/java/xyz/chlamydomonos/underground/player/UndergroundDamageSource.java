package xyz.chlamydomonos.underground.player;

import net.minecraft.world.damagesource.DamageSource;

public class UndergroundDamageSource
{
    public static final DamageSource ON_GROUND = new DamageSource("on_ground")
            .bypassArmor()
            .bypassInvul()
            .bypassMagic();
}
