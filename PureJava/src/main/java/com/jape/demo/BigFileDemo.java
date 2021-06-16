package com.jape.demo;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class BigFileDemo {

    public static void main(String[] args) throws InterruptedException {

        String basePath = System.getProperty("user.dir");
        String relativePath = File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator;
        String fileName = "a.txt";

        String path = basePath + relativePath + fileName;
        System.err.println(path);

        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (int i = 0, j = 0; i < 36000; i++, j++) {
                bufferedWriter.write(String.valueOf(i));
                bufferedWriter.write(" ");
                if (j == 10) {
                    j = 0;
                    bufferedWriter.flush();
                    bufferedWriter.newLine();
                }
                Thread.sleep(100);
                System.err.println(i + "->" + j);
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 分段读
     */
    public static void segRead(String filePath) throws Exception {
        //"G:\\Develop\\Jape\\src\\main\\resources\\static\\big.txt";
        RandomAccessFile accessFile = new RandomAccessFile(filePath, "r");
        long fileLen = accessFile.length();
        accessFile.close();

        int seg = 10;
        long segSize = 1 << 20;//1024 * 1024

        Map<String, Integer> statistics = new ConcurrentHashMap<>(128);
        ExecutorService threadPool = Executors.newFixedThreadPool(seg);
        CountDownLatch latch = new CountDownLatch(seg);

        for (int i = 0; i < seg; i++) {
            int currSeg = i;
            threadPool.execute(() -> {
                System.err.println(Thread.currentThread().getName() + ":从此分片开始读取" + currSeg);
                try {
                    RandomAccessFile segAccess = new RandomAccessFile(filePath, "r");
                    segAccess.seek(currSeg << 20 << 3);
                    for (long j = 0; j < segSize; j++) {
                        String currNum = segAccess.readLine();
                        Integer currNumCount = statistics.get(currNum);
                        if (currNumCount == null) {
                            statistics.put(currNum, 1);
                        } else {
                            currNumCount++;
                            statistics.put(currNum, currNumCount);
                        }
                    }
                    segAccess.close();
                    System.err.println(Thread.currentThread().getName() + ":此分片读取" + currSeg + "完成");
                    latch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        latch.await();
        threadPool.shutdown();
        System.err.println("统计结束");
        List<Map.Entry<String, Integer>> sortedList = statistics.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());
        System.err.println(sortedList);
    }

    /**
     * 分段写
     */
    public static void segWrite(String filePath) throws Exception {
        // "G:\\Develop\\Jape\\src\\main\\resources\\static\\big.txt"
        // 创建
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();

        // 分片，10M数据
        int seg = 10;
        long numTotal = seg << 20;//seg * 1024 * 1024

        // 设置文件占位
        RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
        accessFile.setLength(numTotal * 8);
        accessFile.close();

        ExecutorService threadPool = Executors.newFixedThreadPool(seg);
        CountDownLatch latch = new CountDownLatch(seg);

        for (int i = 0; i < seg; i++) {
            int currSeg = i;
            threadPool.execute(() -> {
                System.err.println(Thread.currentThread().getName() + ":从此分片开始写入" + currSeg);
                try {
                    RandomAccessFile segAccess = new RandomAccessFile(file, "rw");
                    segAccess.seek(currSeg * 1024 * 1024 * 8);
                    Random r = new Random();
                    for (int j = 0; j < 1024 * 1024; j++) {
                        String content = String.format("%03d", r.nextInt(100)) + "\n";
                        segAccess.writeChars(content);
                    }
                    segAccess.close();
                    System.err.println(Thread.currentThread().getName() + ":此分片写入" + currSeg + "完成");
                    latch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        latch.await();
        System.err.println(Thread.currentThread().getName() + ":分片写入全部完成");
        //关闭
        threadPool.shutdown();
    }

    /**
     * 字符串覆盖写入文件
     */
    public static void str2File(String content, String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 字符串追加写入文件
     */
    public static void strAppend2File(String content, String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
