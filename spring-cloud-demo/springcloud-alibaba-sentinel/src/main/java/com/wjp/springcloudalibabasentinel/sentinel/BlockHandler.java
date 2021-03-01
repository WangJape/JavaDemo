package com.wjp.springcloudalibabasentinel.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class BlockHandler {

    /**
     * 返回类型必须与请求方法返回类型一致
     */
    public static String handler429(BlockException e){
        return "全局异常处理类返回信息429:" + e.getMessage();
    }

    public static String handler401(BlockException e){
        return "全局异常处理类返回信息401:" + e.getMessage();
    }

}
