package com.jape.beanpostdemo.service;

import com.jape.beanpostdemo.annos.Business;
import com.jape.beanpostdemo.dtos.*;
import com.jape.beanpostdemo.enums.BusinessEnum;
import org.springframework.stereotype.Service;

@Service
@Business(BusinessEnum.LOGOUT)
public class LogoutService implements IService {
    @Override
    public RspBody doBusiness(ReqBody reqBody) {
        return new LogoutRspBody();
    }
}
