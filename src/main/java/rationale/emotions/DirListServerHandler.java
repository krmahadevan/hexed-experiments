package rationale.emotions;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.nio.charset.Charset;
import java.util.Map;

/**
 *
 */
public class DirListServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        StringBuilder data = new StringBuilder();
        data.append("Hello world.").append("\nInstance type : ").append(msg.getClass().getCanonicalName()).append("\n");
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            HttpHeaders allHeaders = request.headers();
            data.append("Printing all Headers").append("\n");
            for (Map.Entry<String, String> eachHeader : allHeaders.entries()) {
                data.append("[").append(eachHeader.getKey()).append(":").append(eachHeader.getValue()).append("]\n");
            }
        }
        ByteBuf byteBuf = Unpooled.copiedBuffer(data.toString(), Charset.defaultCharset());
        HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

}
