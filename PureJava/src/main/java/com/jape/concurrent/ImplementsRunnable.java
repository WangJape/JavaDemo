package com.jape.concurrent;

public class ImplementsRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("["+ Thread.currentThread().getName() +"]线程运行");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ImplementsRunnable task = new ImplementsRunnable();
        Thread thread1 = new Thread(task,"线程1");
        Thread thread2 = new Thread(task,"线程2");
        Thread thread3 = new Thread(task);
        Thread thread4 = new Thread(task);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
