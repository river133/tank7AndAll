package com.mashibing.tankObserver;

import com.mashibing.tankObserver.Observer.TankFireObserver;

//具体观察者
public class TankFireHandler implements TankFireObserver {
    @Override
    public void actionOnFire(TankFireEvent e) {
        Tank tank = e.getSource();
        tank.fire();
    }
}
