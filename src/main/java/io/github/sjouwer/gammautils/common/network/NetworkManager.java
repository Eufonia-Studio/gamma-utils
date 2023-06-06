package io.github.sjouwer.gammautils.common.network;

import io.github.sjouwer.gammautils.Reference;
import io.github.sjouwer.gammautils.client.network.handler.PacketHandlerSetGamma;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class NetworkManager {

    public static final Identifier PACKET_SET_GAMMA = new Identifier(Reference.MODID, "set_gamma");

    public static void registerClientPacketReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(NetworkManager.PACKET_SET_GAMMA, PacketHandlerSetGamma::onReceive);
    }

    public static void sendTo(IPacket packet, ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, packet.getPacketID(), packet.encode());
    }

    public static void sendToAll(IPacket packet, MinecraftServer server) {
        for (ServerPlayerEntity serverPlayer : PlayerLookup.all(server)) {
            ServerPlayNetworking.send(serverPlayer, packet.getPacketID(), packet.encode());
        }
    }
}
