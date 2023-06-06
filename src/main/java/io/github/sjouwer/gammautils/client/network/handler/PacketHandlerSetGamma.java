package io.github.sjouwer.gammautils.client.network.handler;

import io.github.sjouwer.gammautils.client.ClientGamma;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class PacketHandlerSetGamma {

    public static void onReceive(MinecraftClient minecraftClient, ClientPlayNetworkHandler playNetworkHandler, PacketByteBuf buf, PacketSender packetSender) {
        final double NEW_GAMMA = buf.readDouble();
        final double VALUE_CHANGES_PER_TICK = buf.readDouble();
        final boolean SMOOTH_TRANSITION = buf.readBoolean();
        minecraftClient.execute(() -> PacketHandlerSetGamma.runOnMainThread(NEW_GAMMA, VALUE_CHANGES_PER_TICK, SMOOTH_TRANSITION));
    }

    public static void runOnMainThread(double newGamma, double valueChangesPerTick, boolean smoothTransition) {
        ClientGamma.INSTANCE.setGamma(newGamma, valueChangesPerTick, smoothTransition);
    }
}
