package com.jape.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeDemo {
    public static void main(String[] args) {
        //jdk8
        String ldtStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
        System.err.println(ldtStr);

        LocalDate date = LocalDate.of(2021, 3, 1);
        System.err.println(date.lengthOfMonth());//31
        LocalDate yesterday = date.minusDays(1);
        System.err.println(yesterday.lengthOfMonth());//28
        for (int i = 1; i <= 28; i++) {
            LocalDate date1 = yesterday.withDayOfMonth(i);
            System.err.println(date1);
        }
    }
}
