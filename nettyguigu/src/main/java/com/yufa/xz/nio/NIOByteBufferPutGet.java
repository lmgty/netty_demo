package com.yufa.xz.nio;

import java.nio.ByteBuffer;

/**
 * @author admin
 * @data 2020/8/27
 */
public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        buffer.putShort((short) 5);
//        buffer.putInt(100);
//        buffer.putLong(10L);
//        buffer.putChar('T');

        buffer.flip();

        // 写入和读取的类型要一一对应（按类型字节长度读的）

//        System.out.println(buffer.getShort());
//        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
//        System.out.println(buffer.getChar());
    }
}
