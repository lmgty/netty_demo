package com.yufa.xz.netty.tcpsticky;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @author admin
 * @data 2020/9/3
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        String s = new String(bytes, CharsetUtil.UTF_8);
        System.out.println("服务器端接收到的数据：" + s);
        System.out.println("服务器端接收到的消息量 = " + (++this.count));

        // 服务器回送数据给客户端，回送一个随机id
        ByteBuf responseBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString() + "\n", CharsetUtil.UTF_8);
        ctx.writeAndFlush(responseBuf);
    }
}
