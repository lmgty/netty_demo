package com.yufa.xz.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author admin
 * @data 2020/9/3
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
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
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("服务器接收到的信息如下：");
        System.out.println("长度 = " + len);
        System.out.println("内容 = " + new String(content, CharsetUtil.UTF_8));
        System.out.println("服务器端接收到的消息量 = " + (++this.count));


//         服务器回送数据给客户端，回送一个随机id
        String responseString = UUID.randomUUID().toString() + "\n";
        byte[] responseContent = responseString.getBytes(CharsetUtil.UTF_8);
        int responseLength = responseContent.length;

        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(responseLength);
        messageProtocol.setContent(responseContent);
        ctx.writeAndFlush(messageProtocol);
    }

}
