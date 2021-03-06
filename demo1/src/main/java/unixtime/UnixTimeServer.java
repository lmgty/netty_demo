package unixtime;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import timeserver.TimeServerHandler;

/**
 * @author admin
 * @data 2020/8/25
 */
public class UnixTimeServer {
    private int port;

    public UnixTimeServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0){
            port = Integer.parseInt(args[0]);
        }else {
            port = 8080;
        }
        new UnixTimeServer(port).run();

    }

    public void run() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new UnixTimeEncoder(), new UnixTimeServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);
            // bind the port
            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();


        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
