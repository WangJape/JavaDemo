package com.jape.distributedlockdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@SpringBootTest
class ResidTests {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void contextLoads() {
    }

    /**
     * 值得操作
     */
    @Test
    public void setValue() {
        redisTemplate.boundValueOps("name").set("redis");
    }

    @Test
    public void getValue() {
        String str = (String) redisTemplate.boundValueOps("name").get();
        System.out.println(str);
        Assert.notNull(str, "");
    }

    /**
     * set类型的操作
     */
    @Test
    public void setSetValue() {
        redisTemplate.boundSetOps("nameset").add("曹操");
        redisTemplate.boundSetOps("nameset").add("孙权");
        redisTemplate.boundSetOps("nameset").add("刘备");
    }

    @Test
    public void getSetValue() {
        Set nameset = redisTemplate.boundSetOps("nameset").members();
        System.out.println(nameset);//[刘备, 孙权, 曹操]
    }

    //删除集合中的某个元素
    @Test
    public void deleteSetValue() {
        Long remove = redisTemplate.boundSetOps("nameset").remove("刘备");
        System.out.println(remove);
    }

    //删除整个集合
    @Test
    public void deleteSet() {
        Boolean nameset = redisTemplate.delete("nameset");
        System.out.println(nameset);
    }

    /**
     * List类型操作
     */
    //右压栈：后添加的对象排在后边
    @Test
    public void setListValue1() {
        redisTemplate.boundListOps("namelist1").rightPush("刘备");
        redisTemplate.boundListOps("namelist1").rightPush("关羽");
        redisTemplate.boundListOps("namelist1").rightPush("张飞");
    }

    @Test
    public void getListValue1() {
        List list = redisTemplate.boundListOps("namelist1").range(0, -1);
        System.out.println(list);//[刘备, 关羽, 张飞]
    }

    //左压栈：后添加的对象排在前边
    @Test
    public void setListValue2() {
        redisTemplate.boundListOps("namelist2").leftPush("刘备");
        redisTemplate.boundListOps("namelist2").leftPush("关羽");
        redisTemplate.boundListOps("namelist2").leftPush("张飞");
    }

    @Test
    public void getListValue2() {
        List list = redisTemplate.boundListOps("namelist2").range(0, -1);
        System.out.println(list);//[张飞, 关羽, 刘备]
    }

    //查询集合某个元素
    @Test
    public void searchListByIndex() {
        String s = (String) redisTemplate.boundListOps("namelist1").index(1);
        System.out.println(s);//关羽
    }

    //移除集合某个元素
    @Test
    public void removeListByIndex() {
        redisTemplate.boundListOps("namelist1").remove(1, "关羽");
    }

    /**
     * Hash类型操作
     */
    @Test
    public void setHashValue() {
        redisTemplate.boundHashOps("namehash").put("a", "唐僧");
        redisTemplate.boundHashOps("namehash").put("b", "悟空");
        redisTemplate.boundHashOps("namehash").put("c", "八戒");
        redisTemplate.boundHashOps("namehash").put("d", "沙僧");
    }

    @Test
    public void getHash() {
        //提取所有的KEY
        Set s = redisTemplate.boundHashOps("namehash").keys();
        System.out.println(s);//[a, b, c, d]
        //提取所有的值
        List values = redisTemplate.boundHashOps("namehash").values();
        System.out.println(values);//[唐僧, 悟空, 八戒, 沙僧]
        //根据KEY提取值
        String str = (String) redisTemplate.boundHashOps("namehash").get("b");
        System.out.println(str);//悟空
    }

    //根据KEY移除值
    @Test
    public void removeHashByKey() {
        redisTemplate.boundHashOps("namehash").delete("c");
    }

}
