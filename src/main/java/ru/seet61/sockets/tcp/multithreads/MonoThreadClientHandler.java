package ru.seet61.sockets.tcp.multithreads;

import java.io.*;
import java.net.Socket;

public class MonoThreadClientHandler implements Runnable {
    private Socket socket;

    public MonoThreadClientHandler(Socket accept) {
        this.socket = accept;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            while (this.socket.isConnected()) {
                String line = reader.readLine();
                System.out.println("client > " + line);
                // Отправка сообщения клиенту
                writer.write("Server reply - " + line + " - OK" );
                writer.newLine();
                writer.flush();
                // инициализация проверки условия продолжения работы с клиентом по этому сокету по кодовому слову - quit
                if ("quit".equalsIgnoreCase(line)) {
                    Thread.sleep(3000);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
