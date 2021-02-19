package com.jape.exceldemo.utils;

import com.sun.istack.internal.NotNull;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PoiUtil {


    /**
     * 读取指定Sheet的内容
     *
     * @param filePath filePath 文件全路径
     * @param sheetNo  sheet序号,从0开始
     */
    public static List<List<String>> readExcelToList(@NotNull String filePath, @NotNull Integer sheetNo) {
        File file = new File(filePath);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<List<String>> sheetData = PoiUtil.readExcelToList(fileInputStream, sheetNo);
        try {
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheetData;
    }

    /**
     * 读取指定Sheet的内容
     *
     * @param inputStream 输入流
     * @param sheetNo     sheet序号,从0开始
     */
    public static List<List<String>> readExcelToList(@NotNull InputStream inputStream, @NotNull Integer sheetNo) {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        }
        Sheet sheet = workbook.getSheetAt(sheetNo);
        List<List<String>> sheetData = new ArrayList<>();

        //TODO:forEach不靠谱，还得是自己for
        sheet.forEach(row -> {
            ArrayList<String> rowData = new ArrayList<>();
            row.forEach(cell -> {
                String value = PoiUtil.getCellValue(cell);
                rowData.add(value);
            });
            sheetData.add(rowData);
        });
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheetData;
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
        CellType cellTypeEnum = cell.getCellTypeEnum();
        switch (cellTypeEnum) {
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

    public static void main(String[] args) {
        String filePath = "G:\\Work\\体检\\2020体检套餐.xlsx";
        List<List<String>> lists = PoiUtil.readExcelToList(filePath, 0);

        System.err.println(0);
    }

}
