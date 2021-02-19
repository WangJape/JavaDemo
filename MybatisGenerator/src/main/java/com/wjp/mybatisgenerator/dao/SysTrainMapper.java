package com.wjp.mybatisgenerator.dao;

import com.wjp.mybatisgenerator.entity.SysTrain;

public interface SysTrainMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysTrain record);

    int insertSelective(SysTrain record);

    SysTrain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysTrain record);

    int updateByPrimaryKey(SysTrain record);
}