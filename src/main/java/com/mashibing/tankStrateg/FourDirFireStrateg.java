package com.mashibing.tankStrateg;
/*
策略模式：实现发射4个炮弹
 */
public class FourDirFireStrateg implements FireStrateg{
    @Override
    public void fire(Tank t) {
        //调整子弹居中
        int bx = t.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
        int by = t.y + Tank.HEIGHT/2 - Bullet.HEIGHT/2;

        Dir[] dirs = Dir.values();
        for (Dir dir: dirs){
            new Bullet(bx,by,dir,t.group,t.tf);
        }
    }
}
