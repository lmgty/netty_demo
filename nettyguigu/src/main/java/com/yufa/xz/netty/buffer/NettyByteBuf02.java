package com.yufa.xz.netty.buffer;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @author admin
 * @data 2020/9/1
 */
public class NettyByteBuf02 {
    public static void main(String[] args) {
        ByteBuf buf = Unpooled.copiedBuffer("hello,world!", CharsetUtil.UTF_8);

        if (buf.hasArray()){
            byte[] content = buf.array();
            System.out.println(new String(content,CharsetUtil.UTF_8));

            System.out.println(buf.arrayOffset());
            System.out.println(buf.readerIndex());
            System.out.println(buf.writerIndex());

            System.out.println(buf.capacity());
            System.out.println(buf.readableBytes());

//            for (int i = 0; i < buf.readableBytes(); i++) {
//                System.out.println((char)buf.getByte(i));
//            }

            System.out.println(buf.getCharSequence(0,4,CharsetUtil.UTF_8));  // 起始位置  偏移量  编码集

        }
    }
}
