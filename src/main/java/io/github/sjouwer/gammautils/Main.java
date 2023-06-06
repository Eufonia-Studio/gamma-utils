package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.common.command.CommandManager;
import io.github.sjouwer.gammautils.server.config.ConfigJsonLoader;
import io.github.sjouwer.gammautils.server.listener.ServerListener;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerListener.register();
        CommandManager.register();
        ConfigJsonLoader.loadFromConfig();
    }
}
