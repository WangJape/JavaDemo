package com.jape.Interview;

public class BeverageExchange {

    public static void main(String[] args) {
        BeverageExchange beverageExchange = new BeverageExchange();
        beverageExchange.alreadyDrinkNum = 50;
        int totalDrunkNum = beverageExchange.exchange(50);
        System.err.println(totalDrunkNum);

        System.err.println(beverageExchange.drink(50));

        System.err.println(beverageExchange.exchange2(50) + 50);
    }

    public int alreadyDrinkNum = 0;

    private int exchange(int emptyBottleNum) {
        alreadyDrinkNum += emptyBottleNum / 3;
        emptyBottleNum = emptyBottleNum / 3 + emptyBottleNum % 3;
        if (emptyBottleNum < 3) {
            return 0;
        } else {
            exchange(emptyBottleNum);
        }
        return alreadyDrinkNum;

    }

    private int drink(int drinkNum) {
        int emptyNum = drinkNum;
        int exchangeNum = 0;
        while (emptyNum >= 3) {
            exchangeNum = emptyNum / 3;
            emptyNum = exchangeNum + emptyNum % 3;
            drinkNum += exchangeNum;
        }
        return drinkNum;
    }

    private int exchange2(int emptyNum) {
        if (emptyNum < 3) {
            return 0;
        }
        int exchangeNum = emptyNum / 3;
        emptyNum = exchangeNum + emptyNum % 3;
        return exchangeNum + exchange2(emptyNum);
    }

}
