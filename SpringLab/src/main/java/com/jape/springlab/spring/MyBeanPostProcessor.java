package com.jape.springlab.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * BeanPostProcessor后置处理器，每个Bean对象在实例化和依赖注入完毕后，在显示调用初始化方法的前后添加我们自己的逻辑。注意是Bean实例化完毕后及依赖注入完成后触发的。
 * Ordered定义执行优先级，非必要
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor, Ordered {

    /**
     * 实例化、依赖注入完毕，在调用显示的初始化之前完成一些定制的初始化任务
     * 初始化即(例如：配置文件中bean标签添加init-method属性指定Java类中初始化方法、@PostConstruct注解指定初始化方法，Java类实现InitailztingBean接口)
     * 注意：方法返回值不能为null
     * 如果返回null那么在后续初始化方法将报空指针异常或者通过getBean()方法获取不到bena实例对象
     * 因为后置处理器从Spring IoC容器中取出bean实例对象没有再次放回IoC容器中
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 实例化、依赖注入、初始化完毕时执行
     * 注意：方法返回值不能为null
     * 如果返回null那么在后续初始化方法将报空指针异常或者通过getBean()方法获取不到bena实例对象
     * 因为后置处理器从Spring IoC容器中取出bean实例对象没有再次放回IoC容器中
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.err.println("Bean初始化完成：" + beanName + "，实例：" + bean);

        //测试
        Class<?> aClass = bean.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();
        //Class<?>[] parameterTypes = declaredMethods[0].getParameterTypes();

        return bean;
    }

    /**
     * Ordered接口重写方法，重写此方法以实现存在多个BeanPostProcessor时的执行优先级
     * 默认值为 0，优先级最高。值越大优先级越低
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
