package ru.seet61.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            DataInputStream dis = new DataInputStream(client.getInputStream());
        ) {
            System.out.println("Connection accepted from " + client.getInetAddress() + " " + client.getPort());
            // начинаем диалог с подключенным клиентом в цикле, пока сокет не закрыт
            while(!client.isClosed()){
                String entry = dis.readUTF();
                System.out.println("client > " + entry);
                // Отправка сообщения клиенту
                dos.writeUTF("Server reply - " + entry + " - OK" );
                dos.flush();
                System.out.println("Server reply - " + entry + " - OK" );
                // инициализация проверки условия продолжения работы с клиентом по этому сокету по кодовому слову - quit
                if ("quit".equalsIgnoreCase(entry)) {
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
