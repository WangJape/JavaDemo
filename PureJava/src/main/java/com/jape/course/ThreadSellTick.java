package com.jape.course;

/**
 * @author Jape
 * @since 2021/6/18 15:19
 */
public class ThreadSellTick {

    static int ticks = 100;

    public static synchronized boolean sellTicks(int rn) {
        if (ticks <= 0) {
            System.out.println("线程" + rn + "没有买到票");
            return true;
        }
        ticks--;
        System.out.println("线程" + rn + "买到第" + ticks + "张票");
        return false;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            final int iL = i;
            Runnable r = new Runnable() {
                int rn = iL;

                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(100);
                            if (sellTicks(rn)) break;
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }
            };
            Thread t = new Thread(r);
            t.start();
        }
    }

}
