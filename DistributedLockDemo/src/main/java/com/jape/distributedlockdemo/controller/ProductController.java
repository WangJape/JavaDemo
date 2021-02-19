package com.jape.distributedlockdemo.controller;

import com.jape.distributedlockdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/buy/{productNo}")
    @ResponseBody
    public String bugProduct(@PathVariable String productNo) {
        Integer integer = productService.reduceInventory2(productNo, 1);
        return integer.toString();
    }


}
