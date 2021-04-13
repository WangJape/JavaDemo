package com.jape;

import java.util.ServiceLoader;

/**
 * 主类
 *
 * @author Jape
 * @since 2021/4/13 14:43
 */
public class Application {

    public static void main(String[] args) {
        ServiceLoader<IService> services = ServiceLoader.load(IService.class);
        services.forEach(IService::doService);
    }
}
