package com.jape.Net.SocketTest;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
            //创建socket
            Socket socket=new Socket("127.0.0.1",6000);
            
            //发送消息
            OutputStream outputStream=socket.getOutputStream();
            PrintWriter printWriter=new PrintWriter(outputStream);
            printWriter.print("hello, i am wang.");
            printWriter.flush();
            socket.shutdownOutput();
            
            //接收消息
            InputStream inputStream=socket.getInputStream();
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String temp=null;
            while((temp=bufferedReader.readLine())!=null){
                System.out.println("server reply:"+temp);
            }
            
            //关闭资源
            bufferedReader.close();
            inputStream.close();
            printWriter.close();
            outputStream.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }

}
