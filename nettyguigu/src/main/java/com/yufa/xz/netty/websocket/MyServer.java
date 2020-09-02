package com.yufa.xz.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author admin
 * @data 2020/9/2
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

                            // 使用http的编码解码器
                            pipeline.addLast(new HttpServerCodec());

                            // 是以块方式写，添加ChuckedWriteHandler
                            pipeline.addLast(new ChunkedWriteHandler());

                            // 1.http在传输过程中是分段的，HttpObjectAggregator 可以将多个段聚合
                            // 2.这就是为什么当浏览器发送大量数据时，会发出多次http请求
                            pipeline.addLast(new HttpObjectAggregator(8192));

                            // 1. 对应webSocket，它的数据是以帧（frame）的形式传递
                            // 2. 可以看到WebSocketFrame 下面有6个子类
                            // 3. 浏览器请求 ws://localhost:7000/hello  表示请求的uri
                            // 4. WebSocketServerProtocolHandler 核心功能是将http协议升级成 ws，保持长连接
                            // 5. 通过状态码 101 切换到ws协议
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));

                            // 自定义handler，处理业务逻辑
                            pipeline.addLast(new MyTextWebSocketFrameHandler());

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
