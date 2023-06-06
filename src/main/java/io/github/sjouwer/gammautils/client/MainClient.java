package io.github.sjouwer.gammautils.client;

import io.github.sjouwer.gammautils.common.network.NetworkManager;
import net.fabricmc.api.ClientModInitializer;

public class MainClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NetworkManager.registerClientPacketReceivers();
    }
}
