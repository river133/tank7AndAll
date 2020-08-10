package com.mashibing.tankFacade;

public class Main {
    public static void main(String[] args) throws Exception{
        TankFrame tf = new TankFrame();

        //背景音乐
//        new Thread(()->new Audio("audio/war1.wav").loop()).strat();

        while (true) {
            Thread.sleep(40);
            tf.repaint();
        }
    }
}
