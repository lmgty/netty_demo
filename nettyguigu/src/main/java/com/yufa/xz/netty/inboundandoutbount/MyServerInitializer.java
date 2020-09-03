package com.yufa.xz.netty.inboundandoutbount;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author admin
 * @data 2020/9/3
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 加入入站的 handler 进行解码 MyByteToLongDecoder
        pipeline.addLast(new MyByteToLongDecoder());

        //加入一个编码器
        pipeline.addLast(new MyLongToByteEncoder());

        // 加入handler 处理业务逻辑
        pipeline.addLast(new MyServerHandler());

    }
}
