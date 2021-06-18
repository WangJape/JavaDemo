package com.jape.Interview;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 实现一个简单的游戏战斗逻辑
 * 要求:
 * 1，一个场景类，一个单元类
 * 2，生成x 个场景，每个场景中生成y 个单元。
 * 3，每个单元有100点生命值。生命值小于1时则死亡。
 * 4，每个回合每单元对随机一个活着的其他单元造成10～15点伤害。直到没有其他活着的单元。战斗结束。
 * 5，多线程完成。性能优先。打印输出战斗过程。
 */
public class Game1 {


    public static void main(String[] args) {

        int x = 2;//场景
        int y = 5;//单元

        ExecutorService threadPool = Executors.newFixedThreadPool(x);
        //CyclicBarrier barrier = new CyclicBarrier(x);
        CountDownLatch latch = new CountDownLatch(x);

        /**
         * 循环进行场景的攻击回合，直到有胜出者
         */
        Runnable fighting = () -> {
            System.out.println("[" + Thread.currentThread().getName() + "]线程运行。");
            Scenes scenes = new Scenes(y);
            while (scenes.roundStart() != 1) {
                System.err.println("一个回合结束");
            }
            try {
                latch.countDown();
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println("[" + Thread.currentThread().getName() + "]结束游戏，胜出者：" + scenes.getSurviveUnits().get(0).name);
        };

        for (int i = 0; i < x; i++) {
            threadPool.execute(fighting);
        }
        threadPool.shutdown();
    }

}

/**
 * 场景
 */
class Scenes {

    private List<Unit> surviveUnits;

    private List<Unit> deadUnits;

    public Scenes(int y) {
        surviveUnits = new ArrayList<>();
        deadUnits = new ArrayList<>();
        for (int i = 0; i < y; i++) {
            surviveUnits.add(new Unit("单元" + i));
        }
    }

    /**
     * 在场景中开始一次回合，存活的轮番依次攻击
     * @return
     */
    public int roundStart() {
        int surviveNum = surviveUnits.size();
        for (int i = 0; i < surviveNum; i++) {
            int attackIndex = generateAttackIndex(i);
            int attackValue = generateAttackValue();

            Unit attackTarget = surviveUnits.get(attackIndex);
            if (attackTarget.minusLife(attackValue) < 1) {
                surviveUnits.remove(attackIndex);
                deadUnits.add(attackTarget);
                System.err.println(attackTarget.name + "死亡");
                surviveNum = surviveUnits.size();
            }
        }
        return surviveUnits.size();
    }

    /**
     * 生成要攻击的目标索引，随机值
     * @param currentIndex
     * @return
     */
    private int generateAttackIndex(int currentIndex) {
        int surviveNum = surviveUnits.size();
        Random r = new Random();
        int attackIndex = r.nextInt(surviveNum-1);
        if (attackIndex >= currentIndex) {
            attackIndex += 1;
        }
        System.err.print(surviveUnits.get(currentIndex).name + "-攻击-" + surviveUnits.get(attackIndex).name);
        return attackIndex;
    }

    /**
     * 生成要攻击扣除的生命值
     * @return
     */
    private int generateAttackValue() {
        Random r = new Random();
        int attackValue = r.nextInt(6) + 10;
        System.err.print(",生命值扣除" + attackValue);
        return attackValue;
    }

    /**
     * 得到当前存活的对象
     * @return
     */
    public List<Unit> getSurviveUnits(){
        return surviveUnits;
    }

}

/**
 * 单元
 */
class Unit {

    public String name;

    private int lifeValue = 100;

    private boolean isDead = false;

    public Unit(String name){
        this.name = name;
    }

    /**
     * 扣除生命值
     * @param value
     * @return
     */
    public int minusLife(int value) {
        lifeValue -= value;
        System.err.println(",剩余血量：" + lifeValue);
        return lifeValue;
    }

}