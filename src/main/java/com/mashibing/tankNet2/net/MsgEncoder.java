package com.mashibing.tankNet2.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/*
将发送消息类型，消息长度，消息体
 */
public class MsgEncoder extends MessageToByteEncoder<Msg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Msg msg, ByteBuf byteBuf) throws Exception {
        System.out.println("TankStartMovingMsg ordinal(): "+msg.getMsgType().ordinal());
        byteBuf.writeInt(msg.getMsgType().ordinal());
        byte[] bytes = msg.toBytes();
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }
}
