package com.mashibing.tankDecorator;

import java.awt.*;

/*
装饰者设计模式
 */
public class TalDecortor extends GODecortor {
    public TalDecortor(GameObject go) {
        super(go);
        GameModel.getInstance().add(this);//添加子弹
    }
    @Override
    public void paint(Graphics g){
        this.x=go.x;
        this.y=go.y;
        go.paint(g);

        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawLine(go.x,go.y,go.x+getWidth(),go.y+getHeight());
        g.setColor(c);
    }

    @Override
    public int getWidth() {
        return super.go.getWidth();
    }

    @Override
    public int getHeight() {
        return super.go.getHeight();
    }
}
