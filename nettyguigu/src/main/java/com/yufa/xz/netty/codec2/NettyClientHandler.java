package com.yufa.xz.netty.codec2;

import com.yufa.xz.netty.codec.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

/**
 * @author admin
 * @data 2020/8/31
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    // 当通道就绪就回触发该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int i = new Random().nextInt(3);
        MyDataInfo.MyMessage message = null;

        if (i == 0) {  // 发送Student对象
            message = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.StudentType)
                    .setStudent(MyDataInfo.Student.newBuilder().setId(5).setName("SSS").build()).build();
        } else {  // 发送 Worker 对象
            message = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.WorkerType)
                    .setWorker(MyDataInfo.Worker.newBuilder().setId(6).setName("WWW").build()).build();
        }
        ctx.writeAndFlush(message);
    }


    // 当通道有读取事件时会触发该方法
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务器回复的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器的地址：" + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
