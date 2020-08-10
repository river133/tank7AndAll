package com.mashibing.tankDecorator;

import java.awt.*;

public abstract class GODecortor extends GameObject{
    GameObject go;

    public GODecortor(GameObject go) {
        this.go = go;
    }

    @Override
    public abstract void paint(Graphics g);
}
