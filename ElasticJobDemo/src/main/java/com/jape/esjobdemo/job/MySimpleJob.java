package com.jape.esjobdemo.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.SneakyThrows;

/**
 * Job示例
 *
 * @author Jape
 * @since 2020/11/14 0:05
 */
public class MySimpleJob implements SimpleJob {

    @SneakyThrows
    @Override
    public void execute(ShardingContext shardingContext) {
        System.err.println(shardingContext.getJobName());
        Thread.sleep(5000);
        System.err.println("执行完成");
    }
}
