package ru.seet61.sockets;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class SingleClient {
    private static final int PORT = 2018;

    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost", PORT);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        ) {
            System.out.println("Client connected to socket " + PORT);
            System.out.print("client > ");
            while (!socket.isOutputShutdown()) {
                // ждём консоли клиента на предмет появления в ней данных
                if (br.ready()) {
                    System.out.print("client > ");
                    String clentCommand = br.readLine();
                    System.out.println();
                    dos.writeUTF(clentCommand);
                    dos.flush();
                    // смотрим что нам ответил сервер
                    System.out.println("server > " + dis.readUTF());
                    if ("quit".equalsIgnoreCase(clentCommand)) {
                        System.out.println("Client kill connections...");
                        Thread.sleep(2000);
                        break;
                    }
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
