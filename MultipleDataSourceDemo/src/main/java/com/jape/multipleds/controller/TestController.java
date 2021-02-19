package com.jape.multipleds.controller;

import com.jape.multipleds.bean.BciAccmgn;
import com.jape.multipleds.bean.TblBeAccInfoCopy;
import com.jape.multipleds.dao.ds1.BciAccmgnMapper;
import com.jape.multipleds.dao.ds2.TblBeAccInfoCopyMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Auther: 王建朋
 * @Date: 2020/8/19 15:07
 */
@RestController
public class TestController {

    @Resource
    private BciAccmgnMapper bciAccmgnMapper;
    @Resource
    private TblBeAccInfoCopyMapper tblBeAccInfoCopyMapper;


    @GetMapping("/test1/{user}")
    public String test1(@PathVariable String user){
        return user;
    }

    @GetMapping("/queryDs1")
    public List<BciAccmgn> queryDs1(){
        List<BciAccmgn> bciAccmgns = bciAccmgnMapper.queryAll();
        return bciAccmgns;
    }

    @GetMapping("/queryDs2")
    public List<TblBeAccInfoCopy> queryDs2(){
        List<TblBeAccInfoCopy> tblBeAccInfoCopies = tblBeAccInfoCopyMapper.queryAll();
        return tblBeAccInfoCopies;
    }

}
