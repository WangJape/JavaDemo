package com.jape.beanpostdemo.spring;

import com.jape.beanpostdemo.annos.Business;
import com.jape.beanpostdemo.controller.EntryController;
import com.jape.beanpostdemo.enums.BusinessEnum;
import com.jape.beanpostdemo.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BusinessBeanPostProcess implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        Business busAnn = beanClass.getAnnotation(Business.class);
        if (busAnn != null) {
            BusinessEnum busEnum = busAnn.value();
            EntryController.busServiceMap.put(busEnum.getCode(), (IService) bean);
            EntryController.busReqBodyMap.put(busEnum.getCode(), busEnum.getReqBodyType());
            log.info("扫描到业务服务类[{}],业务代码[{}]", busEnum.getDesc(), busEnum.getCode());
        }
        return bean;
    }
}
