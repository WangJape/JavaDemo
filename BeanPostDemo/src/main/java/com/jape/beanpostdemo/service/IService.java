package com.jape.beanpostdemo.service;

import com.jape.beanpostdemo.dtos.ReqBody;
import com.jape.beanpostdemo.dtos.RspBody;

public interface IService {

    RspBody doBusiness(ReqBody reqBody);

}
