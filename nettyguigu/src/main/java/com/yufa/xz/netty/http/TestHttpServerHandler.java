package com.yufa.xz.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 说明
 * 1.SimpleChannelInboundHandler<I> extends ChannelInboundHandlerAdapter
 * 2.HttpObject 客户端和服务器端相互通讯的数据被封装成HttpObject
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    // 读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        // 判断 msg 是否为 HttpRequest 请求
        if (msg instanceof HttpRequest){
            System.out.println("msg 类型=" + msg.getClass());
            System.out.println("客户端地址" + ctx.channel().remoteAddress());

            // 回复信心给浏览器[http协议]
            ByteBuf content = Unpooled.copiedBuffer("hello, 我是服务器", CharsetUtil.UTF_8);

            // 构造一个http的响应，即 HttpResponse
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            // 将构建好的 response 返回
            ctx.writeAndFlush(response);
        }
    }
}
