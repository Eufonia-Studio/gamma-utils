package io.github.sjouwer.gammautils.common.command.gamma;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.github.sjouwer.gammautils.server.ServerGamma;
import io.github.sjouwer.gammautils.server.config.ConfigJsonLoader;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class GammaConfigCommand {
    public static LiteralArgumentBuilder<ServerCommandSource> register() {
        return CommandManager.literal("config")
                .then(GammaConfigCommand.buildShowGamma()
                        .then(GammaConfigCommand.buildChangeGamma()))
                .then(GammaConfigCommand.buildShowTransitionSpeed()
                        .then(GammaConfigCommand.buildChangeTransitionSpeed()))
                .then(GammaConfigCommand.buildShowGammaStep()
                        .then(GammaConfigCommand.buildChangeGammaStep()))
                .then(GammaConfigCommand.buildToggleGamma())
                .then(GammaConfigCommand.buildIncreaseGamma())
                .then(GammaConfigCommand.buildDecreaseGamma())
                .then(GammaConfigCommand.buildInterpolateGamma());
    }

    private static LiteralArgumentBuilder<ServerCommandSource> buildShowGamma() {
        return CommandManager.literal("gamma").executes(context -> {
            GammaConfigCommand.showConfigValue(context, ConfigJsonLoader.data().currentGamma());
            return 1;
        });
    }

    private static RequiredArgumentBuilder<ServerCommandSource, Double> buildChangeGamma() {
        return CommandManager.argument("gamma", DoubleArgumentType.doubleArg())
                .executes(context -> {
                    ServerGamma.INSTANCE.setGamma(DoubleArgumentType.getDouble(context, "gamma") / 100, 0, false);
                    GammaConfigCommand.showChangeConfigValueMessage(context);
                    return 1;
                });
    }

    private static LiteralArgumentBuilder<ServerCommandSource> buildShowTransitionSpeed() {
        return CommandManager.literal("transition_speed").executes(context -> {
            GammaConfigCommand.showConfigValue(context, ConfigJsonLoader.data().transitionSpeed());
            return 1;
        });
    }

    private static RequiredArgumentBuilder<ServerCommandSource, Integer> buildChangeTransitionSpeed() {
        return CommandManager.argument("transition_speed", IntegerArgumentType.integer())
                .executes(context -> {
                    ConfigJsonLoader.data().setTransitionSpeed(IntegerArgumentType.getInteger(context, "set_transition_speed"));
                    GammaConfigCommand.showChangeConfigValueMessage(context);
                    return 1;
                });
    }

    private static LiteralArgumentBuilder<ServerCommandSource> buildShowGammaStep() {
        return CommandManager.literal("step").executes(context -> {
            GammaConfigCommand.showConfigValue(context, ConfigJsonLoader.data().gammaStep());
            return 1;
        });
    }

    private static RequiredArgumentBuilder<ServerCommandSource, Integer> buildChangeGammaStep() {
        return CommandManager.argument("step", IntegerArgumentType.integer())
                .executes(context -> {
                    ConfigJsonLoader.data().setGammaStep(IntegerArgumentType.getInteger(context, "set_gamma_step"));
                    GammaConfigCommand.showChangeConfigValueMessage(context);
                    return 1;
                });
    }

    private static LiteralArgumentBuilder<ServerCommandSource> buildIncreaseGamma() {
        return CommandManager.literal("increase")
                .then(CommandManager.argument("increase_gamma", DoubleArgumentType.doubleArg())
                        .executes(context -> {
                            ServerGamma.INSTANCE.increaseGamma((DoubleArgumentType.getDouble(context, "increase_gamma")));
                            GammaConfigCommand.showChangeConfigValueMessage(context);
                            return 1;
                        }));
    }

    private static LiteralArgumentBuilder<ServerCommandSource> buildDecreaseGamma() {
        return CommandManager.literal("decrease")
                .then(CommandManager.argument("decrease_gamma", DoubleArgumentType.doubleArg())
                        .executes(context -> {
                            ServerGamma.INSTANCE.decreaseGamma((DoubleArgumentType.getDouble(context, "decrease_gamma")));
                            GammaConfigCommand.showChangeConfigValueMessage(context);
                            return 1;
                        }));
    }

    private static LiteralArgumentBuilder<ServerCommandSource> buildInterpolateGamma() {
        return CommandManager.literal("interpolate")
                .then(CommandManager.argument("interpolate_gamma_to", DoubleArgumentType.doubleArg())
                        .executes(context -> {
                            ServerGamma.INSTANCE.inteporlateGamma((DoubleArgumentType.getDouble(context, "interpolate_gamma_to")), ConfigJsonLoader.data().transitionSpeed());
                            GammaConfigCommand.showChangeConfigValueMessage(context);
                            return 1;
                        }));
    }

    private static LiteralArgumentBuilder<ServerCommandSource> buildToggleGamma() {
        return CommandManager.literal("toggle_gamma")
                .then(CommandManager.argument("toggle_gamma", BoolArgumentType.bool())
                        .executes(context -> {
                            ServerGamma.INSTANCE.toggleGamma(BoolArgumentType.getBool(context, "toggle_gamma"));
                            GammaConfigCommand.showChangeConfigValueMessage(context);
                            return 1;
                        }));
    }

    private static void showChangeConfigValueMessage(CommandContext<ServerCommandSource> context) {
        context.getSource().sendMessage(Text.literal("The current data for this value has been changed correctly").setStyle(Style.EMPTY.withColor(Formatting.GREEN)));
    }

    private static void showConfigValue(CommandContext<ServerCommandSource> context, double value) {
        context.getSource().sendMessage(Text.literal("The current data for this value is: " + value).setStyle(Style.EMPTY.withColor(Formatting.GREEN)));
    }

    private static void showConfigValue(CommandContext<ServerCommandSource> context, int value) {
        context.getSource().sendMessage(Text.literal("The current data for this value is: " + value).setStyle(Style.EMPTY.withColor(Formatting.GREEN)));
    }
}
