package com.jape.Net.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 同步非阻塞模式，面向缓冲区（块）编程，是事件驱动的
 * 数据从channel读取到buffer，或者从buffer写入到channel
 * 单个selector监听多个channel事件（连接请求、数据到达...）
 */
public class NIOService {

    public static void main(String[] args) throws IOException {

        //创建ServerSocketChannel -> SocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(80));
        System.err.println("服务启动，开始监听端口80");

        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        //得到一个selector
        Selector selector = Selector.open();

        //把serverSocketChannel注册到selector, 监听事件OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            //当注册事件到达时，方法返回，否则该方法会一直阻塞
            selector.select();

            //已经获取到关注的事件，获取到关注事件的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //迭代器迭代
            Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();

            while (selectionKeyIterator.hasNext()) {
                SelectionKey key = selectionKeyIterator.next();
                //当获牌一个selectionkey后，就将它去除，表示我们已经对这个I0操作进行了处理。
                selectionKeyIterator.remove();

                if (key.isAcceptable()) {
                    //有连接事件,则生成一个SocketChannel
                    //注意，在OP_ACCEPT 事件中，从key.channel（）返回的 Channel是ServerSocketChannel.
                    //而在OP_WRITE和OP_READ中，从key.channel（）返回的是 SocketChannel.
                    SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
                    //设置为非阻塞,必须设置为非阻塞，才能向Selector注册
                    socketChannel.configureBlocking(false);
                    // 将此socketChannel注册到selector，给通道设置读事件，客户端监听到读事件后进行读取操作,为此channel关联一个ByteBuffer
                    socketChannel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                } else if (key.isReadable()) {
                    //有读取事件
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    //获取到关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    //从通道读取数据到缓冲区
                    int readSize = socketChannel.read(buffer);
                    if (readSize == -1) { //keepalive
                        socketChannel.close();
                        System.err.println("发送响应完毕,已断开连接");
                    } else {
                        //输出
                        System.out.println(new String(buffer.array()));
                        key.interestOps(SelectionKey.OP_WRITE);
                    }

                } else if (key.isValid() && key.isWritable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    buffer.flip();
                    buffer.put("HTTP/1.1 200 OK\r\n".getBytes());
                    buffer.put("Content-Length: 11\r\n\r\n".getBytes());
                    buffer.put("Hello World".getBytes());
                    buffer.flip();
                    socketChannel.write(buffer);
                    buffer.compact();
                    key.interestOps(SelectionKey.OP_READ);
                }

            }

        }


    }

}
