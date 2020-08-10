package com.mashibing.tankObserver.Observer;

import com.mashibing.tankObserver.TankFireEvent;

import java.io.Serializable;

public interface TankFireObserver extends Serializable {
    void actionOnFire(TankFireEvent e);
}
