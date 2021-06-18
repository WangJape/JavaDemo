package com.jape.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolUseRunnable {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        /**
         * 不需要返回值，直接用execute()方法
         * 需要返回值，用submit()方法
         */
        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.print("["+ Thread.currentThread().getName() +"]线程运行。");
                System.out.println("当前存活线程数["+ Thread.activeCount() +"]");
            }
        };
        for(int i = 0;i<5;i++){
            threadPool.execute(task);
        }
        threadPool.shutdown();
    }
}
