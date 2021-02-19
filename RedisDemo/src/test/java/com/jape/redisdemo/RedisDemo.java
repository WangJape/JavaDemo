package com.jape.redisdemo;

import com.jape.redisdemo.entity.Account;
import com.jape.redisdemo.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@SpringBootTest
class RedisDemo {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void setHashTest() {
        Account account1 = new Account(1, "15075291954", "Jape", "15075291954", "", "", LocalDateTime.now());
        Account account2 = new Account(2, "19831125201", "JapeBetter", "19831125201", "", "", LocalDateTime.now());
        String key = "account:" + account2.getId();
        boolean result = redisUtil.setHashObj(key, account2);
        System.out.println(result);
    }

    @Test
    void getHashTest() {
        String key = "account:1";
        Object obj = redisUtil.getHashObj(key, Account.class);
        System.out.println(obj);
    }
    //****************************************************************************************************

    @Test
    void setSetTest() {
        long addNum1 = redisUtil.addToSet("set:1", "1", "2");
        long addNum21 = redisUtil.addToSet("set:2", "aaa", "bbb", "xyz");
        long addNum22 = redisUtil.addToSet("set:2", "aaa", "bbb", "ccc", "aaa");
        System.err.println(addNum22);
    }

    @Test
    void getSetTest() {
        long setSize = redisUtil.getSetSize("set:2");
        System.err.println(setSize);
        redisUtil.removeFromSet("set:2", "aaa", "ccc");
        Set<Object> set = redisUtil.getSet("set:2");
        System.err.println(set);
        Set<Object> set1Diff = redisUtil.getSetsDiff("set:1", "set:2");
        Set<Object> set2Diff = redisUtil.getSetsDiff("set:2", "set:1");
        System.err.println(set1Diff);
        System.err.println(set2Diff);
    }
    //***********************************************************************************************

    @Test
    void setZSetTest() {
        redisUtil.addToZSet("zset:2", "a", 55000);
        redisUtil.addToZSet("zset:2", "b", 50000);
        redisUtil.addToZSet("zset:2", "c", 90000);
        redisUtil.addToZSet("zset:2", "d", 30000);
        redisUtil.addToZSet("zset:2", "e", 10000);
        redisUtil.addToZSet("zset:2", "e", 1000000.63);
    }

    @Test
    void getZSetTest() {
        Set<Object> zSetOfScoreRange = redisUtil.getZSetByScoreRange("zset:1", 0, 100);
        Set<Object> zSetOfIndexRange = redisUtil.getZSetByIndexRange("zset:2", 0, 100);
        System.err.println(zSetOfScoreRange);
        System.err.println(zSetOfIndexRange);
    }

    //***********************************************************************************************
    @Test
    void setListTest() {
        redisUtil.addToListLeft("list:2", "aaa");
        redisUtil.addToListRight("list:2", "bbb");
        redisUtil.addToListLeft("list:2", "ccc");
    }

    @Test
    void getListTest() {
        List<Object> objectList = redisUtil.getListByIndexRange("list:1", 0, -1);
        System.err.println(objectList);

        Object leftObj = redisUtil.moveOutFromListLeft("list:1");
        Object rightObj = redisUtil.moveOutFromListRight("list:1");
        System.err.println(leftObj);
        System.err.println(rightObj);

    }
}
