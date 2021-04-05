package geek.java.week03.gateway.route;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;


/**
 * @Author liaozibo
 * @since 2021/04/05
 **/
public class Router {

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
}
