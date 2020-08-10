package com.mashibing.tankFactory.abstractfactory;

import com.mashibing.tankFactory.Dir;
import com.mashibing.tankFactory.Group;
import com.mashibing.tankFactory.TankFrame;
/*
方形爆炸工厂
 */
public class RectFactory extends GameFactory{
    @Override
    public BaseTank createTank(int x, int y, Dir dir, Group group) {
        return null;
    }

    @Override
    public BaseExplode createExplode(int x, int y, TankFrame tf) {
        return new RectExplode(x,y,tf);
    }

    @Override
    public BaseBullet createBullet(int x, int y,Dir dir,Group group, TankFrame tf) {
        return new RectBullet(x,y,dir,group,tf);
    }
}
