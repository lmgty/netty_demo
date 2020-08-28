package com.yufa.xz.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author admin
 * @data 2020/8/27
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        // 连接服务器
        if(!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作");
            }
        }
        // 连接成功就发送数据
        String s = "I love WoW!";
        // 按byte[]大小装入Buffer
        ByteBuffer buffer = ByteBuffer.wrap(s.getBytes());
        socketChannel.write(buffer);
        System.in.read();
//        System.out.println("buffer " + new String(buffer.array()));

    }
}
