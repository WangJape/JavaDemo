package com.jape.beanpostdemo.dtos;

import lombok.Data;

@Data
public class Response {
    private RspHeader header;
    private RspBody body;
}
