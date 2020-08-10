package com.mashibing.tankStrateg;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceMgr {
//    public  static BufferedImage tankL,tankU,tankR,tankD;
    public  static BufferedImage goodTankL,goodTankU,goodTankR,goodTankD;
    public  static BufferedImage badTankL,badTankU,badTankR,badTankD;
    public  static BufferedImage bulletL,bulletU,bulletR,bulletD;
    public static BufferedImage[]explodes = new BufferedImage[16];//爆炸图片
    static {
        try {
            //好坦克
            goodTankL = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankL.gif"));
            goodTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankU.gif"));
            goodTankR = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankR.gif"));
            goodTankD = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankD.gif"));

            //坏坦克
            badTankL = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/badTankL.gif"));
            badTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/badTankU.gif"));
            badTankR = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/badTankR.gif"));
            badTankD = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/badTnkD.gif"));

            bulletL = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletL.gif"));
            bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.gif"));
            bulletR = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletR.gif"));
            bulletD = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));

//            //实现一张图片切换4个方向
//            tankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/badTank.gif"));
//            tankL = com.mashibing.tank.ImageUtil.rotateImage(tankU,-90);
//            tankR =  com.mashibing.tank.ImageUtil.rotateImage(tankU,90);
//            tankD =  com.mashibing.tank.ImageUtil.rotateImage(tankU,180);
//
//            bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.gif"));
//            bulletL =  com.mashibing.tank.ImageUtil.rotateImage(tankU,-90);
//            bulletR =  com.mashibing.tank.ImageUtil.rotateImage(tankU,90);
//            bulletD =  com.mashibing.tank.ImageUtil.rotateImage(tankU,180);

            for (int i = 0; i < 16; i++) {
                explodes[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e"+(i+1)+".gif"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(bulletL.getWidth());//子弹宽度 13
        System.out.println(bulletL.getHeight());//子弹高度 10
    }
}
