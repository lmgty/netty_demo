package com.yufa.xz.netty.inboundandoutbount;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author admin
 * @data 2020/9/3
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 加入一个编码器 MyLongToByteEncoder，对数据进行编码
        pipeline.addLast(new MyLongToByteEncoder());

        // 假如一个解码器 MyByteToLongDecoder
        pipeline.addLast(new MyByteToLongDecoder());

        // 加入一个自定义的handler，处理业务逻辑
        pipeline.addLast(new MyClientHandler());



    }
}
