package xyz.chlamydomonos.underground.player;

import net.minecraft.Util;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.extensions.IForgePlayer;
import xyz.chlamydomonos.underground.Underground;
import xyz.chlamydomonos.underground.api.player.GVRule;

public class DefaultGVRule extends GVRule
{
    public DefaultGVRule()
    {
        setRegistryName(Underground.MODID, "default");
    }
    @Override
    public int getNewValue(int originalValue, IForgePlayer player, int lightLevel)
    {
        if(lightLevel == 0)
            return 600;
        var newValue = originalValue > 0 ? originalValue - 1 : 0;
        if(newValue < 600 && newValue % 20 == 0 && player instanceof ServerPlayer serverPlayer)
            serverPlayer.sendMessage(
                    new TranslatableComponent(
                            "message.underground.gv",
                            newValue
                    ),
                    Util.NIL_UUID
            );
        return newValue;
    }
}
