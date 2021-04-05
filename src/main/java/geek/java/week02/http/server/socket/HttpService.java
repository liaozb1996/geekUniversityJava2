package geek.java.week02.http.server.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @Author liaozibo
 * @since 2021/04/04
 **/
public class HttpService {

    public static void handle(Socket socket) throws IOException {
        try (PrintWriter writer = new PrintWriter(socket.getOutputStream())) {
            String body = "Hello";
            writer.println("HTTP/1.1 200 OK");
            writer.println("content-type: text/html; charset=utf-8");
            writer.println("content-length: " + body.getBytes(StandardCharsets.UTF_8).length);
            writer.println();
            writer.println(body);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
}
