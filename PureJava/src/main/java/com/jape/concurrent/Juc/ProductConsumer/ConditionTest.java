package com.jape.concurrent.Juc.ProductConsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {

    Lock lock = new ReentrantLock();

    Condition p = lock.newCondition();
    Condition c = lock.newCondition();

    public int product = 0;
    public static int Max_Product = 20;

    public void Producting(){
        lock.lock();
        try {
            if(product == Max_Product ){
                System.out.println("[" + Thread.currentThread().getName() + "]等待。");
                p.await();
            }else{
                product ++;
                System.out.println("[" + Thread.currentThread().getName() + "]生产一个产品,剩余产品个数" + product);
                if(product == Max_Product/2){
                    System.out.println("[" + Thread.currentThread().getName() + "]库存充足，唤醒所有消费者。");
                    c.signalAll();
                }
                if(product == Max_Product ) {
                    System.out.println("库存满，[" + Thread.currentThread().getName() + "]等待。");
                    p.await();
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void Consuming(){
        lock.lock();
        try {
            if(product == 0){
                System.err.println("[" + Thread.currentThread().getName() + "]等待。");
                c.await();
            }else{
                product --;
                System.err.println("[" + Thread.currentThread().getName() + "]消费一个产品,剩余产品个数" + product);
                if(product == Max_Product/3){
                    System.err.println("[" + Thread.currentThread().getName() + "]库存告急，唤醒一个生产者。");
                    p.signal();
                }
                if(product == 0){
                    System.err.println("[" + Thread.currentThread().getName() + "]库存空，唤醒所有生产者。");
                    p.signalAll();
                    System.err.println("[" + Thread.currentThread().getName() + "]一个消费者等待");
                    c.await();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        ConditionTest ct = new ConditionTest();

        new Thread(new Producer(ct),"生产者1").start();
        new Thread(new Producer(ct),"生产者2").start();
        new Thread(new Consumer(ct),"消费者1").start();
        new Thread(new Consumer(ct),"消费者2").start();

        System.out.println("主线程执行完毕");

    }


}

class Producer implements Runnable{
    ConditionTest ct;
    Producer(ConditionTest ct){
        this.ct = ct;
    }
    @Override
    public void run() {
        while (true){
            ct.Producting();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable{
    ConditionTest ct;
    Consumer(ConditionTest ct){
        this.ct = ct;
    }
    @Override
    public void run() {
        while (true) {
            ct.Consuming();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
