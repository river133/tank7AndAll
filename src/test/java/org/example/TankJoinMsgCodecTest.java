package org.example;

import com.mashibing.tankNet.net.TankJoinMsg;
import com.mashibing.tankNet.net.TankJoinMsgDecoder;
import com.mashibing.tankNet.net.TankJoinMsgEncoder;
import com.mashibing.tankNet.tank.Dir;
import com.mashibing.tankNet.tank.Group;
import com.mashibing.tankNet.tank.MsgType;
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
public class TankJoinMsgCodecTest {
    @Test
    public void testTankMsgEncoder1()  {

        EmbeddedChannel ch = new EmbeddedChannel();

        UUID id = UUID.randomUUID();

        TankJoinMsg msg = new TankJoinMsg(5, 9, Dir.DOWN,true, Group.BAD,id);
        ch.pipeline()
                //编码器：对象转字节数组
                .addLast(new TankJoinMsgEncoder());

        ch.writeOutbound(msg);

        ByteBuf buf = ch.readOutbound();

        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir = Dir.values()[buf.readInt()];
        boolean moving = buf.readBoolean();
        Group g = Group.values()[buf.readInt()];
        UUID uuid = new UUID(buf.readLong(), buf.readLong());


        Assert.assertEquals(5,x);
        Assert.assertEquals(9,y);
        Assert.assertEquals(Dir.DOWN,dir);
        Assert.assertEquals(true,moving);
        Assert.assertEquals(Group.BAD,g);
        Assert.assertEquals(id,uuid);
        buf.release();
    }

    @Test
    public void testTankMsgDecoder2(){
        EmbeddedChannel ch = new EmbeddedChannel();

        UUID id = UUID.randomUUID();
        TankJoinMsg msg = new TankJoinMsg(5, 9, Dir.DOWN,true, Group.BAD,id);
        ch.pipeline()
                //解码器：字节数组转对象
                .addLast(new TankJoinMsgDecoder());

        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(msg.toBytes());

        ch.writeInbound(buf.duplicate());

        TankJoinMsg msgR = (TankJoinMsg)ch.readInbound();

        Assert.assertEquals(5,msgR.x);
        Assert.assertEquals(9,msgR.y);
        Assert.assertEquals(Dir.DOWN,msgR.dir);
        Assert.assertEquals(true,msgR.moving);
        Assert.assertEquals(Group.BAD,msgR.group);
        Assert.assertEquals(id,msgR.id);
    }

    /*
    测试Msg的子类TankJoinMsg消息读取
     */
    @Test
    public void testEncoder(){
        EmbeddedChannel ch = new EmbeddedChannel();

        UUID id = UUID.randomUUID();

        TankJoinMsg msg = new TankJoinMsg(5, 9, Dir.DOWN,true, Group.BAD,id);
        ch.pipeline()
                //编码器：对象转字节数组
                .addLast(new TankJoinMsgEncoder());

        ch.writeOutbound(msg);

        ByteBuf buf =(ByteBuf) ch.readOutbound();

        MsgType msgType = MsgType.values()[buf.readInt()];
        Assert.assertEquals(MsgType.TankJoin,msgType);

        int length = buf.readInt();
        Assert.assertEquals(33,length);

        int x = buf.readInt();
        int y = buf.readInt();
        boolean moving = buf.readBoolean();
        Dir dir = Dir.values()[buf.readInt()];
        Group g = Group.values()[buf.readInt()];
        UUID uuid = new UUID(buf.readLong(), buf.readLong());


        Assert.assertEquals(5,x);
        Assert.assertEquals(9,y);
        Assert.assertEquals(Dir.DOWN,dir);
        Assert.assertEquals(true,moving);
        Assert.assertEquals(Group.BAD,g);
        Assert.assertEquals(id,uuid);
        buf.release();
    }

    @Test
    public void testDecoder(){
        EmbeddedChannel ch = new EmbeddedChannel();

        UUID id = UUID.randomUUID();
        TankJoinMsg msg = new TankJoinMsg(5, 9, Dir.DOWN,true, Group.BAD,id);
        ch.pipeline()
                //解码器：字节数组转对象
                .addLast(new TankJoinMsgDecoder());

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankJoin.ordinal());//消息类型
        byte[] bytes = msg.toBytes();
        buf.writeInt(bytes.length);//消息长度
        buf.writeBytes(bytes);//消息体

        ch.writeInbound(buf.duplicate());

        TankJoinMsg msgR = (TankJoinMsg)ch.readInbound();

        Assert.assertEquals(5,msgR.x);
        Assert.assertEquals(9,msgR.y);
        Assert.assertEquals(true,msgR.moving);
        Assert.assertEquals(Dir.DOWN,msgR.dir);
        Assert.assertEquals(Group.BAD,msgR.group);
        Assert.assertEquals(id,msgR.id);
    }
}
