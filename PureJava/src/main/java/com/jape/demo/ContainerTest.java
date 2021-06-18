package com.jape.demo;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 容器基本测试，测试是否能放入null
 */
public class ContainerTest {

    public static void main(String[] args) {

        //可为null
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(null);

        //可为null
        LinkedList<Object> linkedList = new LinkedList<>();
        linkedList.add(null);

        //可为null
        HashSet<Object> hashSet = new HashSet<>();
        hashSet.add(null);

        //均可为空
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put(null, null);

        //均可为空
        LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(null, null);

        //均不能为null
        Hashtable<Object, Object> hashtable = new Hashtable<>();
        hashtable.put("", "");

        //均不能为null
        ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("", "");

        //key不能为null
        TreeMap<Object, Object> treeMap = new TreeMap<>();
        treeMap.put("", null);

        //不能为null
        TreeSet<Object> treeSet = new TreeSet<>();
        treeSet.add("");

        //顺序从右至左，内部为long数组，一个long8Byte,64位
        BitSet bitSet = new BitSet();
        bitSet.set(0, 5);//11111-31
        bitSet.clear(1,3);//11001-25

    }
}
