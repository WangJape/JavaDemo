package com.jape.nettydemo.netty3;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Netty3Client {

    public static void main(String args[]) {
        // Client服务启动器
        ClientBootstrap bootstrap = new ClientBootstrap(
                new NioClientSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));
        // 设置一个处理服务端消息和各种消息事件的类(Handler)
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new StringDecoder());
                pipeline.addLast("encoder", new StringEncoder());
                pipeline.addLast("handler", new HelloClientHandler());
                return pipeline;
            }
        });
        // 连接到本地的8000端口的服务端
        bootstrap.connect(new InetSocketAddress("127.0.0.1", 8569));
        System.out.println("client start success!");
    }

    private static class HelloClientHandler extends SimpleChannelHandler {

        /**
         * 当绑定到服务端的时候触发
         */
        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            System.out.println("客户端连接成功!");
            String str = "0420<REQUEST>" +
                    "  <HEAD>" +
                    "    <TRCD>0001</TRCD>" +
                    "    <BRCHNO>1</BRCHNO>" +
                    "    <OPRCODE>1</OPRCODE> " +
                    "    <TRDT>1</TRDT>" +
                    "    <TRTM>1</TRTM>" +
                    "    <MSCOPRSEQ>1</MSCOPRSEQ>" +
                    "    <TRTP>1</TRTP>" +
                    "  </HEAD>" +
                    "  <BODY>" +
                    "    <source>1</source>" +
                    "    <CardNo>1</CardNo>" +
                    "    <CfAdjType>1</CfAdjType>" +
                    "    <CfReasonCode>1</CfReasonCode>" +
                    "    <CfExpectAmt>1</CfExpectAmt> " +
                    "    <remark01>1</remark01>" +
                    "    <remark02>1</remark02>" +
                    "    <remark03>1</remark03>" +
                    "  </BODY>" +
                    "</REQUEST>";
            e.getChannel().write(str);//异步
        }

        @Override
        public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e) throws Exception {
            System.out.println("客户端写消息完成");
        }

        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
            String msg = (String) e.getMessage();
            System.out.println("客户端接收到消息, msg: " + msg);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
            e.getCause().printStackTrace();
            e.getChannel().close();
        }
    }

}
