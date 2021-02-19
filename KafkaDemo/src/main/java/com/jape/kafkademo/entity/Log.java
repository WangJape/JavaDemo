package com.jape.kafkademo.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Log implements Serializable {

    private String ip;
    private String reqPath;
    private LocalDate date;
    private LocalTime time;
    private Long interval;

}
