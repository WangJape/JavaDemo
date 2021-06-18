package com.jape.demo;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * UUID测试demo
 *
 * @author Jape
 * @since 2020/12/29 16:18
 */
public class UuidDemo {

    public static String getId() {
        String uuidStr = UUID.randomUUID().toString();
        String simpleUuid = uuidStr.replaceAll("-|_", "");
        return simpleUuid;
    }

    //uuid不可能重复
    public static void main(String[] args) {

        int threadNum = 2;
        ExecutorService threadPool = Executors.newFixedThreadPool(threadNum);

        Set<Object> set = Collections.newSetFromMap(new ConcurrentHashMap<>());
        for (int i = 0; i < threadNum; i++) {
            threadPool.execute(() -> {
                for (int j = 0; j < 100000000; j++) {
                    String uuid = getId();
                    if (!set.add(uuid)) {
                        System.err.println("重复" + uuid);
                        threadPool.shutdown();
                        break;
                    }
                    if (j % 10000 == 0) {
                        System.out.println(Thread.currentThread().getName() + "->" + j);
                    }
                }
            });
        }
    }

}
