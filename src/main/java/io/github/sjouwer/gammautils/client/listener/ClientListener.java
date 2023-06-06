package io.github.sjouwer.gammautils.client.listener;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;

public class ClientListener {

    static {
        ClientPlayConnectionEvents.DISCONNECT.register(ClientListener::onPlayerDisconnect);
    }

    private static void onPlayerDisconnect(ClientPlayNetworkHandler playNetworkHandler, MinecraftClient minecraftClient) {
        minecraftClient.options.getGamma().setValue(100D);
    }
}
