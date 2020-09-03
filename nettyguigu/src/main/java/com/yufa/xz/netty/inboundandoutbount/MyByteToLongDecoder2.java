package com.yufa.xz.netty.inboundandoutbount;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author admin
 * @data 2020/9/3
 */
public class MyByteToLongDecoder2 extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder2 被调用");
        // 在ReplayingDecoder 中不需要判断数据是否足够读取，内部会进行处理和判断
        out.add(in.readLong());

    }
}
