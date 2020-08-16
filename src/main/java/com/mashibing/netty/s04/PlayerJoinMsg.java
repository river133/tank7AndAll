package com.mashibing.netty.s04;

import com.mashibing.tankNet.tank.Dir;
import com.mashibing.tankNet.tank.Group;
import com.mashibing.tankNet.tank.Player;

import java.io.*;
import java.util.UUID;

//自定义坦克协议
public class PlayerJoinMsg extends Msg {
    private static final MsgType TYPE = MsgType.PlayerJoin;

    int x,y;
    Dir dir;
    Boolean moving;
    Group group;
    public UUID id;
    String name;

    public PlayerJoinMsg() {}
    public PlayerJoinMsg(Player player) {
        super();
        this.x=player.x;
        this.y=player.y;
        this.moving=player.isMoving();
        this.dir=player.getDir();
        this.group=player.getGroup();
        this.id=player.getId();
    }

    @Override
    public byte[] toBytes(){
        ByteArrayOutputStream baos=null;
        DataOutputStream dos=null;
        byte[]bytes=null;


        try {
            baos=new ByteArrayOutputStream();
            dos=new DataOutputStream(baos);
            dos.writeInt(TYPE.ordinal());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeBoolean(moving);
            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeUTF(name);
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

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis =new DataInputStream(new ByteArrayInputStream(bytes));
        try {
//            先读TYPE信息，根据TYPE信息处理不同消息
//            略过消息类型
            dis.readInt();

            this.x = dis.readInt();
            this.y = dis.readInt();
            this.moving=dis.readBoolean();
            this.dir=Dir.values()[dis.readInt()];
            this.group=group.values()[dis.readInt()];
            this.id=new UUID(dis.readLong(),dis.readLong());
            this.name=dis.readUTF();
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
}
