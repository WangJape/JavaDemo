package com.jape.concurrent.Juc.ProductConsumer;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    public static void main(String[] args) {

        //信号量
        Semaphore semaphore = new Semaphore(3);

        for(int i = 0; i < 10; i++){
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "获得一个信号量");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.err.println(Thread.currentThread().getName() + "释放一个信号量");
                    semaphore.release();
                }
            }).start();
        }


    }
}
