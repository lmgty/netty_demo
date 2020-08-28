package com.yufa.xz.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author admin
 * @data 2020/8/27
 */
public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    /**
     * 1. 实例化一个selector
     * 2. 实例化一个ServerSocketChannel -- listenChannel将来用来创建SocketChannel,绑定地址端口，设置不阻塞
     * 3. 将listenChannel注册到 selector
     */
    public GroupChatServer() {
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            listenChannel.configureBlocking(false);
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

    /**
     * 使用selector进行监听，当注册的channel中有事件发生时，处理对应的channel
     * 1. 如果监听到accept，那么创建一个SocketChannel注册到selector中
     * 2. 如果监听到 isReadable()，就读取信息，并转发
     */
    public void listen() {
        try {
            while (true) {
                int count = selector.select();
                if (count > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        // 监听到accept
                        if (key.isAcceptable()) {
                            SocketChannel ac = listenChannel.accept();
                            ac.configureBlocking(false);
                            ac.register(selector, SelectionKey.OP_READ);
                            System.out.println(ac.getRemoteAddress() + "上线了");
                        }
                        if (key.isReadable()) {
                            readData(key);
                        }
                        // 使用这个方法可以移除selectionKeys中的元素，以便每次select的时候，都返回最新状态的selectionKeys
                        iterator.remove();
                    }
                } else {
                    System.out.println("waiting...");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果channel注册时带有buffer，取出buffer即可
     * 本例属于没有注册buffer，所以需要在对channel操作时，创建一个buffer
     * socketChannel.read(buffer) 从通道读入到buffer
     * socketChannel.write(buffer) 从buffer写入到通道
     * @param key SelectionKey
     */
    private void readData(SelectionKey key) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = socketChannel.read(buffer);
            if (count > 0) {
                String s = new String(buffer.array());
                System.out.println("from client : " + s);
                sendInfoToOtherClients(s, socketChannel);
            }
        } catch (IOException e) {
            try {
                System.out.println(socketChannel.getRemoteAddress() + "离线了");
                key.cancel();
                socketChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中...");
        System.out.println("服务器转发数据给客户端线程: " + Thread.currentThread().getName());

        for (SelectionKey key : selector.keys()) {
            SelectableChannel channel = key.channel();

            if (channel instanceof SocketChannel && channel != self){
                SocketChannel targetChannel = (SocketChannel) channel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                targetChannel.write(buffer);
            }
        }

    }


}
