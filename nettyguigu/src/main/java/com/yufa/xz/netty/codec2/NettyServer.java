package com.yufa.xz.netty.codec2;

import com.yufa.xz.netty.codec.StudentPOJO;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * @author admin
 * @data 2020/8/31
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        // 1.创建两个线程组 bossGroup 和 workerGroup
        // 2.bossGroup 只处理连接请求，workerGroup负责与客户端进行业务处理
        // 3. 两个都是无限循环
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);

        try {
            // 创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)  // 设置两个线程组
                    .channel(NioServerSocketChannel.class)  // 使用NioServerSocketChannel 作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)  // 设置线程队列等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)  // 设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {  // 创建一个通道初始化对象
                        // 向 workerGroup关联的pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();

                            // 加入ProtobufDecoder 解码器并指定对象
                            pipeline.addLast("decoder", new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));

                            pipeline.addLast(new NettyServerHandler());
                        }
                    });  // 给 workerGroup 的 EventLoop 对应的管道设置 handler

            // 绑定一个端口并且同步，生成了一个 ChannelFuture 对象
            // 启动服务器
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();

            // 给ChannelFuture 注册监听器， 监控我们关心的事件
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (channelFuture.isSuccess()){
                        System.out.println("监听端口 6668 成功");
                    }else {
                        System.out.println("监听端口 6668 失败");
                    }
                }
            });

            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
