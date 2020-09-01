package com.yufa.xz.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author admin
 * @data 2020/9/1
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    // 定义一个channel组，管理所有的channel
    // GlobalEventExecutor.INSTANCE 是全局事件执行器，是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 一旦连接，第一个被执行的方法
    // 将当前channel 加入 channelGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        String formatDate = simpleDateFormat.format(new Date());

        // 将该客户端加入聊天的信息推送给其他客户端
        channelGroup.writeAndFlush(formatDate + " [客户端] " + channel.remoteAddress() + " 加入聊天\n");
        channelGroup.add(channel);
    }

    // 断开连接
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        // 将该客户端加入聊天的信息推送给其他客户端
        String formatDate = simpleDateFormat.format(new Date());

        channelGroup.writeAndFlush(formatDate + " [客户端] " + channel.remoteAddress() + " 离开聊天\n");
    }

    // 当 channel 处于活动状态，提示xxx上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String formatDate = simpleDateFormat.format(new Date());

        System.out.println(formatDate + " " + ctx.channel().remoteAddress() + " 上线了~");
    }

    // 当 channel 处于不活动状态
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String formatDate = simpleDateFormat.format(new Date());

        System.out.println(formatDate + " " + ctx.channel().remoteAddress() + " 下线了！");

    }

    // 读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        String formatDate = simpleDateFormat.format(new Date());

        Channel channel = ctx.channel();
        System.out.println(formatDate + " [客户端] " + channel.remoteAddress() + " 发送了消息 " + s + "\n");

        channelGroup.forEach(ch -> {
            if (ch != channel){
                ch.writeAndFlush(formatDate + " [客户] " + channel.remoteAddress() + " 发送了消息 " + s + "\n");
            }else {
                ch.writeAndFlush(formatDate + " [自己] " + channel.remoteAddress() + " 发送了消息 " + s + "\n");
            }
        });
    }

    // 异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
