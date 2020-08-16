package com.mashibing.tankNet2.tank;

import com.mashibing.tankNet2.net.Client;

public class Main {
    public static void main(String[] args) throws Exception{
        TankFrame tf = TankFrame.INSTANCE;
        tf.setVisible(true);

        //背景音乐
//        new Thread(()->new Audio("audio/war1.wav").loop()).strat();

        new Thread(()->{
            while (true) {
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tf.repaint();
            }
        }).start();

        Client.INSTANCE.connet();
    }
}
