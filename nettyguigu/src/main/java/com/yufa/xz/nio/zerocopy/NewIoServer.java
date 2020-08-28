package com.yufa.xz.nio.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author admin
 * @data 2020/8/28
 */
public class NewIoServer {
    public static void main(String[] args) {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7001);
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(inetSocketAddress);

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            while (true){
                SocketChannel socketChannel = serverSocketChannel.accept();
                int readCount = 0;
                while (readCount != -1){
                    readCount = socketChannel.read(byteBuffer);
                }
                byteBuffer.rewind();
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
