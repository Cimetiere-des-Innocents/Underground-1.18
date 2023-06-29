package xyz.chlamydomonos.underground.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.TranslatableComponent;
import xyz.chlamydomonos.underground.loaders.RegistryLoader;
import xyz.chlamydomonos.underground.world.WorldRule;

public class GVRuleCommand
{
    public static LiteralArgumentBuilder<CommandSourceStack> create()
    {
        return Commands.literal("gv_rule").then(
                Commands.argument("ruleName", ResourceLocationArgument.id())
                        .suggests((context, builder) -> {
                            var allRules = RegistryLoader.GV_RULE.get().getKeys();
                            return SharedSuggestionProvider.suggestResource(allRules, builder);
                        }).executes(new GVRuleCommand.Set())
        ).executes(new GVRuleCommand.Get());
    }

    private static class Set implements Command<CommandSourceStack>
    {
        @Override
        public int run(CommandContext<CommandSourceStack> context)
        {
            var ruleName = ResourceLocationArgument.getId(context, "ruleName");
            var rule = RegistryLoader.GV_RULE.get().getValue(ruleName);
            if(rule == null)
            {
                context.getSource().sendFailure(
                        new TranslatableComponent(
                                "command.underground.gv_rule.not_found",
                                ruleName
                        )
                );
                return 1;
            }
            var worldRule = WorldRule.getInstance(context.getSource().getLevel());
            worldRule.setGvRule(rule);
            worldRule.setDirty();
            context.getSource().sendSuccess(
                    new TranslatableComponent(
                            "command.underground.gv_rule.success",
                            ruleName
                    ),
                    true
            );
            return 0;
        }
    }

    private static class Get implements Command<CommandSourceStack>
    {

        @Override
        public int run(CommandContext<CommandSourceStack> context)
        {
            var worldRule = WorldRule.getInstance(context.getSource().getLevel());
            var ruleName = worldRule.getGvRule().getRegistryName();
            context.getSource().sendSuccess(
                    new TranslatableComponent(
                            "command.underground.gv_rule.get",
                            ruleName
                    ),
                    true
            );
            return 0;
        }
    }
}
