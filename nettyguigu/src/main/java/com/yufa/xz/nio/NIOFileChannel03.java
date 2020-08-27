package com.yufa.xz.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author admin
 * @data 2020/8/26
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\admin\\IdeaProjects\\pratice\\netty_demo\\nettyguigu\\src\\main\\resources\\file01.txt");
        FileChannel source = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\admin\\IdeaProjects\\pratice\\netty_demo\\nettyguigu\\src\\main\\resources\\file02.txt");
        FileChannel target = fileOutputStream.getChannel();

        target.transferFrom(source,0, source.size());

        source.close();
        target.close();
        fileInputStream.close();
        fileOutputStream.close();

    }
}
