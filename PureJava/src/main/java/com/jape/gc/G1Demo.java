package com.jape.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * G1垃圾回收器测试
 * jvm参数前缀
 * - 标准参数
 * -X 非标准参数
 * -XX 不稳定参数
 *
 * @author Jape
 * @since 2021/6/17 14:53
 */
public class G1Demo {
    /**
     * 启动参数 -Xlog:gc* -Xms10m -Xmx10m
     * G1参数
     * -XX:+UseG1GC jdk9默认
     * -XX:G1HeapRegionSize=32m 设置区大小,默认值将根据 heap size 算出最优解，范围1-32m
     * -XX:G1ReservePercent=n 预留空间百分比 默认10
     * -XX:InitiatingHeapOccupancyPercent=n  启动并发GC周期时的堆内存占用百分比，默认值为 45
     * -XX:MaxTenuringThreshold=n 提升年老代的最大临界值(tenuring threshold). 默认值为 15
     * -XX:MaxGCPauseMillis=n 最大GC停顿时间
     * -Xlog:gc -verbose:gc 打印基本GC信息
     * -Xlog:gc* 打印详细GC信息 （-XX:+PrintGCDetails jdk9已废弃）
     * -Xlog:ergo*=level 输出自适应分代大小
     * -Xlog:gc+region=trace 输出G1 region分配和回收信息
     * -Xlog:gc:garbage-collection.log GC日志输出到文件
     */
    public static void main(String[] args) {
        if (true) {
            List<String> l1 = new ArrayList<>();
            for (int i = 0; i < 100000; i++) {
                if ((i & 0x3FFF) == 0) {
                    System.out.println(i);
                }
                l1.add(String.valueOf(i));
            }
        }
        List<String> l2 = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            if ((i & 0x3FFF) == 0) {
                System.out.println(i);
            }
            l2.add(String.valueOf(i));
        }
    }
}
