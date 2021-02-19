package com.jape.esjobdemo.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.jape.esjobdemo.entity.User;
import com.jape.esjobdemo.mapper.UserMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * 写入文件job
 *
 * @author Jape
 * @since 2020/11/15 20:25
 */
@Slf4j
public class FileWriterJob implements SimpleJob {

    @Resource
    private UserMapper userMapper;

    @SneakyThrows
    @Override
    public void execute(ShardingContext shardingContext) {
        log.info(shardingContext.getJobName());

        String filePath = "D:\\file\\";
        String fileName = "20201115001.txt";

        File path = new File(filePath);
        if (!path.exists()) {
            path.mkdir();
        }
        File file = new File(filePath + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        int writeNum = 0;
        String preId = null;
        int pageSize = 50000;
        List<User> userList;
        do {
            userList = userMapper.selectPageByPreId(preId, pageSize);
            int currRecordNum = userList.size();
            if (currRecordNum == 0) {
                break;
            }
            for (User user : userList) {
                bufferedWriter.write(user.getUuid());
                bufferedWriter.write("|");
                bufferedWriter.write(user.getName());
                bufferedWriter.write("|");
                bufferedWriter.write(user.getAge().toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            log.info("写入记录【{}】条", currRecordNum);
            writeNum += currRecordNum;
            preId = userList.get(currRecordNum - 1).getUuid();
        } while (userList.size() == pageSize);

        bufferedWriter.close();
        fileWriter.close();
        log.info("写入完成，共【{}】条", writeNum);
    }
}
