package com.mashibing.tankNet2.net;


import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerFrame extends Frame {
    public static final ServerFrame INSTANCE = new ServerFrame();

//    Button btnStart = new Button("start");
    TextArea taLeft = new TextArea();
    TextArea taRight = new TextArea();

     Server server = new Server(8888);

    public ServerFrame() {
        this.setSize(500,300);
        this.setLocation(300,30);
        this.setTitle("服务器监控");
//        this.add(btnStart,BorderLayout.NORTH);
        Panel p = new Panel(new GridLayout(1,2));
        p.add(taLeft);
        p.add(taRight);
        this.add(p);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        ServerFrame.INSTANCE.setVisible(true);
        ServerFrame.INSTANCE.server.serverStart();
    }

    //更新左边 服务器 窗口信息
    public void updateServerMsg(String s) {
        this.taLeft.setText(taLeft.getText()+System.getProperty("line.separator")+s);
    }
    //更新右边 客户端 窗口信息
    public void updateClientMsg(String s) {
        this.taRight.setText(taRight.getText()+System.getProperty("line.separator")+s);
    }
}
