package io.github.sjouwer.gammautils.common.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public interface IPacket {
    PacketByteBuf encode();

    Identifier getPacketID();
}