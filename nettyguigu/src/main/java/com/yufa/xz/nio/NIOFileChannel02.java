package com.yufa.xz.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author admin
 * @data 2020/8/26
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws IOException {
        String str = "hello, world!";
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\admin\\IdeaProjects\\pratice\\netty_demo\\nettyguigu\\src\\main\\resources\\file01.txt");

        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        fileChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));

        fileInputStream.close();
    }
}
