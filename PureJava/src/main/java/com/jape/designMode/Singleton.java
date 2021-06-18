package com.jape.designMode;

import java.util.concurrent.locks.ReentrantLock;

public class Singleton {
    private volatile static Singleton singleton;
    private static ReentrantLock lock = new ReentrantLock();
    private Singleton(){
        //throw new RuntimeException("单例模式不允许调用构造方法，反射也不行");
    }
    public static Singleton getInstance(){
        if (singleton == null){
            lock.lock();
            try {
                if (singleton == null){
                    Thread.sleep(200);
                    singleton = new Singleton();
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
        return singleton;
    }
}


class Test{
    public static void main(String[] args) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                /*Singleton singleton = Singleton.getInstance();
                System.err.println(Thread.currentThread().getName()+ "=>" + singleton);*/
                System.err.println(Thread.currentThread().getName()+ "=>" + Singleton.getInstance());
            }
        };
        new Thread(task).start();
        new Thread(task).start();
        new Thread(task).start();
    }
}