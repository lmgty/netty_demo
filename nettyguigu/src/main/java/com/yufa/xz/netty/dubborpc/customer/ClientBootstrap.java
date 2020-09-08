package com.yufa.xz.netty.dubborpc.customer;

import com.yufa.xz.netty.dubborpc.netty.NettyClient;
import com.yufa.xz.netty.dubborpc.publicinterface.HelloService;

/**
 * @author LiuYe
 * @data 2020/9/8
 */
public class ClientBootstrap {
    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) throws InterruptedException {
        NettyClient customer = new NettyClient();
        HelloService helloService = (HelloService) customer.getBean(HelloService.class, providerName);
        for (; ; ) {
            Thread.sleep(5000);
            String s = helloService.hello("你好 rpc");
            System.out.println("调用的结果：" + s);
        }

    }
}
