package com.jape.concurrent.Juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore(信号量)
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        // 线程池
        ExecutorService exec = Executors.newCachedThreadPool();
        // 只能5个线程同时访问
        final Semaphore semp = new Semaphore(5);

        Runnable run = ()-> {
            try {
                // 获取许可
                semp.acquire();
                System.out.println("Accessing: " + Thread.currentThread().getName());

                //模拟实际业务逻辑
                Thread.sleep((long) (Math.random() * 10000));
            } catch (InterruptedException e) {
            } finally {
                // 访问完后，释放
                semp.release();
            }
        };
        // 模拟20个客户端访问
        for (int index = 0; index < 20; index++) {
            exec.execute(run);
        }
        //System.out.println(semp.getQueueLength());

        // 退出线程池
        //exec.shutdown();
        //System.err.println("线程池关闭");
    }

}
