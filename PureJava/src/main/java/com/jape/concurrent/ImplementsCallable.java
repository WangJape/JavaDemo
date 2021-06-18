package com.jape.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ImplementsCallable implements Callable<Double> {
    @Override
    public Double call() throws Exception {
        System.out.println("["+ Thread.currentThread().getName() +"]线程运行");
        double d = Math.random();
        return d;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ImplementsCallable callable = new ImplementsCallable();
        FutureTask<Double> task1 = new FutureTask<>(callable);
        FutureTask<Double> task2 = new FutureTask<>(callable);
        FutureTask<Double> task3 = new FutureTask<>(callable);
        FutureTask<Double> task4 = new FutureTask<>(callable);

        Thread thread1 = new Thread(task1,"线程1");
        Thread thread2 = new Thread(task2,"线程2");
        Thread thread3 = new Thread(task3,"线程3");
        Thread thread4 = new Thread(task4,"线程4");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        System.out.println(task1.get());
        System.out.println(task2.get());
        System.out.println(task3.get());
        System.out.println(task4.get());

    }
}
