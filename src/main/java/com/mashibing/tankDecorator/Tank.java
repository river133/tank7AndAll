package com.mashibing.tankDecorator;

import java.awt.*;
import java.util.Random;

public class Tank extends GameObject {
    private static final int SPEED=4;//坦克速度

    public static int WIDTH = ResourceMgr.goodTankU.getWidth();//坦克宽度高度
    public static int HEIGHT = ResourceMgr.goodTankU.getHeight();
    Rectangle rect = new Rectangle();
    private Random random=new Random();

//    public int x,y;
    public int oldX,oldY;//碰撞前，记录上一次位置


    public Dir dir =Dir.DOWN;

    private boolean moving =true;//坦克禁止不动
    private boolean living = true;//true：坦克或者
     Group group = Group.BAD;//默认为敌方坦克

    FireStrateg fs;//策略模式发射子弹

    public Tank(int x, int y, Dir dir, Group group ) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group=group;

        rect.x=this.x;
        rect.y=this.y;
        rect.width=WIDTH;
        rect.height=HEIGHT;

        if(group == Group.GOOD){
            String goodFs = PropertyMgr.getStr("goodFsDecorator");
            try { //配置文件反射诶
                fs  = (FourDirFireStrateg)Class.forName(goodFs).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            fs = new DefaultFireStrateg();
        }
        GameModel.getInstance().add(this);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Dir getDir() {
        return dir;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
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
    //画坦克,根据按键方向
    public void paint(Graphics g){
        if(!living){
            GameModel.getInstance().remove(this);//碰撞死后不画坦克
        }
        switch (dir){
            case LEFT:
                g.drawImage(this.group== Group.GOOD? ResourceMgr.goodTankL:ResourceMgr.badTankL,x,y,null);
                break;
            case UP:
                g.drawImage(this.group== Group.GOOD? ResourceMgr.goodTankU:ResourceMgr.badTankU,x,y,null);
                break;
            case RIGHT:
                g.drawImage(this.group== Group.GOOD? ResourceMgr.goodTankR:ResourceMgr.badTankR,x,y,null);
                break;
            case DOWN:
                g.drawImage(this.group== Group.GOOD? ResourceMgr.goodTankD:ResourceMgr.badTankD,x,y,null);
                break;
        }
        move();
    }

    //坦克与坦克相撞后退回到原来的位置
    public void back(){
        x=oldX;
        y=oldY;
    }
    //根据方向移动坦克
    private void move(){
        oldX=x;
        oldY=y;
//        System.out.println(oldX);
//        System.out.println(oldY);
        if(!moving) return;//坦克禁止则返回

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
        //敌人坦克自动开火
        if (this.group == Group.BAD && random.nextInt(100) > 96){
            this.fire();
        }
        //改变坦克方向
        if (this.group == Group.BAD && random.nextInt(100) > 96){
            this.randomDir();
        }
        boundsCheck();//坦克边界检测
        rect.x=x;
        rect.y=y;
    }
    //坦克边界检测
    private void boundsCheck(){
        if(this.x < 0) x=0;
        if(this.y < 30) y=30;
        if(this.x > TankFrame.GAME_WIDTH -Tank.WIDTH) x = TankFrame.GAME_WIDTH-Tank.WIDTH;
        if(this.y > TankFrame.GAME_HEIGHT-Tank.HEIGHT) y = TankFrame.GAME_HEIGHT-Tank.HEIGHT;

    }
    //随机改变方向
    private void randomDir(){
       this.dir =  Dir.values()[random.nextInt(4)];
    }

    //发射子弹
    public void fire() {
      fs.fire(this);
    }

    //坦克碰撞后
    public void die() {
        this.living=false;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }
}
