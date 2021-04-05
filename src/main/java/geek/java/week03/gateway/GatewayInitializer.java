package geek.java.week03.gateway;

import geek.java.week03.gateway.filter.Filter;
import geek.java.week03.gateway.filter.TestFilter;
import geek.java.week03.gateway.route.Router;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Author liaozibo
 * @since 2021/04/05
 **/
public class GatewayInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
        // gateway
        GatewayHandler gatewayHandler = new GatewayHandler();
        gatewayHandler.addFilter(new TestFilter());
        pipeline.addLast(gatewayHandler);
    }
}
