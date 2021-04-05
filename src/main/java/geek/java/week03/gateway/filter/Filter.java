package geek.java.week03.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @Author liaozibo
 * @since 2021/04/05
 **/
public interface Filter {
    void filter(ChannelHandlerContext ctx, FullHttpRequest request);
}
