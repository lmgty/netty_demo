package com.yufa.xz.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author admin
 * @data 2020/9/3
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 发送十条消息
        for (int i = 0; i < 5; i++) {
            String s = "hello,server"+ i;
            byte[] content = s.getBytes(CharsetUtil.UTF_8);
            int length = content.length;

            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(content);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("服务器接收到的信息如下：");
        System.out.println("长度 = " + len);
        System.out.println("内容 = " + new String(content, CharsetUtil.UTF_8));
        System.out.println("服务器端接收到的消息量 = " + (++this.count));
    }
}
