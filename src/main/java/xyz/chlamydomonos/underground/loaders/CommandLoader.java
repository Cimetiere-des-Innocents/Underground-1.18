package xyz.chlamydomonos.underground.loaders;

import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.chlamydomonos.underground.command.GVRuleCommand;

@Mod.EventBusSubscriber
public class CommandLoader
{
    @SubscribeEvent
    public static void onRegister(RegisterCommandsEvent event)
    {
        event.getDispatcher().register(
                Commands.literal("underground")
                        .then(GVRuleCommand.create())
        );
    }
}
