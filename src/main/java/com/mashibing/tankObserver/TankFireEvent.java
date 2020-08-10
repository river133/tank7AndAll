package com.mashibing.tankObserver;

public class TankFireEvent {
    Tank tank;

    public TankFireEvent(Tank tank) {
        this.tank = tank;
    }

    public Tank getSource(){
        return tank;
    }
}
