package com.jape.Net.BIO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *                      .::::.
 *                    .::::::::.
 *                   :::::::::::
 *                ..:::::::::::'
 *             '::::::::::::'
 *               .::::::::::
 *          '::::::::::::::..
 *               ..::::::::::::.
 *             ``::::::::::::::::
 *              ::::``:::::::::'        .:::.
 *             ::::'   ':::::'       .::::::::.
 *           .::::'      ::::     .:::::::'::::.
 *          .:::'       :::::  .:::::::::' ':::::.
 *         .::'        :::::.:::::::::'      ':::::.
 *        .::'         ::::::::::::::'         ``::::.
 *    ...:::           ::::::::::::'              ``::.
 *   ```` ':.          ':::::::::'                  ::::..
 *                      '.:::::'                    ':'````..
 * 同步阻塞I/O模型，数据的读取写入必须阻塞在一个线程内等待其完成。
 * 基于字节流、字符流操作
 */
public class BIOService {

    public static void main(String[] args) {

        ExecutorService threadPool1 = Executors.newCachedThreadPool();
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        ServerSocket serverSocket = null;
        try {
            System.err.println("服务启动，开始监听端口80");
            serverSocket = new ServerSocket(80);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true){

            try {
                //accept阻塞线程，直到有请求加入
                final Socket socket = serverSocket.accept();
                System.err.println("请求接入，新建线程处理");
                threadPool.execute(() -> {
                    System.err.println(Thread.currentThread().getName());

                    try {
                        //输出请求信息
                        InputStream request = socket.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(request));
                        StringBuffer msg = new StringBuffer();
                        while (true) {
                            msg.append(reader.readLine());
                            if (msg.length() == 0) {
                                break;
                            }
                            System.out.println(msg);
                            msg.delete(0, msg.length());
                        }

                        //发送响应
                        OutputStream response = socket.getOutputStream();
                        response.write("HTTP/1.1 200 OK\r\n".getBytes());
                        response.write("Content-Length: 11\r\n\r\n".getBytes());
                        response.write("Hello World".getBytes());
                        response.flush();
                        System.err.println("发送响应完毕");

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
