package com.jape.concurrent.Juc;

import java.util.concurrent.CountDownLatch;

/**
 * 倒计时同步器，等待所有线程执行完毕
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(2);

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+"加载数据");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName()+"数据加载完成,等待其他线程加载完");
                latch.countDown();
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println(Thread.currentThread().getName()+"开始运行");
        }).start();

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+"加载数据");
                Thread.sleep(3000);
                System.out.println(Thread.currentThread().getName()+"数据加载完成,等待其他线程加载完");
                latch.countDown();
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.err.println(Thread.currentThread().getName()+"开始运行");
        }).start();

    }

}
