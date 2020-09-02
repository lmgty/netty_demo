package com.yufa.xz.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @data 2020/9/1
 */
public class MyServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // 在bossGroup增加一个日志处理器
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 加入一个 netty 提供的 IdleStateHandler
                            // 1.IdleStateHandler是 netty 提供的处理空闲状态的处理器
                            // 2.readerIdleTime  表示有多长时间没有读，会发送一个心跳检测包检测是否连接
                            // 3.writerIdleTime  表示有多长时间没有写，会发送一个心跳检测包检测是否连接
                            // 4.allIdleTime     表示有多长时间没有读和写，会发送一个心跳检测包检测是否连接
                            // 5.文档说明 Triggers an {@link IdleStateEvent} when a {@link Channel} has not performed
                            // read, write, or both operation for a while.
                            // 6.当 IdleStateEvent 触发后，就会传递给 pipeline 的下一个 handler 处理，
                            // 通过调用（出发）下一个 handler 的 userEventTriggered 方法，
                            // 在该方法中去处理 IdleStateEvent（读空闲、写空闲或者读写空闲）
                            pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
                            // 加入一个对空闲检测进一步处理的handler（自定义）
                            pipeline.addLast(new MyServerHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
