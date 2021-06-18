package com.jape.designMode;

/**
 * 门面模式
 * 为复杂的底层业务增加一个门面层，用户只用调用门面层方法，不用再知道底层复杂的逻辑
 *
 * Tomcat中的RequestFacade实现HttpServletRequest接口，调用Request对象方法就是门面模式
 *  RequestFacade作为Request的门面，内部包含Request对象
 *  ResponseFacade作为Response的门面，内部包含Response对象
 *  StandardSessionFacade作为HttpSession的门面，内部包含HttpSession对象
 *  ApplicationContextFacade作为ApplicationContext的门面，内部包含ApplicaitonContext对象
 */
public class Facade {
    public static void main(String[] args) {
        ModuleFacade moduleFacade = new ModuleFacade();
        moduleFacade.service();

    }
}

class ModuleFacade {
    //示意方法，满足客户端需要的功能
    public void service(){
        ModuleA a = new ModuleA();
        a.serviceA();
        ModuleB b = new ModuleB();
        b.serviceB();
        ModuleC c = new ModuleC();
        c.serviceC();
    }
}

class ModuleA {
    //示意方法
    public void serviceA(){
        System.out.println("调用ModuleA中的serviceA方法");
    }
}

class ModuleB {
    //示意方法
    public void serviceB(){
        System.out.println("调用ModuleB中的serviceB方法");
    }
}

class ModuleC {
    //示意方法
    public void serviceC(){
        System.out.println("调用ModuleC中的serviceC方法");
    }
}