package com.mashibing.tankFacade;

import java.awt.*;

/*
中介模式(调停者)
 */
public abstract class GameObject {
    int x,y;
    public abstract void paint(Graphics g);
}
