package com.jape.Net.NIO;

import java.nio.IntBuffer;

/**
 * Buffer底层是一个数组，一个Buffer对应一个Channel
 * 是双向的，可读可写，使用flip()切换
 */
public class BufferDemo {

    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i = 0; i < 5; i++) {
            intBuffer.put(i);
        }
        //切换读写
        intBuffer.flip();
        while (intBuffer.hasRemaining()){
            System.err.println(intBuffer.get());
        }
    }
}
