package com.jape.designMode;

public class TemplateMethod {
    public static void main(String[] args) {
        Template demo = new ImplTemplateMethod();
        demo.doSomething();
    }
}

abstract class Template{
    public void doSomething(){
        System.err.println("在模板方法前的操作");
        method();
        System.err.println("在模板方法后的操作");
    }
    abstract void method();
}

class ImplTemplateMethod extends Template {

    @Override
    void method() {
        System.err.println("具体操作延后到子类来实现");
    }
}
