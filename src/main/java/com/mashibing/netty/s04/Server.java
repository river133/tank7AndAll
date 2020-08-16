package com.mashibing.netty.s04;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {
    /*
   通道组:创建单线程事件执行器，当客户端连接服务器端后，将通道保存到组里
    */
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    int port;

    public Server(int port) {
        this.port = port;
    }

    //启动服务器
    public void serverStart(){
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            ChannelFuture f = b.group(bossGroup, workerGroup)
                    //指定建立连接的类型：NioServerSocketChannel
                    .channel(NioServerSocketChannel.class)
                    //每一个客户端连接后给他一个监听器来处理
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //通道连接成功后，添加自定义处理器
                            ChannelPipeline p1=ch.pipeline();
                            p1.addLast(new TankMsgDecoder());//添加解码器
                            p1.addLast(new ServerChildHandler());
                        }
                    })
                    .bind(port)
                    .sync();
            System.out.println("服务器已启动...");
            ServerFrame.INSTANCE.updateServerMsg("server started!");

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
//        new Server(8888).serverStart();
    }
}

//自定义处理器
class ServerChildHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //通道初始化后保存到通道组
         Server.clients.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        //传输自定义消息
        TankMsg tm = (TankMsg)msg;
        System.out.println(tm);
        ServerFrame.INSTANCE.updateClientMsg(tm.toString());
    }

    //异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        Server.clients.remove(ctx.channel());//客户端断开后
        ctx.close();
    }
}

