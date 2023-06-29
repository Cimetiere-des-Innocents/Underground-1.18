package xyz.chlamydomonos.underground.player;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import xyz.chlamydomonos.underground.Underground;
import xyz.chlamydomonos.underground.api.UndergroundAPI;
import xyz.chlamydomonos.underground.api.player.Punishment;
import xyz.chlamydomonos.underground.blocks.GroundExplosionBlock;
import xyz.chlamydomonos.underground.loaders.BlockLoader;

public class DefaultPunishment extends Punishment
{
    public DefaultPunishment()
    {
        setRegistryName(Underground.MODID, "default");
    }

    @Override
    public void apply(Player player, int lightLevel)
    {
        player.getCapability(UndergroundAPI.CAPABILITY).ifPresent(cap -> {
            long wealth = cap.calculateWealth(lightLevel);
            if(wealth < 1024)
            {
                cap.setInPunishment(true);
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 30, 3));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 3));
            }
            else if(wealth < 32768)
            {
                player.hurt(UndergroundAPI.ON_GROUND, Float.MAX_VALUE);
                cap.stopPunishment();
                cap.setGV(600);
            }
            else
            {
                player.hurt(UndergroundAPI.ON_GROUND, Float.MAX_VALUE);
                ((GroundExplosionBlock)BlockLoader.GROUND_EXPLOSION.get()).createBlock(
                        (ServerLevel) player.level,
                        player.blockPosition(),
                        player,
                        (int) (wealth / 8192)
                );
                cap.stopPunishment();
                cap.setGV(600);
            }
        });
    }

    @Override
    public void stop(Player player)
    {
        player.removeEffect(MobEffects.BLINDNESS);
        player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
    }
}
