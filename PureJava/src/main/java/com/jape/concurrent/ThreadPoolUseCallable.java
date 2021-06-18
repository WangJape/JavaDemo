package com.jape.concurrent;

import java.util.Random;
import java.util.concurrent.*;

public class ThreadPoolUseCallable{

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        Callable task = new Callable() {
            @Override
            public Integer call() throws Exception {
                System.out.print("["+ Thread.currentThread().getName() +"]线程运行。");
                System.out.println("当前存活线程数["+ Thread.activeCount() +"]");
                Random r = new Random();
                return r.nextInt();
            }
        };


        /**
         * submit有返回值，而execute没有
         * 需要返回值，用submit()方法
         * 不需要返回值，用execute()方法
         */
        for(int i = 0;i<5;i++){
            Future f = threadPool.submit(task);
            System.out.println(f.get());
        }
        threadPool.shutdown();
    }
}
