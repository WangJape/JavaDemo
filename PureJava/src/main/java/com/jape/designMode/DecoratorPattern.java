package com.jape.designMode;

/**
 * 装饰者模式
 */
public class DecoratorPattern {
    public static void main(String[] args) {
        GameCharacter angele = new Angela();
        angele.attack();

        GameCharacter skinnedAngele = new MindHacker(angele);
        skinnedAngele.attack();
    }
}

//游戏人物
interface GameCharacter{
    void attack();
}

class Angela implements GameCharacter{
    @Override
    public void attack() {
        System.err.println("普通攻击造成100点法术伤害");
    }
}

//皮肤抽象类
abstract class appearance implements GameCharacter{
    GameCharacter gameCharacter;
    public appearance(GameCharacter gameCharacter) {
        this.gameCharacter = gameCharacter;
    }
    @Override
    public void attack() {
        gameCharacter.attack();
    }
}

//心灵骇客-皮肤
class MindHacker extends appearance{
    public MindHacker(GameCharacter gameCharacter) {
        super(gameCharacter);
    }
    @Override
    public void attack() {
        AttackSpecialEffects();
        gameCharacter.attack();
    }
    //攻击特效
    private void AttackSpecialEffects(){
        System.err.print("普通攻击是代码字母形状->");
    }
}


