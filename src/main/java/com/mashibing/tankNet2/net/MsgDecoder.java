package com.mashibing.tankNet2.net;

import com.mashibing.tankNet2.tank.MsgType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/*
解码器
 */
public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
         /*确保消息头正确：
         解决TCP 拆包 粘包 问题,需要知道消息头和消息长度
         消息类型4个字节+消息体长度4个字节*/
        if(byteBuf.readableBytes() < 8)return;

        byteBuf.markReaderIndex();//标记读取指针的位置

        MsgType msgType = MsgType.values()[byteBuf.readInt()];
        int length = byteBuf.readInt();

        //如果消息内容没有读完整，返回，并重置读指针
        if(byteBuf.readableBytes() < length){
            byteBuf.resetReaderIndex();
            return;
        }

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Msg msg= null;

        //反射代替switch
         msg = (Msg)Class.forName("com.mashibing.tankNet2.net."+msgType.toString() + "Msg").getConstructor().newInstance();
        System.out.println("MsgDecoder: "+msg.getClass());

//        //判断消息类型
//        switch (msgType){
//            case TankJoin:
//                  msg = new TankJoinMsg();
//                break;
//            case TankStartMoving:
//                  msg = new TankStartMovingMsg();
//                break;
//            case TankStop:
//                msg = new TankStopMsg();
//                break;
//            default:
//                break;
//        }
        msg.parse(bytes);
        list.add(msg);
    }
}
