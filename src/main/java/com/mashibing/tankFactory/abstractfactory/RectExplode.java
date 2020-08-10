package com.mashibing.tankFactory.abstractfactory;

import com.mashibing.tankFactory.ResourceMgr;
import com.mashibing.tankFactory.TankFrame;

import java.awt.*;
/*
方形爆炸
 */
public class RectExplode extends BaseExplode{
    public static int WIDTH = ResourceMgr.explodes[0].getWidth();//爆炸宽度高度
    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();//爆炸宽度高度

    private int x,y;
    TankFrame tf=null;
    private int step=0;

    public RectExplode(int x, int y, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.tf = tf;
//        new Audio("audio/explode.wav").run();
    }
    @Override
    public void paint(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillRect(x,y,10*step,10*step);
        step++;
        if(step >= 10){
            tf.explodes.remove(this);
        }
        g.setColor(c);
    }
}
