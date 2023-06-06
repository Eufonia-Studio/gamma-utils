package io.github.sjouwer.gammautils.server.network;

import io.github.sjouwer.gammautils.common.network.IPacket;
import io.github.sjouwer.gammautils.common.network.NetworkManager;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class PacketS2CSetGamma implements IPacket {

    private final double NEW_GAMMA, VALUE_CHANGES_PER_TICK;
    private final boolean SMOOTH_TRANSITION;

    public PacketS2CSetGamma(double newGamma, double valueChangesPerTick, boolean smoothTransition) {
        this.NEW_GAMMA = newGamma;
        this.VALUE_CHANGES_PER_TICK = valueChangesPerTick;
        this.SMOOTH_TRANSITION = smoothTransition;
    }

    @Override
    public PacketByteBuf encode() {
        final PacketByteBuf BUF = PacketByteBufs.create();
        BUF.writeDouble(this.NEW_GAMMA);
        BUF.writeDouble(this.VALUE_CHANGES_PER_TICK);
        BUF.writeBoolean(this.SMOOTH_TRANSITION);
        return BUF;
    }

    @Override
    public Identifier getPacketID() {
        return NetworkManager.PACKET_SET_GAMMA;
    }
}
