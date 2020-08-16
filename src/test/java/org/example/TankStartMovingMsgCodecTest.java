package org.example;

import com.mashibing.tankNet2.net.MsgDecoder;
import com.mashibing.tankNet2.net.MsgEncoder;
import com.mashibing.tankNet2.net.TankStartMovingMsg;
import com.mashibing.tankNet2.tank.Dir;
import com.mashibing.tankNet2.tank.MsgType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

/**
 * Unit test for simple App.
 * 在非网络连接状态下 io读取测试
 */
public class TankStartMovingMsgCodecTest {

    /*
    测试Msg的子类TankStartMovingMsg消息读取
     */
    @Test
    public void testEncoder(){
        EmbeddedChannel ch = new EmbeddedChannel();

        UUID id = UUID.randomUUID();

        TankStartMovingMsg msg = new TankStartMovingMsg(id,5, 9, Dir.LEFT);
        ch.pipeline()
                //编码器：对象转字节数组
                .addLast(new MsgEncoder());

        ch.writeOutbound(msg);

        ByteBuf buf =(ByteBuf) ch.readOutbound();

        MsgType msgType = MsgType.values()[buf.readInt()];
        Assert.assertEquals(MsgType.TankStartMoving,msgType);

        int length = buf.readInt();
        Assert.assertEquals(28,length);

        UUID uuid = new UUID(buf.readLong(), buf.readLong());
        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir = Dir.values()[buf.readInt()];//方向


        Assert.assertEquals(5,x);
        Assert.assertEquals(9,y);
        Assert.assertEquals(Dir.LEFT,dir);
        Assert.assertEquals(id,uuid);
        buf.release();
    }

    @Test
    public void testDecoder(){
        EmbeddedChannel ch = new EmbeddedChannel();

        UUID id = UUID.randomUUID();
        TankStartMovingMsg msg = new TankStartMovingMsg(id,5, 9, Dir.LEFT);
        ch.pipeline()
                //解码器：字节数组转对象
                .addLast( new MsgDecoder());

        ByteBuf buf = Unpooled.buffer();

        //写出字节流
        buf.writeInt(MsgType.TankStartMoving.ordinal());//消息类型
        byte[] bytes = msg.toBytes();
        buf.writeInt(bytes.length);//消息长度
        buf.writeBytes(bytes);//消息体

        ch.writeInbound(buf.duplicate());

        //读取字节解码器转为对象
        TankStartMovingMsg msgR = (TankStartMovingMsg)ch.readInbound();

        Assert.assertEquals(5,msgR.getX());
        Assert.assertEquals(9,msgR.getY());
        Assert.assertEquals(Dir.LEFT,msgR.getDir());
        Assert.assertEquals(id,msgR.getId());
    }
}
