package com.jape.concurrent.Juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

/**
 * Java 8引入了新的读写锁 StampedLock
 * 所有获取锁的方法，都返回一个邮戳（Stamp），Stamp为0表示获取失败，其余都表示成功；
 * 所有释放锁的方法，都需要一个邮戳（Stamp），这个Stamp必须是和成功获取锁时得到的Stamp一致
 *
 *
 * StampedLock提供了乐观读锁，可取代ReadWriteLock以进一步提升并发性能
 * StampedLock 是不可重入锁
 * StampedLock和ReadWriteLock相比，改进之处在于：
 *  读的过程中也允许获取写锁后写入！这样一来，我们读的数据就可能不一致，所以，需要一点额外的代码来判断读的过程中是否有写入，这种读锁是一种乐观锁。
 *  乐观锁的意思就是乐观地估计读的过程中大概率不会有写入，因此被称为乐观锁。
 *  反过来，悲观锁则是读的过程中拒绝有写入，也就是写入必须等待。显然乐观锁的并发效率更高，但一旦有小概率的写入导致读取的数据不一致，需要能检测出来，再读一遍就行。
 */
public class StampedLockDemo {

    private double x;
    private StampedLock s1 = new StampedLock();//定义StampedLock锁

    void write(double deltaX) {
        long stamp = s1.writeLock();//写锁
        try {
            x += deltaX;
            System.out.println(Thread.currentThread().getName() + "写入:" +deltaX);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            s1.unlockWrite(stamp);//退出临界区,释放写锁
        }
    }

    double read() {//只读方法
        long stamp = s1.tryOptimisticRead();  //试图尝试一次乐观读 返回一个类似于时间戳的邮戳整数stamp 这个stamp就可以作为这一个所获取的凭证
        double currentX = x;//读取x和y的值,这时候我们并不确定x和y是否是一致的
        /**
         * 判断这个stamp是否在读过程发生期间被修改过,
         * 如果stamp没有被修改过,则认为这次读取时有效的,因此就可以直接return了,
         * 反之,如果stamp是不可用的,则意味着在读取的过程中,可能被其他线程改写了数据,
         * 因此,有可能出现脏读,如果出现这种情况,我们可以像CAS操作那样在一个死循环中一直使用乐观锁,知道成功为止
         */
        if (!s1.validate(stamp)) {
            System.err.println(Thread.currentThread().getName() + "读取时遇到写入，改为悲观读锁");
            stamp = s1.readLock();//升级乐观锁的级别,将乐观锁变为悲观锁, 如果当前对象正在被修改,则读锁的申请可能导致线程挂起.
            try {
                currentX = x;
            } finally {
                s1.unlockRead(stamp);//退出临界区,释放读锁
            }
        }
        return currentX;
    }





    public static void main(String[] args) throws InterruptedException {

        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        StampedLockDemo a = new StampedLockDemo();

        Runnable readTask = new Runnable() {
            @Override
            public void run() {
                while(true){
                    System.err.println(Thread.currentThread().getName() + "读出:" +a.read());
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Runnable writeTask = new Runnable() {
            @Override
            public void run() {
                while(true){
                    a.write(1);
                    System.out.println(Thread.currentThread().getName() + "写入完毕");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        threadPool.execute(writeTask);
        Thread.sleep(1600);
        threadPool.execute(writeTask);
        threadPool.execute(readTask);
        threadPool.shutdown();

    }


}
