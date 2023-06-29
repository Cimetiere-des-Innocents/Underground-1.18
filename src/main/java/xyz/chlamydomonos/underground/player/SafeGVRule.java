package xyz.chlamydomonos.underground.player;

import net.minecraftforge.common.extensions.IForgePlayer;
import xyz.chlamydomonos.underground.Underground;
import xyz.chlamydomonos.underground.api.player.GVRule;

public class SafeGVRule extends GVRule
{
    public SafeGVRule()
    {
        setRegistryName(Underground.MODID, "safe");
    }

    @Override
    public int getNewValue(int originalValue, IForgePlayer player, int lightLevel)
    {
        return 600;
    }
}
