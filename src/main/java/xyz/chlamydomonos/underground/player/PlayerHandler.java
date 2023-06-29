package xyz.chlamydomonos.underground.player;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.chlamydomonos.underground.Underground;
import xyz.chlamydomonos.underground.api.UndergroundAPI;

@Mod.EventBusSubscriber
public class PlayerHandler
{
    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event)
    {
        var entity = event.getObject();
        if(entity instanceof Player)
            event.addCapability(
                    new ResourceLocation(Underground.MODID, "underground"),
                    new UndergroundCapabilityProvider((Player) entity)
            );
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
        var oldSpeedCap = event.getOriginal().getCapability(UndergroundAPI.CAPABILITY);
        var newSpeedCap = event.getPlayer().getCapability(UndergroundAPI.CAPABILITY);
        if (oldSpeedCap.isPresent() && newSpeedCap.isPresent()) {
            newSpeedCap.ifPresent((newCap) -> oldSpeedCap.ifPresent((oldCap) -> {
                newCap.deserializeNBT(oldCap.serializeNBT());
                newCap.setGV(600);
                newCap.stopPunishment();
            }));
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if(event.player.level.isClientSide || event.player.isCreative() || event.player.isDeadOrDying())
            return;

        event.player.getCapability(UndergroundAPI.CAPABILITY).ifPresent(capability -> {
            int lightLevel = event.player.level.getBrightness(LightLayer.SKY, event.player.blockPosition());
            capability.updateGV(lightLevel);
            if(capability.getGV() <= 0)
                capability.applyPunishment(lightLevel);
            else if(capability.isInPunishment())
                capability.stopPunishment();
        });
    }
}
