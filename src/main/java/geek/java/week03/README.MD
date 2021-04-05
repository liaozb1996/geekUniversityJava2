# 第三周作业

1.（必做）整合你上次作业的 httpclient/okhttp；
[GatewayHandler.java#doRoute](gateway/GatewayHandler.java)
```java
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
```

[HttpClient.java#get](../week02/http/client/HttpClient.java)

```java
public static String get(String url) {
    Request request = new Request.Builder()
            .url(url)
            .build();
    try (Response response = client.newCall(request).execute()) {
        return response.body().string();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return null;
```

2.（选做）使用 netty 实现后端 http 访问（代替上一步骤）

3.（必做）实现过滤器。
[TestFilter.java](gateway/filter/TestFilter.java)
```java
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
```

4.（选做）实现路由。
[](gateway/route/Router.java)
```java
private static final Map<String, ArrayList<String>> table = new ConcurrentHashMap<>();
private ThreadLocalRandom random = ThreadLocalRandom.current();

/**
 * 从路有表获取URL
 * @return 服务名称
 * @return url, 名称不存在时返回 null
 * */
public String getUrl(String name) {
    ArrayList<String> urlList = table.get(name);
    if (urlList == null || urlList.isEmpty()) {
        return null;
    }
    int index = getIndex(urlList.size());
    return urlList.get(index);
}

public void addRoute(String name, String url) {
    if (!table.containsKey(name)) {
        table.put(name, new ArrayList(2));
    }
    table.get(name).add(url);
}

private int getIndex(int size) {
    return random.nextInt(size);
}

@Override
public String toString() {
    return table.toString();
}
```

启动服务时注册路由

[HttpServer01.java#run](../week02/http/server/socket/HttpServer01.java)
```java
RegisterUtils.register("server01", "localhost", port);
```

[RegisterUtils.java](../week02/http/util/RegisterUtils.java)
```java
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
```