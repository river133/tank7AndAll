package com.mashibing.netty.s01;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;

public class Client {
    public static void main(String[] args) {

        new Client().clientStart();
    }
    private void clientStart(){
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
                        System.out.println(" connetcted!");
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
}

//通道初始化器
class ClientchannelInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        System.out.println("客户端 channel 初始化成功");
        channel.pipeline().addLast(new ClientHandler());
    }
}

//自定义处理器
class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buffer = Unpooled.copiedBuffer("你好！", Charset.forName("UTF-8"));
        ChannelFuture f = ctx.writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf=null;
            buf = (ByteBuf)msg;
            //读取方式一：
//            System.out.println(buf.toString(CharsetUtil.UTF_8));

            //读取方式二：
            byte[]bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(),bytes);//读到字节数组
            System.out.println("服务器端说: "+new String(bytes));

    }
}
