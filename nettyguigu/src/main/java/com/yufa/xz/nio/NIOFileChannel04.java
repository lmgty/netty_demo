package com.yufa.xz.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author admin
 * @data 2020/8/26
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\admin\\IdeaProjects\\pratice\\netty_demo\\nettyguigu\\src\\main\\resources\\file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\admin\\IdeaProjects\\pratice\\netty_demo\\nettyguigu\\src\\main\\resources\\file02.txt");
        FileChannel inChannel = fileInputStream.getChannel();
        FileChannel outChannel = fileOutputStream.getChannel();


        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        while (true){
            byteBuffer.clear();
            int read = inChannel.read(byteBuffer);
            if (read == -1){
                break;
            }

            byteBuffer.flip();
            outChannel.write(byteBuffer);
        }



        fileInputStream.close();
        fileOutputStream.close();

    }
}
