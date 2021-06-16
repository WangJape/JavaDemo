package com.jape.inf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 处理异常的lambda方法接口(再也不用在lambda中try-catch了)
 *
 * @author Jape
 * @since 2020/12/8 18:05
 */
@FunctionalInterface
public interface ThrowingConsumer<T> extends Consumer<T> {

    @Override
    default void accept(T t) {
        try {
            acceptThrows(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void acceptThrows(T t) throws Exception;



    //Demo
    public static void main(String[] args) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            Optional.ofNullable(fileOutputStream)
                    .ifPresent((ThrowingConsumer<FileOutputStream>) FileOutputStream::close);
        }

    }
}
