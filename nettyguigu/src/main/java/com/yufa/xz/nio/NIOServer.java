package com.yufa.xz.nio;

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
public class NIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        Selector selector = Selector.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        serverSocketChannel.configureBlocking(false);

        // 把 serverSocketChannel 注册到 selector 关心事件为 OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 循环等待客户端连接
        while (true){
            if (selector.select(1000) == 0){
//                System.out.println("服务器等待了一秒，无连接");
                continue;
            }

            // 如果有事件发生(>0),就获取到相关的 selectionKeys 集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()){
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()){  // 如果是 OP_ACCEPT,有新的客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 设置socketChannel为非阻塞
                    socketChannel.configureBlocking(false);
                    // 将socketChannel 注册到selector,
                    // 关注事件为 OP_READ,同时给socketChannel关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (key.isReadable()){  // 发生OP_READ
                    // 通过key反向获取到channel
                    SocketChannel channel = (SocketChannel)key.channel();
                    // 获取该channel关联的buffer（上边注册时候那个）
                    ByteBuffer buffer = (ByteBuffer)key.attachment();
                    channel.read(buffer);
                    System.out.println("from client " + new String(buffer.array()));
                }
                // 手动从集合中移除的相关的selectionKey，防止重复操作
                keyIterator.remove();
            }
        }
    }
}
