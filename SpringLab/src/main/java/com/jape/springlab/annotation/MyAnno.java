package com.jape.springlab.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Target(ElementType.TYPE) 指定注解的类型属性
 * ElementType.TYPE 修饰"类、接口（包括注释类型）或枚举声明"的注解
 * ElementType.ANNOTATION_TYPE 注解只能被用来标注 "Annotation 类型"
 * @Retention(RetentionPolicy.RUNTIME) 指定注解的策略属性
 * RetentionPolicy.RUNTIME 编译器会将该注解信息保留在.class文件中，并且能被虚拟机读取，运行时可以通过反射访问
 * @Inherited 所标注的Annotation将具有继承性。
 * @Repeatable 可重复的, 可以同时作用一个对象多次，但是每次作用注解又可以代表不同的含义
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnno {

    String name() default "anonymous";

    String[] girlFriends();

}
