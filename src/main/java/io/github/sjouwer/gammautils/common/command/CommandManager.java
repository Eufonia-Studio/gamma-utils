package io.github.sjouwer.gammautils.common.command;

import io.github.sjouwer.gammautils.common.command.gamma.GammaCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class CommandManager {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            GammaCommand.register(dispatcher);
        });
    }

}
