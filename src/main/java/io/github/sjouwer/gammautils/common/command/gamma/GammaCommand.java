package io.github.sjouwer.gammautils.common.command.gamma;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class GammaCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("gamma")
                .requires(sourceStack -> sourceStack.hasPermissionLevel(4))
                .then(GammaConfigCommand.register()));
    }
}
