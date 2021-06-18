package com.jape.Net.socket;

import java.io.*;
import java.net.Socket;

/**
 * @Description:
 * @Auther: 王建朋
 * @Date: 2020/9/3 20:36
 */
public class ScoketClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 666);
        socket.setSoTimeout(1000);

        String reqMsg = "11111";
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(reqMsg.getBytes());
        socket.shutdownOutput();

        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String rspMsg;
        while ((rspMsg = bufferedReader.readLine()) != null) {
            System.out.println(rspMsg);
        }

        outputStream.close();
        inputStream.close();
        inputStreamReader.close();
        bufferedReader.close();
        socket.close();

    }

}
