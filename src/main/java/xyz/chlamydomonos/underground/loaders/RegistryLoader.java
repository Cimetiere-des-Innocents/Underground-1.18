package xyz.chlamydomonos.underground.loaders;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;
import xyz.chlamydomonos.underground.api.player.GVRule;
import xyz.chlamydomonos.underground.api.player.Punishment;
import xyz.chlamydomonos.underground.api.player.WealthRule;
import xyz.chlamydomonos.underground.registries.GVRuleRegistry;
import xyz.chlamydomonos.underground.registries.PunishmentRegistry;
import xyz.chlamydomonos.underground.registries.WealthRuleRegistry;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryLoader
{
    public static final RegistryBuilder<GVRule> GV_RULE_BUILDER = new GVRuleRegistry();
    public static final RegistryBuilder<WealthRule> WEALTH_RULE_BUILDER = new WealthRuleRegistry();
    public static final RegistryBuilder<Punishment> PUNISHMENT_BUILDER = new PunishmentRegistry();

    public static Supplier<IForgeRegistry<GVRule>> GV_RULE;
    public static Supplier<IForgeRegistry<WealthRule>> WEALTH_RULE;
    public static Supplier<IForgeRegistry<Punishment>> PUNISHMENT;
    @SubscribeEvent
    public static void onNewRegistry(NewRegistryEvent event)
    {
        GV_RULE = event.create(GV_RULE_BUILDER);
        WEALTH_RULE = event.create(WEALTH_RULE_BUILDER);
        PUNISHMENT = event.create(PUNISHMENT_BUILDER);
    }
}
