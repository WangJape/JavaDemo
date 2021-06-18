package com.jape.Net.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 一个channel必须对应一个buffer,或者buffer数组
 *
 *
 * FileChannel
 * ServerSocketChannel -> SocketChannel
 *
 */
public class ChannelDemo {

    public static void main(String[] args) throws IOException {

        // 创建通道ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 绑定端口并启动
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));

        // 创建buffer,这里实验一个数组，测试Buffer的分散与聚集
        ByteBuffer[] buffers = new ByteBuffer[2];
        buffers[0] = ByteBuffer.allocate(256);
        buffers[1] = ByteBuffer.allocate(512);

        while (true){
            // 等待客户端连接
            SocketChannel socketChannel = serverSocketChannel.accept();

            // 从通道读到buffer中
            socketChannel.read(buffers);

            //非正规输出，只是测试
            Arrays.asList(buffers).forEach(byteBuffer -> {
                String request = new String(byteBuffer.array());
                System.err.println(request);
            });

            // buffer写入翻转为读取
            Arrays.asList(buffers).forEach(byteBuffer -> byteBuffer.flip());

            // 返回响应
            socketChannel.write(buffers);

            // 清空buffers
            Arrays.asList(buffers).forEach(byteBuffer -> byteBuffer.clear());
        }

    }

}
