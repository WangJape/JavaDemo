package com.jape.exceldemo.easy;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelMapListener extends AnalysisEventListener<Map<Object, Object>> {

    /**
     * 这个每一条数据解析都会来调用
     */
    @Override
    public void invoke(Map<Object, Object> record, AnalysisContext analysisContext) {
        record.forEach((k, v) -> {
            System.err.print(v + "\t");
        });
        System.err.println();
    }

    /**
     * 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
