package com.jape.demo;

import com.jape.entity.User;

import java.util.Optional;


/**
 * null值测试
 *
 * @author Jape
 * @since 2020/11/24 10:07
 */
public class OptionalDemo {

    public static void main(String[] args) {
        //确定不为null，用of，不确定，用ofNullable
        String leader1 = Optional.of(new User())
                .get().getLeader();

        User user = null;
        //ifPresent(),如果Optional实例有值则为其调用consumer，否则不做处理
        //ifPresentOrElse(),有值则执行，没有则执行后边的lambda
        Optional.ofNullable(user)
                .ifPresent(user1 -> user1.setLeader("单手狗"));
        Optional.ofNullable(user)
                .ifPresentOrElse(user1 -> user1.setLeader("单手狗"), () -> System.err.println("单身狗"));

        user = new User("Jape", "男", null, "", "", "");
        //orElse(other),为null直接返回other
        //orElseGet(()->{}),为null执行方法获取返回值
        //orElseThrow(new Exception()),为null抛出指定异常
        String leader = Optional.ofNullable(user)
                .map(User::getLeader)
                .orElse("我是你爸爸");
        System.err.println(leader);
    }
}
