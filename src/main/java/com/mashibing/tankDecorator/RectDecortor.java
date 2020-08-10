package com.mashibing.tankDecorator;

import java.awt.*;
/*
装饰者设计模式
 */
public class RectDecortor extends GODecortor {
    Rectangle rect = new Rectangle();
    public RectDecortor(GameObject go) {
        super(go);
        GameModel.getInstance().add(this);//添加子弹
    }
    @Override
    public void paint(Graphics g){
        this.x=go.x;
        this.y=go.y;

        rect.x=x;
        rect.y=y;
        rect.width=go.getWidth();
        rect.height=go.getHeight();

        go.paint(g);

        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawRect(go.x,go.y,getWidth(),getHeight());
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
