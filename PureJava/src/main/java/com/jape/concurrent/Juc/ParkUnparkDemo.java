package com.jape.concurrent.Juc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.LockSupport;

/**
 * park 不会释放锁，unpark()可以提前于park()调用
 */
public class ParkUnparkDemo {

    private static boolean flag = true;
    private static final Object lock = new Object();
    private static Thread parkedThread;

    public static void main(String[] args) throws Exception {
        Thread waitThread = new Thread(new Wait(), "Wait Thread");
        waitThread.start();
        Thread.sleep(1000);
        Thread notifyThread = new Thread(new Notify(), "Notify Thread");
        notifyThread.start();
    }

    private static class Wait implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                while (flag) {
                    System.out.println(Thread.currentThread() + " flag is true. wait @ " +
                            new SimpleDateFormat("HH:mm:ss").format(new Date()));
                    parkedThread = Thread.currentThread();
                    //LockSupport.park(parkedThread);
                    LockSupport.park();
                }
                System.out.println(Thread.currentThread() + " flag is false. running @ " +
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }

    private static class Notify implements Runnable {
        @Override
        public void run() {
            //synchronized (lock) {
            System.out.println(Thread.currentThread() + " hold lock. notify @ " +
                    new SimpleDateFormat("HH:mm:ss").format(new Date()));
            flag = false;
            LockSupport.unpark(parkedThread);
            try {
                Thread.sleep(5000); // Thread.sleep() 不会释放锁
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            synchronized (lock) {
                System.out.println(Thread.currentThread() + " hold lock again. sleep @ " +
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
