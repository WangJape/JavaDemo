package com.jape.exceldemo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.jape.exceldemo.easy.EasyUser;
import com.jape.exceldemo.easy.EasyUserListener;
import com.jape.exceldemo.easy.ExcelMapListener;
import com.jape.exceldemo.utils.PoiUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ExcelDemoApplicationTests {

    @Test
    void easyExcelReadDemo() {

        String filePath = "E:\\Downloads\\a.xlsx";

        //不写参数二的实体类，默认使用LinkedHashMap
        //方法一
        EasyExcel.read(filePath, EasyUser.class, new EasyUserListener()).sheet().headRowNumber(1).doRead();

        //方法二(细化读取sheet)
        ExcelReader excelReader = EasyExcel.read(filePath).build();
        ReadSheet sheet0 = EasyExcel.readSheet(0).registerReadListener(new ExcelMapListener()).build();
        ReadSheet sheet1 = EasyExcel.readSheet(1).headRowNumber(2).registerReadListener(new ExcelMapListener()).build();
        //这里注意 一定要把sheet0 sheet1 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
        excelReader.read(sheet1);
        //这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }

    @Test
    void easyExcelWriteDemo() {
        String filePath = "E:\\Downloads\\a.xlsx";
        List<EasyUser> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            EasyUser user = new EasyUser();
            user.setId(String.valueOf(i + 1));
            user.setName("Jape" + i);
            user.setAge(20 + i);
            user.setSex("男");
            data.add(user);
        }
        //简单使用实体
        //EasyExcel.write(filePath, EasyUser.class).sheet(0, "测试").doWrite(data);

        //不使用实体
        List<String> head0 = List.of("基本", "序号");
        List<String> head1 = List.of("基本", "姓名");
        List<List<String>> head = List.of(head0, head1);
        List<Object> row0 = List.of(0, "Jape0");
        List<Object> row1 = List.of(1, "Jape1");
        List<List<Object>> body = List.of(row0, row1);
        ExcelWriter excelWriter = EasyExcel.write(filePath).build();
        WriteSheet writeSheet0 = EasyExcel.writerSheet(0, "测试1").head(EasyUser.class).build();
        WriteSheet writeSheet1 = EasyExcel.writerSheet(1, "测试2").head(head).build();
        excelWriter.write(data, writeSheet0);
        excelWriter.write(body, writeSheet1);
        excelWriter.finish();
    }


    @Test
    void poiDemo() throws IOException, InvalidFormatException {
        String filePath = "G:\\Work\\体检\\2020体检套餐.xlsx";
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);

        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        //方法1
        sheet.forEach(row -> {
            row.forEach(cell -> {
                String value = cell.getStringCellValue();
                System.err.print(value);
                System.err.print(" ");
            });
            System.err.println();
        });

        //方法2
        Row row = sheet.getRow(0);
        short lastCellNum = row.getLastCellNum();
        System.err.println(lastCellNum);
        Cell cell = row.getCell(0);
        String stringCellValue = cell.getStringCellValue();
        System.err.println(stringCellValue);

        workbook.close();
        fileInputStream.close();

    }

    @Test
    void poiUtilDemo() {
        String filePath = "G:\\Work\\体检\\2020体检套餐.xlsx";
        List<List<String>> lists = PoiUtil.readExcelToList(filePath, 0);

        System.err.println(0);

    }


}
