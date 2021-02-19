package com.jape.distributedlockdemo.service;

import com.jape.distributedlockdemo.lock.ZkLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class ProductService implements InitializingBean {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private CuratorFramework zkClient;

    @Autowired
    private RedissonClient redissonClient;
    private RLock rLock;

    @Override
    public void afterPropertiesSet() throws Exception {
        rLock = redissonClient.getLock("product:lock");
    }

    public Integer reduceInventory(String productNo, int amount) {
        String key = "product:".concat(productNo);

        rLock.lock();
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        String name = (String) entries.get("name");
        int inventory = Integer.parseInt((String) entries.get("inventory"));
        log.info("产品编号:[{}],产品名称:[{}]，剩余库存量:[{}]", key, name, inventory);
        if (inventory != 0) {
            inventory -= amount;
            redisTemplate.opsForHash().put(key, "inventory", String.valueOf(inventory));
        }
        rLock.unlock();

        return inventory;
    }

    public Integer reduceInventory2(String productNo, int amount) {
        String key = "product:".concat(productNo);

        ZkLock lock = new ZkLock(zkClient, "product");
        lock.lock();
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        String name = (String) entries.get("name");
        int inventory = Integer.parseInt((String) entries.get("inventory"));
        log.info("产品编号:[{}],产品名称:[{}]，剩余库存量:[{}]", key, name, inventory);
        if (inventory != 0) {
            inventory -= amount;
            redisTemplate.opsForHash().put(key, "inventory", String.valueOf(inventory));
        }
        lock.unlock();

        return inventory;
    }


}
