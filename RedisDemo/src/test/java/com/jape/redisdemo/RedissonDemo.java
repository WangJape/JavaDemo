package com.jape.redisdemo;

import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedissonDemo {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void lockDemo() throws InterruptedException {
        // 可重入锁
        RLock lock1 = redissonClient.getLock("Redisson:Lock:1");
        RLock lock2 = redissonClient.getLock("Redisson:Lock:2");
        RLock lock3 = redissonClient.getLock("Redisson:Lock:3");
        // 联锁
        RLock multiLock = redissonClient.getMultiLock(lock1, lock2, lock3);

        //公平锁
        RLock fairLock = redissonClient.getFairLock("Redisson:FairLock:1");

        System.err.println("竞争锁");
        lock1.lock();
        System.err.println("获得锁");
        Thread.sleep(1000 * 3);
        lock1.unlock();
        System.err.println("释放锁");

    }

    @Test
    public void subPubDemo() throws InterruptedException {
        RTopic topic = redissonClient.getTopic("msg:1");

        topic.addListener(String.class, new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence channel, String msg) {
                System.err.println(channel.toString() + ":" +msg);
            }
        });
        topic.publish("66666666666666");
        Thread.sleep(1000 * 5);

    }

}
