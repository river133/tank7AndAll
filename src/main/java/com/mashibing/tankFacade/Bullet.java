package com.mashibing.tankFacade;

import java.awt.*;

public class Bullet extends GameObject{
    private static final int SPEED = 14;
    public static int WIDTH = ResourceMgr.bulletD.getWidth();//子弹宽度高度
    public static int HEIGHT = ResourceMgr.bulletD.getHeight();
    Rectangle rect = new Rectangle();

    private int x,y;
    private Dir dir;

    private boolean living=true;//子弹存活状态，true：存活
    public Group group = Group.BAD;

    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group=group;

        rect.x=x;
        rect.y=y;
        rect.width=WIDTH;
        rect.height=HEIGHT;

        GameModel.getInstance().add(this);//添加子弹
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
    @Override
    public void paint(Graphics g){
        if(!living){//子弹出边界，删除自己
            GameModel.getInstance().remove(this);
        }

        switch (dir){
            case LEFT:
                g.drawImage(ResourceMgr.bulletL,x,y,null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletU,x,y,null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR,x,y,null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD,x,y,null);
                break;
        }
        move();
    }
    //根据方向移动子弹
    private void move(){
        switch (dir){
            case LEFT:
                x-=SPEED;
                break;
            case UP:
                y-=SPEED;
                break;
            case RIGHT:
                x+=SPEED;
                break;
            case DOWN:
                y+=SPEED;
                break;
        }
        rect.x=x;
        rect.y=y;
        //子弹出边界则消失，容器size--
        if(x<0 || y<0 || x> TankFrame.GAME_WIDTH || y> TankFrame.GAME_HEIGHT){
            living=false;
        }
    }
    //子弹碰撞后消失
    public void die() {
        this.living=false;
    }
}
