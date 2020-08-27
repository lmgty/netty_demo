package com.yufa.xz.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author admin
 * @data 2020/8/26
 */
public class NIOFileChannel01 {
    public static void main(String[] args) throws IOException {
        String str = "hello, world!";
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\admin\\IdeaProjects\\pratice\\netty_demo\\nettyguigu\\src\\main\\resources\\file01.txt");

        // 通过fileOutputStream 获取对应的 FileChannel
        // 这个 fileChannel 真实类型是 FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();

        // 创建一个缓冲区 ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
