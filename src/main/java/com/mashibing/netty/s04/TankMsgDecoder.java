package com.mashibing.netty.s04;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/*
解码器
 */
public class TankMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes()<8)return; // 解决TCP 拆包 粘包 问题

        byteBuf.markReaderIndex();

        int x = byteBuf.readInt();
        int y = byteBuf.readInt();
        list.add(new TankMsg(x,y));
    }
}
