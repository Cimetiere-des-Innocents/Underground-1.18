package xyz.chlamydomonos.underground;

import net.minecraftforge.fml.common.Mod;
import xyz.chlamydomonos.underground.loaders.BlockLoader;
import xyz.chlamydomonos.underground.loaders.TileEntityLoader;

@Mod(Underground.MODID)
public class Underground
{
    public static final String MODID = "underground";

    public Underground()
    {
        BlockLoader.registerAll();
        TileEntityLoader.registerAll();
    }
}
