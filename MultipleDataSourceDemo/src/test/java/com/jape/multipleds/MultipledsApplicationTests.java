package com.jape.multipleds;

import com.jape.multipleds.bean.BciAccmgn;
import com.jape.multipleds.bean.TblBeAccInfoCopy;
import com.jape.multipleds.dao.ds1.BciAccmgnMapper;
import com.jape.multipleds.dao.ds2.TblBeAccInfoCopyMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class MultipledsApplicationTests {

    @Resource
    private BciAccmgnMapper bciAccmgnMapper;
    @Resource
    private TblBeAccInfoCopyMapper tblBeAccInfoCopyMapper;

    @Test
    void queryDs1() {
        List<BciAccmgn> bciAccmgns = bciAccmgnMapper.queryAll();
        System.out.println(bciAccmgns);
    }

    @Test
    void queryDs2() {
        List<TblBeAccInfoCopy> tblBeAccInfoCopies = tblBeAccInfoCopyMapper.queryAll();
        System.out.println(tblBeAccInfoCopies);
    }
}
