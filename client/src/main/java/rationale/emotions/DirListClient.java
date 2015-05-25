package rationale.emotions;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.ClientCookieEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 */
public class DirListClient {
    public static void main(String[] args) throws InterruptedException, URISyntaxException {
        URI uri = new URI("http://127.0.0.1:8080/");
        EventLoopGroup group = new NioEventLoopGroup();
        ChannelInitializer initializer = new DirListClientInitialiser();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                .channel(NioSocketChannel.class)
                .handler(initializer);

            // Make the connection attempt.
            Channel ch = b.connect(InetAddress.getLoopbackAddress(),8080).sync().channel();

            // Prepare the HTTP request.
            HttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.GET, uri.getRawPath());
            // Send the HTTP request.
            ChannelFuture channelFuture =ch.writeAndFlush(request);
            ch.write(request);
            ch.write(request);
            ch.write(request);
            ch.flush();

            // Wait for the server to close the connection.
            ch.closeFuture().sync();
        } finally {
            // Shut down executor threads to exit.
            group.shutdownGracefully();
        }
    }
}
