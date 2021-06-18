package com.jape.demo;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Jape
 * @since 2021/3/9 10:45
 */
public class BigDecimalDemo {

    public static void main(String[] args) {

        BigDecimal decimal = new BigDecimal(5000.00);
        BigDecimal decimal0 = decimal.setScale(2, RoundingMode.DOWN);
        System.err.println(decimal0.toPlainString());

        BigDecimal decimal1 = new BigDecimal("1.0");
        BigDecimal decimal2 = new BigDecimal("1.00");
        System.err.println(decimal1.equals(decimal2));//false 字符串转成的，精度不同

    }
}
