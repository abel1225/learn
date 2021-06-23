package me.abel.learn.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Abel.li
 * @description
 * @contact abel0130@163.com
 * @date 2020-03-15
 */
public class BioServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            System.out.println("bioserver start, listening on port " + serverSocket.getLocalSocketAddress());
            while (true) {
                Socket clentSocket = serverSocket.accept();
                System.out.println("connection from " + clentSocket.getRemoteSocketAddress());
                try (Scanner input = new Scanner(clentSocket.getInputStream())) {
                    while (true) {
                        String request = input.nextLine();
                        if ("quit".equals(request)) {
                            break;
                        }
                        System.out.println(String.format("from %s: %s", clentSocket.getRemoteSocketAddress(), request));
                        String response = "from bioserver " + request;
                        clentSocket.getOutputStream().write(response.getBytes());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
