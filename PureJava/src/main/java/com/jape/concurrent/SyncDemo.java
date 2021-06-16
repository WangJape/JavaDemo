package com.jape.concurrent;

import org.openjdk.jol.info.ClassLayout;

/**
 * -XX:+UseBiasedLocking
 * -XX:BiasedLockingStartupDelay=0
 */
class SyncDemo {
    public static void main(String[] args) throws InterruptedException {
        Sync sync = new Sync();
        System.err.println(ClassLayout.parseInstance(sync).toPrintable());

        new Thread(sync::doMethod).start();
        Thread.sleep(1000);
        new Thread(sync::doMethod).start();
    }


}

class Sync {
    int i = 0;

    public synchronized void doMethod() {
        System.err.println(ClassLayout.parseInstance(this).toPrintable());
        try {
            Thread.sleep(1000 * 60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void doClass() {
        System.err.println(ClassLayout.parseClass(Sync.class).toPrintable());
        try {
            Thread.sleep(1000 * 60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}