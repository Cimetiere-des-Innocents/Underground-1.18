package xyz.chlamydomonos.underground.registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryBuilder;
import xyz.chlamydomonos.underground.Underground;
import xyz.chlamydomonos.underground.api.player.GVRule;

public class GVRuleRegistry extends RegistryBuilder<GVRule>
{
    public GVRuleRegistry()
    {
        setName(new ResourceLocation(Underground.MODID, "gv_rule"));
        setType(GVRule.class);
    }
}
