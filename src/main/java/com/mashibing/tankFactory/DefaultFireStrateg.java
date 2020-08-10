package com.mashibing.tankFactory;

/*
策略模式发射1个炮弹
 */
public class DefaultFireStrateg implements FireStrateg {
    @Override
    public void fire(Tank t) {
        //调整子弹居中
        int bx = t.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
        int by = t.y + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
//        Factory
        new Bullet(bx,by,t.dir,t.group,t.tf);
    }
}
