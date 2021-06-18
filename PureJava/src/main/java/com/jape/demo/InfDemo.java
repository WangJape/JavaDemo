package com.jape.demo;

interface Animal {
    public void cry();

    public String getName();
}

class Dog implements Animal {
    public void cry() {
        System.out.println("汪汪");
    }

    public String getName() {
        return "狗";
    }
}

class Wjp {
    public void action(Animal sq) {
        System.out.println("现在是" + sq.getName() + "在叫");
        sq.cry();
    }

}

public class InfDemo {
    public static void main(String args[]) {
        Wjp wjp = new Wjp();
        wjp.action(new Dog());
    }
}