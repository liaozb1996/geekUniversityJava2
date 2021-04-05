package geek.java.week03.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @Author liaozibo
 * @since 2021/04/05
 **/
public class TestFilter implements Filter {

    /**
     * 拦截路径带 test 的请求
     * */
    @Override
    public void filter(ChannelHandlerContext ctx, FullHttpRequest request) {
        String uri = request.uri();
        if (uri.contains("test")) {
            ctx.disconnect();
            System.out.println("拦截请求: " + uri);
        }
    }

}
