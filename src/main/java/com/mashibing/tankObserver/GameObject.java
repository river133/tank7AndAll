package com.mashibing.tankObserver;

import java.awt.*;
import java.io.Serializable;

/*
中介模式(调停者)
 */
public abstract class GameObject implements Serializable {
    protected int x, y;
    protected int width, height;

    public abstract void paint(Graphics g);

    public abstract int getWidth();

    public abstract int getHeight();
}
