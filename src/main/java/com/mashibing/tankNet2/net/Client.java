package com.mashibing.tankNet2.net;

import com.mashibing.tankNet2.tank.TankFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


public class Client {
    public Channel channel=null;
    public static final Client INSTANCE = new Client();

    private Client() { }

    public void connet(){
        NioEventLoopGroup workers = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();

        try {
            ChannelFuture f = b.group(workers)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientchannelInitializer())
                    .connect("127.0.0.1", 8888);

            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(!channelFuture.isSuccess()){
                        System.out.println("not connetcted!");
                    }else {
                        //连接成功初始化channel
                        channel = channelFuture.channel();
                    }
                }
            });
                    f.sync();

            System.out.println("客户端准备连接...");

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            workers.shutdownGracefully();
        }
    }

    //发送特定消息，通知服务器退出
    public void closeConnect() {
//        send("_bye_");
    }

    public void send(Msg msg){
        channel.writeAndFlush(msg);
    }

    //主程序入口
//    public static void main(String[] args) {
//        new Client().connet();
//    }
}

//通道初始化器
class ClientchannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println("客户端 channel 初始化成功");
        ch.pipeline()
                .addLast(new MsgEncoder())//自定义编码器
                .addLast(new MsgDecoder())//自定义编码器
                .addLast(new ClientHandler());
    }
}

//SimpleChanne 只处理一种消息，或相同父类的消息
class ClientHandler extends SimpleChannelInboundHandler<Msg> {
        @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
            System.out.println("channelRead0: "+msg);
            msg.handle();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /* 直接写自定义协议，交给channle编码处理为ByteBuf
        * 56集
        * */
        ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
    }
}
