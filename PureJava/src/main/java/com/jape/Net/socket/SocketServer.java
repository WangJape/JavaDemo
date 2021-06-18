package com.jape.Net.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description:
 * @Auther: 王建朋
 * @Date: 2020/9/3 23:09
 */
public class SocketServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(666);
        Socket socket = serverSocket.accept();

        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String reqMsg;
        while ((reqMsg = bufferedReader.readLine()) != null) {
            System.out.println(reqMsg);
        }

        Thread.sleep(60000);

        String rspMsg = "22222";
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(rspMsg.getBytes());
        socket.shutdownOutput();


        inputStream.close();
        inputStreamReader.close();
        bufferedReader.close();
        outputStream.close();
        socket.close();
        serverSocket.close();

    }
}
