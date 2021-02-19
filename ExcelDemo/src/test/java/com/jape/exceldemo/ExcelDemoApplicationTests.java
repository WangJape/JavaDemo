package com.jape.exceldemo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.jape.exceldemo.listener.ExcelDataListener;
import com.jape.exceldemo.utils.PoiUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@SpringBootTest
class ExcelDemoApplicationTests {

    @Test
    void easyExcelDemo() {

        String filePath = "G:\\Work\\体检\\2020体检套餐.xlsx";

        //方法一
        EasyExcel.read(filePath, new ExcelDataListener()).sheet().doRead();

        //方法二
        ExcelReader excelReader = EasyExcel.read(filePath, new ExcelDataListener()).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        excelReader.finish();
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
