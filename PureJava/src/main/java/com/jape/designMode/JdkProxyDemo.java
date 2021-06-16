package com.jape.designMode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyDemo {

    public static void main(String[] args) {
        //维护一个目标对象
        Object target = new MyDao();

        Object proxyObj = Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("JDK代理调用开始");
                        Object invoke = method.invoke(target, args);
                        System.out.println("JDK代理调用结束");
                        return invoke;
                    }
                });

        IDao proxy = (IDao)proxyObj;
        proxy.query();
        proxy.update();

    }
}

interface IDao {
    void query();

    void update();
}

class MyDao implements IDao {
    @Override
    public void query() {
        System.out.println("我的查询");
    }

    @Override
    public void update() {
        System.out.println("我的更新");
    }


}