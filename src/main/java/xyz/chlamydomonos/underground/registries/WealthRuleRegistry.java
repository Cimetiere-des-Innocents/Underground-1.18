package xyz.chlamydomonos.underground.registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryBuilder;
import xyz.chlamydomonos.underground.Underground;
import xyz.chlamydomonos.underground.api.player.WealthRule;

public class WealthRuleRegistry extends RegistryBuilder<WealthRule>
{
    public WealthRuleRegistry()
    {
        setName(new ResourceLocation(Underground.MODID, "wealth_rule"));
        setType(WealthRule.class);
    }
}
