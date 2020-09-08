package com.yufa.xz.netty.dubborpc.netty;

import com.yufa.xz.netty.dubborpc.customer.ClientBootstrap;
import com.yufa.xz.netty.dubborpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author LiuYe
 * @data 2020/9/8
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取客户端消息并调用服务
        System.out.println("msg = " + msg);
        String s = msg.toString();
        // 协议 "HelloService#hello#你好"
        if (s.startsWith(ClientBootstrap.providerName)){
            String result = new HelloServiceImpl().hello(s.substring(s.lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
