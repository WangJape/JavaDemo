package com.jape.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class IODemo {

    public static void main(String[] args) throws IOException {

        //1、读取文件属性
        File file = new File("H:\\项目\\Java\\wjp\\src\\main\\java\\Net\\FTP\\readme.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        fileInputStream.close();

        //2、打开一个随机访问文件流，按只读方式
        RandomAccessFile randomFile = new RandomAccessFile("H:\\项目\\Java\\wjp\\src\\main\\java\\Net\\FTP\\readme.txt", "r");
        // 文件总长度，字节数
        long lenTotal = randomFile.length();
        // 分段读取
        long segTotal = lenTotal / 1024;

        randomFile.seek(0);

        // 缓存
        byte[] buffer = new byte[1024];
        int readLen;
        while ((readLen = randomFile.read(buffer)) != -1) {
            System.out.write(buffer, 0, readLen);
        }
        randomFile.close();

    }

}
