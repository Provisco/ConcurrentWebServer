package org.academiadecodigo.concurrentwebserver;

import java.io.*;
import java.lang.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by codecadet on 09/11/16.
 */
public class WebServer {

    public static void main(String[] args) throws IOException {

        int portNum = 8080;


        ServerSocket serverSocket = new ServerSocket(portNum);
        System.out.println("Waiting for client");


        while (true) {

            Socket clientSocket = serverSocket.accept();

            Thread thread = new Thread(new ClientThread(clientSocket));

            System.out.println("Connected to client");


        }

    }
}
