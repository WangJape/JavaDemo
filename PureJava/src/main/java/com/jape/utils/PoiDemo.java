package com.jape.utils;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * excel解析
 * @author Jape 2020/11/11 16:22
 */
public class PoiDemo {
    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\Jape\\Desktop\\民生银行账户管理决策系统BOM设计文档Final_V1.3.xlsx";
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);

        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheetAt(3);

        String sheetName = sheet.getSheetName();
        System.err.println(sheetName);

        LinkedHashMap<String, Object> varPathMap = new LinkedHashMap<>();

        String[] xPath = new String[7];
        xPath[0] = sheetName;

        int totalRow = sheet.getPhysicalNumberOfRows();
        int rIndex = 1;
        while (rIndex < totalRow) {
            Row row = sheet.getRow(rIndex);
            for (int cIndex = 1; cIndex < 7; cIndex++) {
                Cell cell = row.getCell(cIndex);
                String cellValue = getCellValue(cell);
                if(!"".equals(cellValue)){
                    xPath[cIndex] = cellValue;
                }
                if(cIndex == 6 && !"".equals(cellValue)){
                    varPathMap.put(cellValue, xPath.toString());
                }
            }
            rIndex++;
        }
        System.err.println(varPathMap);
    }

    /**
     * 获取单元格各类型值，返回字符串
     */
    private static String getCellValue(Cell cell) {
        //判断是否为null或空串
        if (cell == null || "".equals(cell.toString().trim())) {
            return "";
        }
        String cellValue = "";

        // 以下是判断数据的类型
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case NUMERIC: // 数字
                if (HSSFDateUtil.isCellDateFormatted(cell)) {// 判断是否为日期类型
                    Date date = cell.getDateCellValue();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    cellValue = format.format(date);
                } else {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case STRING: // 字符串
                cellValue = cell.getStringCellValue();
                break;
            case BOOLEAN: // Boolean
                cellValue = cell.getBooleanCellValue() + "";
                break;
            case FORMULA: // 公式
                cellValue = cell.getCellFormula() + "";
                break;
            case _NONE:
            case BLANK: // 空值
                cellValue = "";
                break;
            case ERROR: // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

}












