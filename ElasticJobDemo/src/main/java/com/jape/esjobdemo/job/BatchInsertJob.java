package com.jape.esjobdemo.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.jape.esjobdemo.entity.User;
import com.jape.esjobdemo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.UUID;

/**
 * 批量插入Job
 *
 * @author Jape
 * @since 2020/11/14 0:28
 */
@Slf4j
public class BatchInsertJob implements SimpleJob {

    @Resource
    private UserMapper userMapper;

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info(shardingContext.getJobName());

        int successTotal = 0;
        for (int i = 0; i < 0; i++) {
            ArrayList<User> users = new ArrayList<>();
            for (int j = 0; j < 5000; j++) {
                User user = new User();
                user.setUuid(UUID.randomUUID().toString());
                user.setName("Jape" + (i * j));
                user.setAge(i);
                users.add(user);
                userMapper.insert(user);
            }
            int batchSuccessNum = userMapper.batchInsert(users);
            log.info("本批次插入条数[{}]", batchSuccessNum);
            successTotal += batchSuccessNum;
        }

        log.info("执行完成,共插入数据[{}]条", successTotal);
    }
}
