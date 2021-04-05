package geek.java.week03.gateway;

import com.alibaba.fastjson.JSON;
import geek.java.week02.http.client.HttpClient;
import geek.java.week03.gateway.filter.Filter;
import geek.java.week03.gateway.route.Router;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;
import okhttp3.OkHttpClient;
import okhttp3.Route;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_GATEWAY;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @Author liaozibo
 * @since 2021/04/05
 **/
public class GatewayHandler extends ChannelInboundHandlerAdapter {

    private static final OkHttpClient client = new OkHttpClient();
    private Router router = new Router();
    private List<Filter> filterList = new ArrayList<>();

    public void addFilter(Filter filter) {
        filterList.add(filter);
    }

    /**
     * API:
     * /addRoute
     * /getRoute
     * /{others}
     * */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            FullHttpRequest request = (FullHttpRequest) msg;
            doFilter(ctx, request);
            String uri = request.uri();
            if (uri.contains("addRoute")) {
                addRoute(uri);
            } else if (uri.contains("getRoute")) {
                write(ctx, OK, router.toString());
            } else {
                doRoute(ctx, uri);
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }

    }

    private void doRoute(ChannelHandlerContext ctx, String uri) {
        String name = uri.substring(1, uri.length());
        String url = router.getUrl(name);
        if (url != null) {
            System.out.println(String.format("get url %s for %s", url, name));
            String body = HttpClient.get(url);
            write(ctx, OK, body);
        } else {
            write(ctx, BAD_GATEWAY, "No route for " + name);
        }
    }

    private void doFilter(ChannelHandlerContext ctx, FullHttpRequest request) {
        for (Filter filter : filterList) {
            filter.filter(ctx, request);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       cause.printStackTrace();
       ctx.close();
    }

    /**
     * @param uri /addRoute/serverName/host/port
     * */
    private void addRoute(String uri) {
        String[] split = uri.split("/");
        String serverName = split[2];
        String url = String.format("http://%s:%s", split[3], split[4]);
        router.addRoute(serverName, url);
        System.out.println(String.format("add route %s for %s", url, serverName));
    }

    private void write(ChannelHandlerContext ctx, HttpResponseStatus status, String body) {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status, Unpooled.wrappedBuffer(bytes));
        HttpUtil.setContentLength(response, bytes.length);
        ctx.write(response);
    }

}
