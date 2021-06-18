package com.jape.concurrent.Juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class AQSDemo {

    public static void main(String[] args) {
        MyLock lock = new MyLock();

        Runnable task = () -> {
            System.out.println(Thread.currentThread().getName()+"竞争锁。。。");
            lock.lock();
            System.out.println(Thread.currentThread().getName()+"获得锁");
            try {
                Thread.sleep(2000);
            } catch (Exception e){
                System.err.println(e.getCause());
            } finally {
                System.out.println(Thread.currentThread().getName()+"释放锁");
                lock.unlock();
            }
        };

        new Thread(task).start();
        new Thread(task).start();
        new Thread(task).start();

    }

}

//自定义锁，不可重入锁
class MyLock implements Lock{

    /**
     * 独占锁
     */
    class MySync extends AbstractQueuedSynchronizer{

        /**
         * 尝试获得锁（一次）
         * @param arg
         * @return
         */
        @Override
        protected boolean tryAcquire(int arg) {
            if(compareAndSetState(0,1)){
                //加锁成功,设置持有者为当前线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);

            return true;
        }

        /**
         * 是否持有独占锁
         * @return
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        /**
         * 返回一个条件变量
         * @return
         */
        public Condition newCondition(){
            return new ConditionObject();
        }
    }

    private MySync sync = new MySync();


    /**
     * 加锁，不成功进入到等待队列
     */
    @Override
    public void lock() {
        sync.acquire(1);
    }

    /**
     * 可中断的加锁
     * @throws InterruptedException
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    /**
     * 尝试加锁一次
     * @return
     */
    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    /**
     * 带超时的尝试加锁
     * @param time
     * @param unit
     * @return
     * @throws InterruptedException
     */
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    /**
     * 解锁
     */
    @Override
    public void unlock() {
        sync.release(0);
    }

    /**
     * 创建条件变量
     * @return
     */
    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}