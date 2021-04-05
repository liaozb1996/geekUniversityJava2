package geek.java.week03.gateway;

import geek.java.constant.PortConstants;
import geek.java.week03.gateway.route.Router;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author liaozibo
 * @since 2021/04/05
 **/
public class NettyGatewayServer {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new GatewayInitializer());
            ChannelFuture future = bootstrap.bind(PortConstants.GATEWAY_0_PORT).sync();
            System.out.println("NettyGateway started at: " + PortConstants.GATEWAY_0_PORT);
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 路由配置
     * */
    private static Router getRouter() {
        Router router = new Router();
        String urlPrefix = "http://localhost:";
        router.addRoute("server01", urlPrefix + PortConstants.SERVER01_0_PORT);
        router.addRoute("server01", urlPrefix + PortConstants.SERVER01_1_PORT);
        router.addRoute("server02", urlPrefix+ PortConstants.SERVER02_0_PORT);
        router.addRoute("server03", urlPrefix + PortConstants.SERVER03_0_PORT);
        router.addRoute("netty", urlPrefix + PortConstants.NETTY_SERVER_0_PORT);
        return router;
    }
}
