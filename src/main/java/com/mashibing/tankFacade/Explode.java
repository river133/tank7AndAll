package com.mashibing.tankFacade;

import java.awt.*;

public class Explode extends GameObject{
    public static int WIDTH = ResourceMgr.explodes[0].getWidth();//爆炸宽度高度
    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();//爆炸宽度高度

    private int x,y;
    private int step=0;

    public Explode(int x, int y ) {
        this.x = x;
        this.y = y;
//        new Audio("audio/explode.wav").run();
        GameModel.getInstance().add(this);
    }
    @Override
    public void paint(Graphics g){
        g.drawImage(ResourceMgr.explodes[step++],x,y,null);
        if(step >= ResourceMgr.explodes.length){
            GameModel.getInstance().remove(this);
        }
    }
}
