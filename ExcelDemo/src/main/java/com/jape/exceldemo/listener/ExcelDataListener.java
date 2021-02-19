package com.jape.exceldemo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelDataListener extends AnalysisEventListener<Map<String, Object>> {

    /**
     * 这个每一条数据解析都会来调用
     */
    @Override
    public void invoke(Map<String, Object> record, AnalysisContext analysisContext) {
        System.err.println(record.entrySet().toArray());
    }

    /**
     * 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
