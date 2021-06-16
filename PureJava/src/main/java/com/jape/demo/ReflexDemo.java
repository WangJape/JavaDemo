package com.jape.demo;

import com.jape.entity.User;

public class ReflexDemo {

    public static void main(String[] args) {
        User user = new User("jape", "男", "", "", "", "");

        Class<?> clazz = User.class;
        String canonicalName = clazz.getCanonicalName();
        String typeName = clazz.getTypeName();
        String name = clazz.getName();
        String simpleName = clazz.getSimpleName();
        System.out.println(canonicalName);
        System.out.println(typeName);
        System.out.println(name);
        System.out.println(simpleName);
    }
}
