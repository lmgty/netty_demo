package com.yufa.xz.netty.dubborpc.provider;

import com.yufa.xz.netty.dubborpc.netty.NettyServer;

/**
 * 启动一个服务提供者 NettyServer
 * @author LiuYe
 * @data 2020/9/8
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        // todo
        NettyServer.startServer("127.0.0.1", 7000);
    }
}
