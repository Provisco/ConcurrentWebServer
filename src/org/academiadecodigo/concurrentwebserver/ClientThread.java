package org.academiadecodigo.concurrentwebserver;

import java.io.*;
import java.net.Socket;

/**
 * Created by codecadet on 09/11/16.
 */
public class ClientThread implements Runnable {


    Socket clientSocket;


    public ClientThread(Socket clientSocket) {
    }

    @Override
    public void run() {

        String notFound = "HTTP/1.0 404 Not Found\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "Content-Length: <file_byte_size> \r\n" +
                "\r\n";

        String found = "HTTP/1.0 200 Document Follows\r\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "Content-Length: <file_byte_size> \r\n" +
                "\r\n";


        String getRequest = "GET / HTTP/1.1";


        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String[] inputClient = in.readLine().split(" ");
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            if (inputClient == null) {
                clientSocket.close();
            }
            if (inputClient[1].equals("/")) {
                //if (inputClient.toLowerCase().contains(getRequest.toLowerCase())) {
                out.write(buildHeader("playground.html").getBytes());
                out.write(readFile("/Users/codecadet/IdeaProjects/exercises/SimpleWebServer/resources/playground.html").getBytes());
            } else {
                //acrescentar uma condiçao para um ficheiro html que exista mas que nao esteja comtemplado no if anterior
                out.write(notFound.getBytes());
            }
            out.flush();
            clientSocket.close();
             } catch (IOException e) {
            e.printStackTrace();
            }
    }


    public static String readFile(String path) throws IOException {
        FileReader reader;
        BufferedReader bReader = null;
        String line = "";
        String result = "";
        try {
            bReader = new BufferedReader(new FileReader(path));
            while ((line = bReader.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bReader.close();
            return result;
        }
    }


    public static String buildHeader(String resource) {

        File file = new File("resources/" + resource);
        String statusCode = "";
        String fileType = "";
        String size = "";

        if (file.exists()) {
            statusCode = "200";
            fileType = file.getName();
            size = String.valueOf(file.length());
        } else {
            statusCode = "400";
            fileType = "html";
            size = String.valueOf(file.length());
        }

        return "HTTP/1.0 " + statusCode + "\r\n" +
                "Content-Type:" + fileType + "charset=UTF-8\r\n" +
                "Content-Length:" + size + "\r\n" +
                "\r\n";
    }

}



