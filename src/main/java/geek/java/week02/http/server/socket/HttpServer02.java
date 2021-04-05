package geek.java.week02.http.server.socket;

import geek.java.week02.http.util.RegisterUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static geek.java.constant.PortConstants.SERVER02_0_PORT;

/**
 * HServer 多线程版本
 * @Author liaozibo
 * @since 2021/04/05
 **/
public class HttpServer02 {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER02_0_PORT);
        System.out.println("HttpServer02 started at: " + SERVER02_0_PORT);
        RegisterUtils.register("server02", "localhost", SERVER02_0_PORT);
        while (true) {
            Socket accept = serverSocket.accept();
            Thread thread = new Thread(() -> {
                try {
                    HttpService.handle(accept);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
    }
}
