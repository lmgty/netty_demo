package com.yufa.xz.netty.http;


import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author admin
 * @data 2020/8/31
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 向管道加入处理器

        // 得到管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 加入一个 netty 提供的 httpServerCodec
        // HttpServerCodec 说明
        // 1. HttpServerCodec 是 netty 提供的处理 http 的编码解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());

        // 加入一个自定义的 HttpServerHandler
        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());

    }
}
