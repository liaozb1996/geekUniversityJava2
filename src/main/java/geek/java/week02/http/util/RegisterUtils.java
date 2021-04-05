package geek.java.week02.http.util;

import geek.java.week02.http.client.HttpClient;

/**
 * @Author liaozibo
 * @since 2021/04/05
 **/
public class RegisterUtils {

    private static final String registerServer = "http://localhost:8500";

    /**
     * 向网关注册服务
     * @param name 服务名称
     * @param host 服务主机名
     * @param port 服务端口
     * */
    public static void register(String name, String host, int port) {
        String param = String.format("/addRoute/%s/%s/%s", name, host, port);
        HttpClient.get(registerServer + param);
    }
}
