package com.mashibing.tankFactory.abstractfactory;

import com.mashibing.tankFactory.Dir;
import com.mashibing.tankFactory.Group;
import com.mashibing.tankFactory.TankFrame;
/*
抽象工厂
 */
public abstract class GameFactory {
    public abstract BaseTank createTank(int x, int y, Dir dir, Group group);
    public abstract BaseExplode createExplode(int x, int y, TankFrame tf);
    public abstract BaseBullet createBullet(int x,int y,Dir dir,Group group,TankFrame tf);
}
