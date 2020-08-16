package com.mashibing.tankNet.net;

import com.mashibing.tankNet.tank.*;

import java.io.*;
import java.util.UUID;

//自定义坦克协议
public class TankJoinMsg extends Msg{

    public int x,y;
    public Dir dir;
    public Boolean moving;
    public Group group;
    public UUID id;

    public TankJoinMsg(Tank t) {
        this.x= t.getX();
        this.y=t.getY();
        this.dir=t.getDir();
        this.group=t.getGroup();
        this.id=t.getId();
        this.moving=t.isMoving();
    }

    public TankJoinMsg(int x, int y, Dir dir, Boolean moving, Group group, UUID id) {
        super();
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.moving = moving;
        this.group = group;
        this.id = id;
    }
    public TankJoinMsg(){};


    @Override
    public MsgType getMsgType() {
        return MsgType.TankJoin;
    }

    @Override
    public void handle() {
//                1、判断是不是自己？
//                2、列表里是不是已经有了
//                3、发自己的一个TankJoinMsg

            if(this.id.equals(TankFrame.INSTANCE.getMainTank().getId()) ||
        TankFrame.INSTANCE.findByUUID(this.id) != null)return;
//
        System.out.println("TankJoinMsg handle: "+this);
        Tank t = new Tank(this);
        TankFrame.INSTANCE.addTank(t);

        //发送一个新加入坦克
        Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
    }

    //对象转字节数组 (编码)
    public byte[] toBytes(){
        ByteArrayOutputStream baos=null;
        DataOutputStream dos=null;
        byte[]bytes=null;

        try {
            baos=new ByteArrayOutputStream();
            dos=new DataOutputStream(baos);
//            dos.writeInt(TYPE.ordinal());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeBoolean(moving);
            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
//            dos.writeUTF(name);
            dos.flush();
            bytes=baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(baos!=null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(dos!=null) {
                    dos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    //字节数组 转 对象(解码)
    public void parse(byte[] bytes) {
        DataInputStream dis =new DataInputStream(new ByteArrayInputStream(bytes));
        try {
//            先读TYPE信息，根据TYPE信息处理不同消息
//            略过消息类型
//            dis.readInt();

            this.x = dis.readInt();
            this.y = dis.readInt();
            this.moving=dis.readBoolean();
            this.dir=Dir.values()[dis.readInt()];
            this.group=group.values()[dis.readInt()];
            this.id=new UUID(dis.readLong(),dis.readLong());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "TankJoinMsg{" +
                "id = " + id +
                "，x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", moving=" + moving +
                ", group=" + group +
                '}';
    }
}
