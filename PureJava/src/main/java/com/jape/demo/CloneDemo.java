package com.jape.demo;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * 浅拷贝demo
 *
 * @author Jape
 * @since 2020/12/29 11:34
 */
public class CloneDemo {
    public static void main(String[] args) {

        CA ca = new CA();
        CA clone = ca.clone();
        System.err.println(clone);
    }
}

@Data
class CA implements Cloneable {

    private Integer a = 10;
    private LocalDate date = LocalDate.now();
    private CAF caf = new CAF();//需要clone
    private ArrayList<String> list = new ArrayList<>();//需要clone

    @Override
    protected CA clone() {
        CA clone = null;
        try {
            clone = (CA) super.clone();
            clone.caf = caf.clone();
            clone.list = (ArrayList<String>) list.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}

@Data
class CAF implements Cloneable {
    private String f = "f";

    @Override
    protected CAF clone() {
        CAF caf = null;
        try {
            caf = (CAF) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return caf;
    }
}
