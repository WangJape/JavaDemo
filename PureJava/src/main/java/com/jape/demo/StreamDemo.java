package com.jape.demo;


import com.jape.entity.User;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: Java8的Stream测试类
 * @Auther: 王建朋
 * @Date: 2020/8/22 15:23
 */
public class StreamDemo {

    public static void main(String[] args) {
        StreamDemo streamDemo = new StreamDemo();

        List<String> stringList = Arrays.asList("a", "a", "b".concat(""), "", "d", "c", "e");

        //归约
        List<String> stringListBak = stringList.stream().collect(Collectors.toList());
        String s = stringList.stream().collect(Collectors.joining(", "));

        //过滤
        List<String> stringList1 = stringList.stream().
                filter(str -> str.endsWith("a")).collect(Collectors.toList());

        //去重
        List<String> stringList2 = stringList.stream().distinct().collect(Collectors.toList());

        //迭代计算,一次次往total中增量更新
        String s1 = stringList.stream().reduce((total, item) -> total + item).orElse("");

        //去掉符合断言前的序列-jdk9
        List<String> stringList7 = stringList.stream().dropWhile(String::isEmpty).collect(Collectors.toList());
        //获取符合断言前的序列-jdk9
        List<String> stringList8 = stringList.stream().takeWhile(String::isEmpty).collect(Collectors.toList());
        //可放入Null
        long count = Stream.ofNullable(stringList).count();

        //分页,提取前n个
        List<String> stringList3 = stringList.stream().limit(3).collect(Collectors.toList());

        //跳过n个，提取后边的所有
        List<String> stringList4 = stringList.stream().skip(3).collect(Collectors.toList());

        //映射map，用于映射每个元素到对应的结果
        List<String> stringList5 = stringList.stream().
                map(str -> str.concat("_jape")).collect(Collectors.toList());

        //排序，自定义比较器的排序
        List<String> stringList6 = stringList.stream().sorted().collect(Collectors.toList());

        streamDemo.testHashMap();
        streamDemo.testListToMap();
        System.out.println();


    }

    //stream式处理Hashmap
    private void testHashMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("1", "One");
        map.put("2", "Two");
        map.put("3", "Three");
        map.put("4", "Four");
        map.put("5", "Five");

        String s = map.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("|"));
        System.out.println();
    }

    //stream式处理Hashmap
    private void testListToMap() {

        List<Object> list = new ArrayList();
        Object a = new User("a", "1", "", "", "", "");
        Object b = new User("a", "1", "", "", "", "");
        Object c = new User("c", "1", "", "", "", "");
        Object d = new User("d", "1", "", "", "", "");
        Object e = new User("e", "1", "", "", "", "");
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);

        Map<String, User> map = list.stream().map(o -> (User) o)
                .collect(Collectors.toMap(User::getUserName, Function.identity(), (value1, value2) -> value1));

        System.out.println();
    }


}
