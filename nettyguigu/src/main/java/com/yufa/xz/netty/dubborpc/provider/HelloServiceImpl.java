package com.yufa.xz.netty.dubborpc.provider;

import com.yufa.xz.netty.dubborpc.publicinterface.HelloService;

/**
 * @author LiuYe
 * @data 2020/9/8
 */
public class HelloServiceImpl implements HelloService {
    private int count;
    @Override
    public String hello(String msg) {
        System.out.println("服务端收到消息：" + msg);
        if (msg != null) {
            return "你好客户端，我已经收到你的消息：[" + msg + "]" + "count =  " + ++count;
        }else {
            return "你好客户端，我已经收到你的消息";
        }
    }
}
