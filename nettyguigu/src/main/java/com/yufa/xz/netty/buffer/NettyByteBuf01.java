package com.yufa.xz.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author admin
 * @data 2020/9/1
 */
public class NettyByteBuf01 {
    public static void main(String[] args) {

        // 创建一个ByteBuf
        // 1.该对象包含一个数组arr,是一个byte[10]
        // 2.不需要flip了。（readerIndex和writeIndex）
        // 0 -- readerIndex 已读取过的区域
        // readerIndex -- writeIndex 可读取的区域
        // writeIndex -- capacity 可写区域
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.getByte(i));
        }
        System.out.println(buffer.readByte());
        System.out.println(buffer.readByte());
        System.out.println(buffer.readByte());
        System.out.println(buffer.readByte());
        System.out.println(buffer.readByte());
        System.out.println(buffer.readByte());
        System.out.println(buffer.readByte());
        System.out.println(buffer.readByte());
        System.out.println(buffer.readByte());
        System.out.println(buffer.readByte());
        System.out.println(buffer.readByte()); // readIndex 最大只能是9。java.lang.IndexOutOfBoundsException
    }
}
