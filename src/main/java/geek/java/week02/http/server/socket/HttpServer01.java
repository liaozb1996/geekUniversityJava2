package geek.java.week02.http.server.socket;


import geek.java.week02.http.util.RegisterUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * HttpServer 单线程版本
 * @Author liaozibo
 * @since 2021/04/04
 **/
public class HttpServer01 {

    private int port;

    public HttpServer01(int port) {
        this.port = port;
    }

    public void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("HttpServer01 started at: " + port);
        RegisterUtils.register("server01", "localhost", port);
        while (true) {
            Socket accept = serverSocket.accept();
            HttpService.handle(accept);
            accept.close();
        }
    }


    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: port1[,por2...]");
            System.exit(1);
        }
        String[] ports = args[0].split(",");
        for (String port : ports) {
            new Thread(() -> {
                try {
                    new HttpServer01(Integer.valueOf(port)).run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
