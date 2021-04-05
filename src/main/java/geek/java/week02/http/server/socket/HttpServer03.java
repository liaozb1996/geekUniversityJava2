package geek.java.week02.http.server.socket;

import geek.java.week02.http.util.RegisterUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

import static geek.java.constant.PortConstants.SERVER03_0_PORT;

/**
 * HttpServer 线程池版本
 * @Author liaozibo
 * @since 2021/04/05
 **/
public class HttpServer03 {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER03_0_PORT);
        System.out.println("HttpServer03 started at: " + SERVER03_0_PORT);
        RegisterUtils.register("server03", "localhost", SERVER03_0_PORT);
        int workerNum = Runtime.getRuntime().availableProcessors() * 2;
        ExecutorService executorService = Executors.newFixedThreadPool(workerNum);
        while (true) {
            Socket accept = serverSocket.accept();
            executorService.submit(() -> {
                try {
                    HttpService.handle(accept);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
