package com.jape.beanpostdemo.dtos;

import lombok.Data;

@Data
@Deprecated
public class Request {
    private ReqHeader header;
    private ReqBody body;
}
