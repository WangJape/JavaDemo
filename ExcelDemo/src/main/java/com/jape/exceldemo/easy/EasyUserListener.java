package com.jape.exceldemo.easy;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

public class EasyUserListener extends AnalysisEventListener<EasyUser> {

    @Override
    public void invoke(EasyUser easyUser, AnalysisContext analysisContext) {
        System.err.println(easyUser);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
