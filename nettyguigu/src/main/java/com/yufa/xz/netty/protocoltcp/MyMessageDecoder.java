package com.yufa.xz.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author admin
 * @data 2020/9/4
 */
public class MyMessageDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("----------");
        System.out.println("MyMessageDecoder 的 decode 被调用");
        // 将得到的二进制字节码转成 MessageProtocol 数据包
        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);

        // 封装成 MessageProtocol 对象，加入 List 然后给下个 handler 处理
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(length);
        messageProtocol.setContent(content);
        out.add(messageProtocol);
    }
}
