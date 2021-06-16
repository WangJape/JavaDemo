package com.jape.demo;

/**
 * 异常测试
 *
 * @author Jape
 * @since 2020/11/17 14:41
 */
public class ExceptionDemo {
    public static void main(String[] args) {
        A a = new B();
        try {
            a.Test(6);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

interface A {
    <T> void Test(T arg);
}

class B implements A {

    @Override
    public <T> void Test(T arg) {
        if (arg instanceof Integer) {
            throw new MyException("6666");
        }
    }
}

class MyException extends RuntimeException {
    MyException(String msg) {
        super(msg);
    }
}
