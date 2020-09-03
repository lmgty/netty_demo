package com.yufa.xz.netty.inboundandoutbount;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author admin
 * @data 2020/9/3
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("服务器回送了：" + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler 发送数据");
        ctx.writeAndFlush(123456L);


        // 发送一个16个字节的数据过去
        // 并没有用到 MessageToByteEncoder 编码器
        // 因为在 MessageToByteEncoder 的 write 方法中，会有一个 if (acceptOutboundMessage(msg))
        // 因此我们编写 encoder 时要注意传入的数据类型和处理的数据类型要一致
//        ctx.writeAndFlush(Unpooled.copiedBuffer("aaaabbbbccccdddd", CharsetUtil.UTF_8));

    }

}
