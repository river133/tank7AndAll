package com.mashibing.tank;

import java.awt.*;

public class Bullet {
    private static final int SPEED = 14;
    public static int WIDTH = ResourceMgr.bulletD.getWidth();//子弹宽度高度
    public static int HEIGHT = ResourceMgr.bulletD.getHeight();

    //边界检测
    Rectangle rect = new Rectangle();

    private int x,y;
    private Dir dir;

    private boolean living=true;//子弹存活状态，true：存活
    TankFrame tf =null;
    private Group group = Group.BAD;

    public Bullet( int x, int y, Dir dir,Group group,TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group=group;
        this.tf = tf;

        rect.x=this.x;
        rect.y=this.y;
        rect.width=WIDTH;
        rect.height=HEIGHT;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void paint(Graphics g){
        if(!living){//子弹出边界，删除自己
            this.tf.bullets.remove(this);
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
        //更新rect矩形边界
        rect.x=this.x;
        rect.y=this.y;

        //子弹出边界则消失，容器size--
        if(x<0 || y<0 || x>TankFrame.GAME_WIDTH || y>TankFrame.GAME_HEIGHT){
            living=false;
        }
    }

    //坦克-子弹 碰撞检测
    public void collidewith(Tank tank) {
        //排除自己的炮弹
        if(this.group == tank.getGroup())return;

        if(rect.intersects(tank.rect)){
            tank.die();//坦克死掉
            this.die();//子弹死掉
            //调整爆炸中心位置
            int ex = tank.getX() + Tank.WIDTH/2 - Explode.WIDTH/2;
            int ey = tank.getY() + Tank.HEIGHT/2 - Explode.HEIGHT/2;
            tf.explodes.add(new Explode(ex,ey,tf));
        }
    }
    //子弹碰撞后消失
    private void die() {
        this.living=false;
    }
}
