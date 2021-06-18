package com.jape.concurrent.Juc;

import com.jape.entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.*;

public class AtomicDemo {

    volatile int va;

    public static void main(String[] args) {

        AtomicDemo ad = new AtomicDemo();

        AtomicInteger ai = new AtomicInteger();
        AtomicLongArray ala = new AtomicLongArray(10);

        User user1 = new User();
        user1.setUserName("111");
        User user2 = new User();
        user2.setUserName("222");
        AtomicReference arUser = new AtomicReference(user1);
        arUser.compareAndSet(user1,user2);

        LongAdder l = new LongAdder();
        DoubleAdder d = new DoubleAdder();
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        Runnable writeVa = new Runnable() {
            @Override
            public void run() {
                while(true){
                    System.out.println(++ ad.va);
                    /*try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        };
        Runnable task = new Runnable() {
            @Override
            public void run() {
                while (true){
                    l.add(1);
                    System.out.println(Thread.currentThread().getName() + "["+ l +"]");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        for (int i = 0; i < 5; i++) {
            threadPool.execute(writeVa);
        }


    }
}
