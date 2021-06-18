package com.jape.designMode;

/**
 * 策略模式
 * 一个类的行为或其算法可以在运行时更改。
 * 这种类型的设计模式属于行为型模式
 * 在策略模式中，我们创建表示各种策略的对象和一个行为随着策略对象改变而改变的 context 对象。
 * 策略对象改变 context 对象的执行算法。
 */
public class Strategy {
    public static void main(String[] args) {
        Zombie zombie1 = new SimpleZombie();
        zombie1.setMoveAble(new MoveNormal());
        zombie1.setAttackAble(new AttackEat());
        zombie1.move();
        zombie1.attack();

        zombie1.setMoveAble(new MoveQuickly());
        zombie1.setAttackAble(new AttackStolenFromTheSky());
        zombie1.move();
        zombie1.attack();
    }
}

//移动策略
interface MoveAble{
    void move();
}

class MoveNormal implements MoveAble{
    @Override
    public void move() {
        System.err.println("正在1X速正常前进");
    }
}

class MoveQuickly implements MoveAble{
    @Override
    public void move() {
        System.err.println("正在2X速前进");
    }
}

//攻击策略
interface AttackAble{
    void attack();
}

class AttackEat implements AttackAble{
    @Override
    public void attack() {
        System.err.println("用手抓着吃植物");
    }
}

class AttackStolenFromTheSky implements AttackAble{
    @Override
    public void attack() {
        System.err.println("从天上偷走植物");
    }
}

//僵尸
abstract class Zombie{
    MoveAble moveAble;
    AttackAble attackAble;

    abstract void appearance();
    abstract void move();
    abstract void attack();

    public MoveAble getMoveAble() {
        return moveAble;
    }

    public void setMoveAble(MoveAble moveAble) {
        this.moveAble = moveAble;
    }

    public AttackAble getAttackAble() {
        return attackAble;
    }

    public void setAttackAble(AttackAble attackAble) {
        this.attackAble = attackAble;
    }
}

class SimpleZombie extends Zombie{

    @Override
    void appearance() {
        System.err.println("普通僵尸");
    }

    @Override
    void move() {
        moveAble.move();
    }

    @Override
    void attack() {
        attackAble.attack();
    }
}


