package com.yufa.xz.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author admin
 * @data 2020/8/28
 */
public class GroupChatClient {

    private static final int PORT = 6667;
    private SocketChannel socketChannel;
    private String username;
    private Selector selector;

    public GroupChatClient() {
        try {
            selector = Selector.open();

            socketChannel = SocketChannel.open(new InetSocketAddress(PORT));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(username + "is ok...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupChatClient groupChatClient = new GroupChatClient();

        // 启动一个线程，每隔3秒读取一次数据
        new Thread(){
            @Override
            public void run() {
                while (true){
                    groupChatClient.readInfo();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        // 发送数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            groupChatClient.sendInfo(s);
        }
    }

    private void sendInfo(String info) {
        info = username + "说: " + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readInfo() {
        try {
            int count = selector.select();
            if (count > 0) { // 有可用的通道

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {  // 可读的
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.read(buffer);
                        System.out.println(new String(buffer.array()));
                    }
                    iterator.remove();
                }
            } else{
                System.out.println("没有可用的通道");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
