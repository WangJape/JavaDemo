package com.jape.designMode;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxyDemo {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Teacher.class);
        enhancer.setCallback(new Monitor());

        Teacher teacher = (Teacher) enhancer.create();
        teacher.leaveHomework();
    }
}

class Teacher {
    public void leaveHomework() {
        System.out.println("老师留作业");
    }
}

class Monitor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("Cglib代理调用开始");
        Object result = methodProxy.invokeSuper(o, args);
        System.out.println("Cglib代理调用结束");
        return result;
    }
}
