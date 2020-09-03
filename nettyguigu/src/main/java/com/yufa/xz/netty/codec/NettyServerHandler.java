package com.yufa.xz.netty.codec;

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
        StudentPOJO.Student student = (StudentPOJO.Student) msg;
        System.out.println("客户端发送的数据： " + student.getId() + " 名字= " + student.getName());
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
