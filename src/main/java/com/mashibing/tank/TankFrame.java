package com.mashibing.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class TankFrame extends Frame {
    //自己坦克
    Tank myTank = new Tank(200,500,Dir.DOWN,Group.GOOD,this);
    List<Bullet> bullets = new ArrayList<>();//子弹容器
    List<Tank> tanks = new ArrayList<>();//敌方坦克容器
    List<Explode> explodes = new ArrayList<>();//爆炸容器
//    Explode e = new Explode(100,100,this);

    static final  int GAME_WIDTH=900,GAME_HEIGHT=800;//窗口宽、高

    public TankFrame()   {
        setSize(GAME_WIDTH,GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war");
        setVisible(true);

        //监听键盘
        this.addKeyListener(new MykeyListener());

        //关闭窗口事件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    //处理双缓冲问题
    Image offScreenImage=null;
    @Override
    public void update(Graphics g) {
        if(offScreenImage==null){
            offScreenImage=this.createImage(GAME_WIDTH,GAME_HEIGHT);
        }
        Graphics goffScreen = offScreenImage.getGraphics();
        Color c =goffScreen.getColor();
        goffScreen.setColor(Color.BLACK);
        goffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        goffScreen.setColor(c);
        paint(goffScreen);
        g.drawImage(offScreenImage,0,0,null);
    }

    //绘制坦克
    @Override
    public void paint(Graphics g) {
        Color c=g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹数量："+bullets.size(),10,60);
        g.drawString("敌方坦克数量："+tanks.size(),10,80);
        g.drawString("爆炸的数量："+explodes.size(),10,100);
        g.setColor(c);

        myTank.paint(g);//画坦克
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }

        //画敌方坦克
        for (int i = 0; i < tanks.size(); i++) {
            tanks.get(i).paint(g);
        }

        //画爆炸
        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }

        //子弹-坦克 碰撞检测
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < tanks.size(); j++) {
                bullets.get(i).collidewith(tanks.get(j));
            }
        }
    }

    //键盘监听处理类
    class MykeyListener extends KeyAdapter {

        boolean bL = false;
        boolean bU = false;
        boolean bR = false;
        boolean bD = false;

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;

                    break;
                default:
                    break;
            }
            setMainTankDir();//根据按键改变方向
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
                case KeyEvent.VK_CONTROL://发子弹
                   myTank.fire();
                    break;
                default:
                    break;
            }
            setMainTankDir();//根据按键抬起改变方向
        }

        //将方向传递到坦克类 ，改变方向
        private void setMainTankDir() {
            if(!bL && !bU && !bR && !bD) {
                myTank.setMoving(false);//代表坦克禁止状态
            }else {
                myTank.setMoving(true);//代表坦克移动了
                if(bL)myTank.setDir(Dir.LEFT) ;
                if(bU)myTank.setDir(Dir.UP);
                if(bR)myTank.setDir(Dir.RIGHT);
                if(bD)myTank.setDir(Dir.DOWN);
            }
        }
    }
}
