package com.yufa.xz.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author admin
 * @data 2020/8/28
 */
public class NewIOClient {
    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(7001));
            String filename = "file01.txt";
            FileChannel fileChannel = new FileInputStream(filename).getChannel();

            long startTime = System.currentTimeMillis();

            // linux下 transferTo 一次就可以完成传输
            // windows下 transferTo 一次只能传送8M，需要分段传输，注意起始position
            // transferTo 底层使用零拷贝
            long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
            System.out.println("发送的总字节数 = " + transferCount + " 耗时：" + (System.currentTimeMillis() - startTime));
            fileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
