package com.jape.concurrent;

public class ExtendThread extends Thread{

    public ExtendThread() {
        super();
    }
    public ExtendThread(String name) {
        super(name);
    }

    public void run(){
        System.out.println("[" + getName() + "]线程运行");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExtendThread thread1 = new ExtendThread("线程1");
        ExtendThread thread2 = new ExtendThread("线程2");
        ExtendThread thread3 = new ExtendThread();
        ExtendThread thread4 = new ExtendThread();

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

    }
}
