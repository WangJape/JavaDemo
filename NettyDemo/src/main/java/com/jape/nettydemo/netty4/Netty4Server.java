package com.jape.nettydemo.netty4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.nio.charset.StandardCharsets;

public class Netty4Server {

    public static void main(String[] args) throws Exception {
        // 1 创建两个线程组
        // 一个是用于处理服务器端接收客户端连接的
        // 一个是处理已经被接收的连接
        // 一旦‘boss’接收到连接，就会把连接信息注册到‘worker’上。
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        // 2 创建辅助启动类，用于服务器通道的一系列配置
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)        // 绑定俩个线程组
                .channel(NioServerSocketChannel.class)             // 指定NIO的模式
                .option(ChannelOption.SO_BACKLOG, 1024)      // 设置tcp缓冲区
                .option(ChannelOption.SO_RCVBUF, 32 * 1024)  // 设置接收缓冲大小
                //.childOption(ChannelOption.SO_KEEPALIVE, true)    // http保持连接
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        // 3 在这里配置具体数据接收方法的处理

                        //tcp-socket
                        sc.pipeline()
                                .addLast(new ServerHandler());

                        // http
                        /*sc.pipeline()
                                .addLast(new HttpServerCodec())
                                .addLast(new HttpObjectAggregator(512 * 1024))
                                .addLast(new HttpRequestHandler());*/

                        // WebSocket
                        /*sc.pipeline()
                                .addLast("http-codec", new HttpServerCodec())//设置解码器
                                .addLast("aggregator",new HttpObjectAggregator(65536))//聚合器，使用websocket会用到
                                .addLast("http-chunked",new ChunkedWriteHandler())//用于大数据的分区传输
                                .addLast(new WebSocketServerProtocolHandler("/websocket/jape"))
                                .addLast(new TextWebSocketFrameHandler());*/
                    }
                });

        // 4 进行绑定 开始接收进来的连接
        ChannelFuture cf1 = bootstrap.bind(8569).sync();
        // 5 等待服务器socket 关闭 ，在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
        cf1.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }


    public static class ServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            System.out.println("一个连接被创建");
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            System.out.println("一个连接已断开");
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("一个连接已就绪");
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            Channel incoming = ctx.channel();
            System.out.println("一个连接已掉线");
        }

        //每当从客户端收到新的数据时，这个方法会在收到消息时被调用，这个例子中，收到的消息的类型是 ByteBuf
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = (ByteBuf) msg;

            // 方法一
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            String body = new String(req, StandardCharsets.UTF_8);

            //方法2
            /*req = buf.array();
            body = new String(req, StandardCharsets.UTF_8);*/

            System.out.println("服务端接受到请求:\n" + body);

        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            System.out.println("读取请求完成，开始发送响应数据");
            String response = "HTTP/1.1 200 OK\n" +
                    "Content-Length: 11\n\n" +
                    "Hello World";
            System.out.println("服务端发送相应信息:\n" + response);
            ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable t) throws Exception {
            System.out.println("链接异常");
            t.printStackTrace();
            ctx.close();
        }
    }


}
