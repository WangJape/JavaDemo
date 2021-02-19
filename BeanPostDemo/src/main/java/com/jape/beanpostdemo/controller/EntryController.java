package com.jape.beanpostdemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jape.beanpostdemo.dtos.*;
import com.jape.beanpostdemo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class EntryController {
    @Autowired
    private ObjectMapper objectMapper;

    public static final HashMap<String, IService> busServiceMap = new HashMap<>();
    public static final HashMap<String, Class<? extends ReqBody>> busReqBodyMap = new HashMap<>();

    @PostMapping("/entry")
    public Response entry(@RequestBody String reqJsonStr) throws JsonProcessingException {
        JsonNode reqJson = objectMapper.readTree(reqJsonStr);
        JsonNode reqHeaderJson = reqJson.get("header");
        JsonNode reqBodyJson = reqJson.get("body");
        ReqHeader reqHeader = objectMapper.treeToValue(reqHeaderJson, ReqHeader.class);
        String busCode = reqHeader.getBusinessCode();//业务代码
        String serialNo = reqHeader.getSerialNo();   //请求序列号

        IService service = busServiceMap.get(busCode);
        Class<? extends ReqBody> reqBodyType = busReqBodyMap.get(busCode);

        ReqBody reqBody = objectMapper.treeToValue(reqBodyJson, reqBodyType);
        Response response = new Response();
        if (service != null) {
            RspBody rspBody = service.doBusiness(reqBody);
            response.setBody(rspBody);
        }
        RspHeader rspHeader = new RspHeader();
        rspHeader.setSerialNo(serialNo);
        rspHeader.setStatus("200");
        response.setHeader(rspHeader);
        return response;
    }

}
