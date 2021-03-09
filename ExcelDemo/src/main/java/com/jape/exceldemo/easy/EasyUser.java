package com.jape.exceldemo.easy;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class EasyUser {

    @ExcelProperty("主键")
    private String id;
    @ExcelProperty("名称")
    private String name;
    @ExcelProperty("年龄")
    private Integer age;
    @ExcelProperty("性别")
    private String sex;

}
