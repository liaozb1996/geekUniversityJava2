package geek.java.week02.http.client;

import com.alibaba.fastjson.JSON;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

/**
 * @Author liaozibo
 * @since 2021/04/05
 **/
public class HttpClient {

    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSONMediaType = MediaType.get("application/json; charset=utf-8");

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
    }

    public static void post(String url, Map<String, Object> json) {
        RequestBody body = RequestBody.create(JSONMediaType, JSON.toJSONString(json));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()){

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        String result = get("http://localhost:8004");
        System.out.println(result);
    }
}
