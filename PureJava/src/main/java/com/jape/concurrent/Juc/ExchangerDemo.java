package com.jape.concurrent.Juc;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Exchanger 是 JDK 1.5 开始提供的一个用于两个工作线程之间交换数据的封装工具类，
 *  简单说就是一个线程在完成一定的事务后想与另一个线程交换数据，
 *  则第一个先拿出数据的线程会一直等待第二个线程，
 *  直到第二个线程拿着数据到来时才能彼此交换对应数据。
 *
 * 当一个线程到达 exchange 调用点时，
 * 如果其他线程此前已经调用了此方法，
 * 则其他线程会被调度唤醒并与之进行对象交换，然后各自返回；
 *
 * 如果其他线程还没到达交换点，则当前线程会被挂起，
 * 直至其他线程到达才会完成交换并正常返回，或者当前线程被中断或超时返回
 *
 */
public class ExchangerDemo {

    public void a(Exchanger<String> exch){
        System.out.println("a方法执行");
        try {
            Thread.sleep(2000);
            System.out.println("a方法数据交换启动");
            for(int i = 0;i < 10; i++){
                String result = exch.exchange("12345");
                System.err.println("a方法换回的数据为["+ result +"]");
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void b(Exchanger<String> exch){
        System.out.println("b方法执行");
        try {
            Thread.sleep(1000);
            System.out.println("b方法数据交换启动");
            for(int i = 0;i < 10; i++){
                String result = exch.exchange("上山打老鼠");
                System.err.println("b方法换回的数据为["+ result +"]");
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        ExchangerDemo task = new ExchangerDemo();
        Exchanger exch = new Exchanger();
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                task.a(exch);
            }
        });
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                task.b(exch);
            }
        });

    }

}
