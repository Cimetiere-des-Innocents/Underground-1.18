package xyz.chlamydomonos.underground.loaders;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.chlamydomonos.underground.api.player.GVRule;
import xyz.chlamydomonos.underground.api.player.Punishment;
import xyz.chlamydomonos.underground.api.player.WealthRule;
import xyz.chlamydomonos.underground.player.DefaultGVRule;
import xyz.chlamydomonos.underground.player.DefaultPunishment;
import xyz.chlamydomonos.underground.player.DefaultWealthRule;
import xyz.chlamydomonos.underground.player.SafeGVRule;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CustomRegistryLoader
{
    public static final GVRule DEFAULT_GV_RULE = new DefaultGVRule();
    public static final GVRule SAFE_GV_RULE = new SafeGVRule();
    public static final WealthRule DEFAULT_WEALTH_RULE = new DefaultWealthRule();
    public static final Punishment DEFAULT_PUNISHMENT = new DefaultPunishment();

    @SubscribeEvent
    public static void onRegisterGVRule(RegistryEvent.Register<GVRule> event)
    {
        event.getRegistry().registerAll(DEFAULT_GV_RULE, SAFE_GV_RULE);
    }

    @SubscribeEvent
    public static void onRegisterWealthRule(RegistryEvent.Register<WealthRule> event)
    {
        event.getRegistry().registerAll(DEFAULT_WEALTH_RULE);
    }

    @SubscribeEvent
    public static void onRegisterPunishment(RegistryEvent.Register<Punishment> event)
    {
        event.getRegistry().registerAll(DEFAULT_PUNISHMENT);
    }
}
