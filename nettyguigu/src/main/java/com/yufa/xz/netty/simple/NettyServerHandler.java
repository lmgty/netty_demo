package com.yufa.xz.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @data 2020/8/31
 * 1.自定义一个Handler 需要继承 netty 规定好的某个 HandlerAdapter
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * @param ctx 上下文对象，含有 pipeline，channel，地址
     * @param msg 客户端发送的数据 默认Object
     * @throws Exception 异常
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 比如这里有一个非常耗时的业务 -> 异步执行 -> 提交channel到对应的
        // NIOEventLoop 的 taskQueue 中
        // 解决方案1 用户自定义的普通任务
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("taskQueue完成啦", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    System.out.println("发生异常" + e.getMessage());
                }
            }
        });

        // 解决方案2 用户自定义定时任务  ->  该任务是提交到 scheduleTaskQueue中
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("scheduleTaskQueue完成啦", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    System.out.println("发生异常" + e.getMessage());
                }
            }
        }, 5, TimeUnit.SECONDS);

        // 解决方案3，非当前Reactor线程调用 Channel 的各种方法

        System.out.println("go on...");

//        System.out.println("服务器读取线程："+Thread.currentThread().getName());
//        System.out.println("server ctx = " + ctx);
//        // 将msg专程一个ByteBuf
//        // 这里的 ByteBuf，是 Netty 提供的， 不是NIO的 ByteBuffer
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("客户端发送消息是："+ buf.toString(CharsetUtil.UTF_8));
//        System.out.println("客户端地址："+ctx.channel().remoteAddress());
    }

    // 数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 写入缓存并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("channelReadComplete : Hello, 客户端~", CharsetUtil.UTF_8));
    }

    // 处理异常，一般关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
