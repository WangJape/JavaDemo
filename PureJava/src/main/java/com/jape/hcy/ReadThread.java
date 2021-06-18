package com.jape.hcy;

public class ReadThread extends Thread{
    int id;
    ReWrTest test;
    int num ;
    public ReadThread(int id,ReWrTest test,int executeTimes){
        this.id = id ;
        this.test = test ;
        num = executeTimes;
    }

    @Override
    public void run() {
        int index;
        for (int i = 0 ; i < num ; i ++){
            index = id *num + i ;
            test.get(index);
        }
    }
}
