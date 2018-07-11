package ru.seet61.sockets.tcp.multithreads;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadServer {
    private static ExecutorService pool = Executors.newFixedThreadPool(10);
    private static final int PORT = 2018;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println(String.format("Application started, and listening port: %d", PORT));
            System.out.println("Wait connect...");
            while (true) {
                pool.execute(new MonoThreadClientHandler(server.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
