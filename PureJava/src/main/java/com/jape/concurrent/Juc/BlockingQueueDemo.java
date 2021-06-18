package com.jape.concurrent.Juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueDemo {

    public static void main(String[] args) {

        /**
         * 阻塞的：put(),take()
         * 非阻塞的：add(),remove()
         */
        BlockingQueue queue = new ArrayBlockingQueue(100);
        new LinkedBlockingQueue<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int n = 0;
                String msg;
                while (true) {
                    n++;
                    try {
                        msg = "第" + n + "个消息";
                        queue.put(msg);
                        System.err.println("发送消息[" + msg + "]，当前阻塞队列中剩余消息数：[" + queue.size() + "]");
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int n = 0;
                while (true) {
                    n++;
                    String msg;
                    try {
                        msg = (String) queue.take();
                        System.out.println("接收到消息：[" + msg + "]，当前阻塞队列中剩余消息数：[" + queue.size() + "]");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
        //queue.clear();


    }

}
