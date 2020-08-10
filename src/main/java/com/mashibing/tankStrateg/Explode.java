package com.mashibing.tankStrateg;

import java.awt.*;

public class Explode {
    public static int WIDTH = ResourceMgr.explodes[0].getWidth();//爆炸宽度高度
    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();//爆炸宽度高度

    private int x,y;
    TankFrame tf=null;
    private int step=0;

    public Explode(int x, int y, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.tf = tf;
//        new Audio("audio/explode.wav").run();
    }

    public void paint(Graphics g){
        g.drawImage(ResourceMgr.explodes[step++],x,y,null);
        if(step >= ResourceMgr.explodes.length){
            tf.explodes.remove(this);
        }
    }
}
