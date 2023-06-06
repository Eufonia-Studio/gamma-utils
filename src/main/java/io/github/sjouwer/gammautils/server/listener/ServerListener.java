package io.github.sjouwer.gammautils.server.listener;

import io.github.sjouwer.gammautils.common.network.NetworkManager;
import io.github.sjouwer.gammautils.server.ServerGamma;
import io.github.sjouwer.gammautils.server.network.PacketS2CSetGamma;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;

public class ServerListener {

    private static MinecraftServer server;

    public static void register() {
        ServerLifecycleEvents.SERVER_STARTED.register(ServerListener::onServerStarted);
        ServerPlayConnectionEvents.JOIN.register(ServerListener::onPlayerConnect);
    }

    private static void onServerStarted(MinecraftServer server) {
        ServerListener.server = server;
    }

    private static void onPlayerConnect(ServerPlayNetworkHandler playNetworkHandler, PacketSender packetSender, MinecraftServer server) {
        NetworkManager.sendTo(new PacketS2CSetGamma(ServerGamma.INSTANCE.gamma(), 0, false), playNetworkHandler.getPlayer());
    }

    public static MinecraftServer server() {
        return ServerListener.server;
    }
}
