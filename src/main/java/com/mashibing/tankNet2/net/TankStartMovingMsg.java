package com.mashibing.tankNet2.net;

import com.mashibing.tankNet2.tank.Dir;
import com.mashibing.tankNet2.tank.MsgType;
import com.mashibing.tankNet2.tank.Tank;
import com.mashibing.tankNet2.tank.TankFrame;

import java.io.*;
import java.util.UUID;
/*
坦克移动的消息
 */
public class TankStartMovingMsg extends Msg{
    UUID id;
    int x,y;
    Dir dir;//方向

    public TankStartMovingMsg() {  }

    public TankStartMovingMsg(UUID id, int x, int y, Dir dir) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.dir = dir;
    }
    public TankStartMovingMsg(Tank tank) {
        this.id=tank.getId();
        this.x=tank.getX();
        this.y=tank.getY();
        this.dir=tank.getDir();
    }

    public Dir getDir() {
        return dir;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void handle() {
        //58集：11分讲解
        // 如果是自己发的位置，不处理，否则找到对应坦克
        if(this.id.equals(TankFrame.INSTANCE.getMainTank().getId())) return;
//
        System.out.println("TankStartMovingMsg handle: "+this);
        Tank t = TankFrame.INSTANCE.findByUUID(this.id);

        if(t !=null){
            t.setMoving(true);
            t.setX(this.x);
            t.setY(this.y);
            t.setDir(dir);
        }
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos=null;
        DataOutputStream dos=null;
        byte[]bytes=null;

        try {
            baos=new ByteArrayOutputStream();
            dos=new DataOutputStream(baos);
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
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

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis =new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.id = new UUID(dis.readLong(),dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir= Dir.values()[dis.readInt()];
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
    public MsgType getMsgType() {
        return MsgType.TankStartMoving;
    }

    public UUID getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TankStartMovingMsg{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                '}';
    }
}
