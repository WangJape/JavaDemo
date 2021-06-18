package com.jape.concurrent;

import com.jape.entity.User;
import org.openjdk.jol.info.ClassLayout;

/**
 * |--------------------------------------------------------------------------------------------------------------|
 * |                                              Object Header (128 bits)                                        |
 * |--------------------------------------------------------------------------------------------------------------|
 * |                        Mark Word (64 bits)                                    |      Klass Word (64 bits)    |
 * |--------------------------------------------------------------------------------------------------------------|
 * |  unused:25 | identity_hashcode:31 | unused:1 | age:4 | biased_lock:1 | lock:2 |     OOP to metadata object   |  无锁   01
 * |----------------------------------------------------------------------|--------|------------------------------|
 * |  thread:54 |         epoch:2      | unused:1 | age:4 | biased_lock:1 | lock:2 |     OOP to metadata object   |  偏向锁 01
 * |----------------------------------------------------------------------|--------|------------------------------|
 * |                     ptr_to_lock_record:62                            | lock:2 |     OOP to metadata object   |  轻量锁 00
 * |----------------------------------------------------------------------|--------|------------------------------|
 * |                     ptr_to_heavyweight_monitor:62                    | lock:2 |     OOP to metadata object   |  重量锁 10
 * |----------------------------------------------------------------------|--------|------------------------------|
 * |                                                                      | lock:2 |     OOP to metadata object   |    GC   11
 * |--------------------------------------------------------------------------------------------------------------|
 *
 * 设置JVM参数 -XX:BiasedLockingStartupDelay=0 来取消延时加载偏向锁。
 *  初始001（还是101），允许偏向锁
 *  第一个线程进入同步代码块，CAS(Compare and Swap)操作，将线程ID插入到Markword中，同时修改偏向锁的标志位，对象偏向于此线程
 *      若第二个线程来竞争，比较 线程ID：
 *          若不是自己，暂停拥有偏向锁的线程，检查线程是否存活：
 *              非活动状态，则设置为无锁状态
 *              存活，则重新偏向于其他线程或者恢复到无锁状态或者标记对象不适合作为偏向锁，升为轻量级锁 00，自旋超时,升级锁为重量级锁 10,对象头部存monitor地址，阻塞自己
 *      没有竞争，直接出同步代码块，保留头部偏向
 *  第二个线程进入同步块，发现偏向不是自己，升级锁为轻量级锁 00（栈帧与对象交换头部信息，栈帧中记录对象MarkWork,对象头保存锁记录）
 *      运行中有另一个线程竞争，发现有轻量级锁，自旋超时，则升级为重量级锁 10，把自己写入EntryList 唤醒队列中开始阻塞
 *      没有竞争，恢复锁为 01 无锁状态，
 */
public class SyncTest {

    public static void main(String[] args) {
        User user = new User();

        Thread t1,t2;

        t1 = new Thread(()->{
            System.out.println(Thread.currentThread().getName() +"竞争进入同步代码块" + SyncTest.printHeader(user));
            synchronized (user){
                System.out.println(Thread.currentThread().getName() +"获得锁"+ SyncTest.printHeader(user));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() +"临释放前看一眼锁"+ SyncTest.printHeader(user));
            }
            System.out.println(Thread.currentThread().getName() +"释放锁"+ SyncTest.printHeader(user));

        });
        t1.start();

        t2 = new Thread(()->{

            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() +"竞争进入同步代码块" + SyncTest.printHeader(user));
            synchronized (user){
                System.out.println(Thread.currentThread().getName() +"获得锁"+ SyncTest.printHeader(user));
                System.out.println(Thread.currentThread().getName() +"临释放前看一眼锁"+ SyncTest.printHeader(user));
            }
            System.out.println(Thread.currentThread().getName() +"释放锁"+ SyncTest.printHeader(user));
        });
        t2.start();
        
    }

    public static String printHeader(Object obj){
        String info = ClassLayout.parseInstance(obj).toPrintable();
        String header = info.substring(info.indexOf('\n'), 510);
        return header;
    }

}
