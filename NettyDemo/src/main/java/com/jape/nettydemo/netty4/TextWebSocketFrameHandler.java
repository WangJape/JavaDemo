package com.jape.nettydemo.netty4;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * CloseWebSocketFrame
 * PingWebSocketFrame
 * PongWebSocketFrame
 * TextWebSocketFrame
 * <p>
 * WebSocket TextWebSocketFrame数据帧处理类
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        Channel incoming = channelHandlerContext.channel();
        for (Channel channel : channels) {
            if (channel != incoming) {
                channel.writeAndFlush(new TextWebSocketFrame("[接收到来自" + incoming.remoteAddress() + "的消息]" + textWebSocketFrame.text()));
            } else {
                channel.writeAndFlush(new TextWebSocketFrame("[消息已发送]" + textWebSocketFrame.text()));
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        String msg = "[系统提示] - " + incoming.remoteAddress() + " 上线";
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame(msg));
        }
        channels.add(ctx.channel());
        System.out.println(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        String msg = "[系统提示] - " + incoming.remoteAddress() + " 离线";
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame(msg));
        }
        System.out.println(msg);
        channels.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("客户端:" + incoming.remoteAddress() + "异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
