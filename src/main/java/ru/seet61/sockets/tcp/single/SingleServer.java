package ru.seet61.sockets.tcp.single;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleServer {
    /***
     * https://habr.com/post/330676/
     */
    private static final int PORT = 2018;

    public static void main(String[] args) {
        System.out.println("Waiting for connection...");
        try(ServerSocket server = new ServerSocket(PORT);
            Socket client = server.accept();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        ) {
            System.out.println("Connection accepted from " + client.getInetAddress() + " " + client.getPort());
            // начинаем диалог с подключенным клиентом в цикле, пока сокет не закрыт
            while(client.isConnected()){
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
            System.out.println("Exit");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
