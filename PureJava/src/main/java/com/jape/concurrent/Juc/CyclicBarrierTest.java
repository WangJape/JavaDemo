package com.jape.concurrent.Juc;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 可回收计数的运行同步器（屏障），相比CountDownLatch可回收计数
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        Runnable callback = ()->{
            System.err.println("一轮加载已经完成");
        };

        CyclicBarrier barrier = new CyclicBarrier(2, callback);

        Runnable task = () -> {
            try {
                System.out.println(Thread.currentThread().getName()+"开始新一轮的加载数据");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName()+"数据加载完成,等待其他线程加载完");
                barrier.await();
                System.err.println(Thread.currentThread().getName()+"开始运行");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < 5; i++) {
            threadPool.execute(task);
            threadPool.execute(task);

        }


    }

}
