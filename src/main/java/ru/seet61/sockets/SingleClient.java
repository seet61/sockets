package ru.seet61.sockets;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class SingleClient {
    private static final int PORT = 2018;

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", PORT);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Client connected to socket " + PORT);
            while (socket.isConnected()) {
                String line = null;
                String answer = null;
                System.out.print("client > ");
                line = keyboard.readLine();
                writer.write(line);
                writer.newLine();
                writer.flush();
                if ((answer = reader.readLine()) != null) {
                    System.out.println("server > " + answer);
                }
                if ("quit".equalsIgnoreCase(line)) {
                    System.out.println("Exit!");
                    Thread.sleep(1000);
                    break;
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
