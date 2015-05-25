package rationale.emotions;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 */
public class DirListServer {
    public static void main(String[] args) throws InterruptedException, UnknownHostException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .handler(new LoggingHandler(LogLevel.INFO)).childHandler(new DirListServerInitialiser());
        try {
            System.out.println("Server running on : http://" + InetAddress.getLoopbackAddress().getHostAddress() +
                ":8080/");
            Channel channel = bootstrap.bind(InetAddress.getLoopbackAddress(),8080).sync().channel();
            channel.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
