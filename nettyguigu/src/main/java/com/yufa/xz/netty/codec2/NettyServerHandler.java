package com.yufa.xz.netty.codec2;

import com.yufa.xz.netty.codec.StudentPOJO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author admin
 * @data 2020/8/31
 * 1.自定义一个Handler 需要继承 netty 规定好的某个 HandlerAdapter
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        // 根据 dataType 显示不同信息
        MyDataInfo.MyMessage.DataType dataType = msg.getDataType();
        if (dataType == MyDataInfo.MyMessage.DataType.StudentType){
            MyDataInfo.Student student = msg.getStudent();
            System.out.println("student id = " + student.getId());
            System.out.println("student name = " + student.getName());
        }else if (dataType == MyDataInfo.MyMessage.DataType.WorkerType){
            MyDataInfo.Worker worker = msg.getWorker();
            System.out.println("worker id = " + worker.getId());
            System.out.println("worker name = " + worker.getName());
        }else {
            System.out.println("传输的类型不正确");
        }
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
