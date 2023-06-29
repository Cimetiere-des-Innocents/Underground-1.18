package xyz.chlamydomonos.underground.player;

import moze_intel.projecte.api.ProjectEAPI;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import xyz.chlamydomonos.underground.Underground;
import xyz.chlamydomonos.underground.api.player.WealthRule;

public class DefaultWealthRule extends WealthRule
{
    public DefaultWealthRule()
    {
        setRegistryName(Underground.MODID, "default");
    }
    @Override
    public long calculate(Player player, int lightLevel)
    {
        long out = 0L;
        var proxy = ProjectEAPI.getEMCProxy();
        for (ItemStack item : player.getInventory().items)
        {
            if(proxy != null)
                out += proxy.getValue(item) * item.getCount();
            else
                out += item.getCount() * 1024L;
        }

        for(ItemStack item : player.getAllSlots())
        {
            if(proxy != null)
                out += proxy.getValue(item);
            else
                out += item.getCount() * 1024L;
        }

        return out;
    }
}
