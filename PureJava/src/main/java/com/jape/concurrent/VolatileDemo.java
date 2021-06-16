package com.jape.concurrent;

/**
 * 缓存行的伪共享测试
 * <p>
 * cpu读数据会一次取64B的数据到缓存
 * 当操作到被volatile修饰的变量时，使用_ASM_ lock; addl $0,0(%%esp) 写屏障汇编指令使缓存刷新到内存中，并使其他处理器中的缓存失效，同时lock指令 不允许有序的指令越过这个内存屏障（线程可见性与禁止指令重排序）
 * 其他线程就要重新从内存读取这64B内存数据 放入自己的缓存。
 * <p>
 * 使用特殊写法，可以使缓存行隔离，不用互相通知
 */
public class VolatileDemo {

    /**
     * 裸奔写法
     * (1850ms)
     */
    private static class SmpNum {
        public volatile long n = 0L;
    }

    /**
     * jdk1.7及之前(435ms)
     */
    private static class PadNum {
        private long p0, p1, p2, p3, p4, p5, p6;
        public volatile long n = 0L;
        private long p8, p9, p10, p11, p12, p13, p14, p15;
    }

    /**
     * jdk1.8 @sun.misc.Contended
     * jdk11 @jdk.internal.vm.annotation.Contended( 外部用不了 加--add-exports=java.base/jdk.internal.vm.annotation=ALL-UNNAMED )
     * (1030ms)
     */
    //@jdk.internal.vm.annotation.Contended
    private static class AnnNum {
        public volatile long n = 0L;
    }

    private static final long COUNT = 1_0000_0000L;
    private static final PadNum[] arr = new PadNum[2];

    static {
        arr[0] = new PadNum();
        arr[1] = new PadNum();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                arr[0].n++;
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                arr[1].n++;
            }
        });
        long start = System.currentTimeMillis();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        long end = System.currentTimeMillis();
        System.err.println(end - start);
    }


}
