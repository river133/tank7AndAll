package com.mashibing.netty.s02;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;


public class Client {
    private Channel channel=null;
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

    //发送特点消息，通知服务器退出
    public void closeConnect() {
        send("_bye_");
    }

    public void send(String msg){
        ByteBuf buffer = Unpooled.copiedBuffer(msg.getBytes());
        channel.writeAndFlush(buffer);
    }

    //主程序入口
//    public static void main(String[] args) {
//        new Client().connet();
//    }
}

//通道初始化器
class ClientchannelInitializer extends ChannelInitializer {
    @Override
    protected void initChannel( Channel channel) throws Exception {
        System.out.println("客户端 channel 初始化成功");
        channel.pipeline().addLast(new ClientHandler());
    }
}

//自定义处理器
class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ByteBuf buffer = Unpooled.copiedBuffer("你好！", Charset.forName("UTF-8"));
//        ChannelFuture f = ctx.writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
             ByteBuf buf=null;
        try {
            buf = (ByteBuf)msg;
            byte[]bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(),bytes);//读到字节数组
            String msgAccepted =  new String(bytes);

            //将服务器发来的消息更新到窗口
            ClientFrame.INSTANCE.updateText(msgAccepted);
        } finally {
            if(buf!=null){
                ReferenceCountUtil.release(buf);
            }
        }
    }
}
