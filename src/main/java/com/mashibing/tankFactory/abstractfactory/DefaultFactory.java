package com.mashibing.tankFactory.abstractfactory;

import com.mashibing.tankFactory.*;
/*
工厂模式
 */
public class DefaultFactory extends GameFactory{
    @Override
    public BaseTank createTank(int x, int y, Dir dir, Group group) {
        return null;
    }

    @Override
    public BaseExplode createExplode(int x, int y, TankFrame tf) {
        return new Explode(x,y,tf);
    }

    @Override
    public BaseBullet createBullet(int x, int y,Dir dir,Group group, TankFrame tf) {
        return new Bullet(x,y,dir,group,tf);
    }
}
