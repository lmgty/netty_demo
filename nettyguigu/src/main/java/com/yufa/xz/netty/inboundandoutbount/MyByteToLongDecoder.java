package com.yufa.xz.netty.inboundandoutbount;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author admin
 * @data 2020/9/3
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    /**
     * decode 方法会根据接收的数据长度，调用0次或多次，
     * 直到确定没有新的元素被添加到list，或者是ByteBuf 没有更多的可读字节为止
     * 如果 list 不为空，就会将它的内容传递给下一个channelInboundHandler处理
     * 下一个channelInboundHandler的方法也会被调用多次。
     * 例如：传过来一个 16 个字节的数据，那么 decode 两次， 然后 channelRead0 两次。
     * @param ctx 上下文
     * @param in ByteBuf
     * @param out List 集合，将解码后的数据传给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {


        System.out.println("MyByteToLongDecoder 被调用");
        // 需要判断够一个Long （8个字节） 才能读取
        if (in.readableBytes() >= 8){
            out.add(in.readLong());
        }
    }
}
