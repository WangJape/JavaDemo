package com.jape.impl;

import com.jape.IService;

/**
 * 服务提供者1 实现类
 *
 * @author Jape
 * @since 2021/4/13 14:38
 */
public class Provider2Impl implements IService {
    public void doService() {
        System.err.println("我是服务提供者2");
    }
}
