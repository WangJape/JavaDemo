package com.jape.distributedlockdemo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ZkClientTest {

    @Autowired
    private CuratorFramework zkClient;


    @Test
    public void create1() throws Exception {
        String s = zkClient.create()
                .creatingParentsIfNeeded()
                .forPath("/lock_product");
        System.err.println(s);
    }

    @Test
    public void create2() throws Exception {
        String s = zkClient.create()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath("/lock_product/");
        System.err.println(s);

        List<String> stringList = this.zkClient.getChildren().forPath("/lock_product");
        stringList.forEach(System.out::println);
        //进程死，临时节点就没了
        //Thread.sleep(100 * 1000);
    }


    @Test
    public void listener() throws Exception {
        CuratorCache curatorCache = CuratorCache.build(zkClient, "/lock_product/test");
        CuratorCacheListener listener = CuratorCacheListener.builder()
                .forChanges(new CuratorCacheListenerBuilder.ChangeListener() {
                    @Override
                    public void event(ChildData oldNode, ChildData node) {
                        System.out.println("old data: " + new String(node.getData()) + " new data:" + new String(node.getData()));
                    }
                })
                .forCreates(node -> {
                    System.out.println("节点被创建：" + node.getPath());
                })
                .forDeletes(node -> {
                    System.out.println("节点被删除：" + node.getPath());
                })
                .build();
        curatorCache.listenable().addListener(listener);
        curatorCache.start();
        Thread.sleep(100 * 1000);

    }


}
