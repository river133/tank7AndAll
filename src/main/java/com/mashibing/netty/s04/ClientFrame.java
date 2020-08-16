package com.mashibing.netty.s04;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientFrame extends Frame {

    public static final ClientFrame INSTANCE = new ClientFrame();

    TextArea ta =new TextArea();//多行文本框
    TextField tf = new TextField();//单行文本框

    Client c = null;//客户端程序

    public ClientFrame()  {
        this.setSize(600,400);
        this.setLocation(100,100);
        this.setTitle("聊天室");
        this.add(ta,BorderLayout.CENTER);
        this.add(tf,BorderLayout.SOUTH);

        //关闭窗口事件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                c.closeConnect();
                System.exit(0);
            }
        });
        //文本框事件处理
        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("tf.getText():   "+tf.getText());
                c.send(tf.getText()); //发送到服务器
                tf.setText("");
            }
        });
    }

    private void connectToServer(){
        c = new Client();
        c.connet();
    }

    public static void main(String[] args) {
//        new ClientFrame();
        //客户端每次连接开启新窗口
        ClientFrame frame = ClientFrame.INSTANCE;
        frame.setVisible(true);
        frame.connectToServer();
    }

    //服务器端发来的消息暂时到窗口
    public void updateText(String msgAccepted) {
        this.ta.setText(ta.getText()+System.getProperty("line.separator")+msgAccepted);
    }
}
