package com.jape.demo;

import java.util.Collections;
import java.util.List;

public class ListDemo {
    public static void main(String[] args) {

        List<String> empty = Collections.emptyList();

        //jdk9
        List<String> stringList = List.of("a", "b");
        //stringList.add("c");//UnsupportedOperationException
    }
}
