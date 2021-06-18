package com.jape.hcy;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReWrTest {
    private List<Integer>myList;
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private Lock readLock = rwLock.readLock();
    private Lock writeLock = rwLock.writeLock();
    public ReWrTest(List<Integer>myList){
        this.myList = myList;
    }
    public Object get(int index){
        readLock.lock();
        try{
            return myList.get(index);
        }finally {
            readLock.unlock();
        }
    }
    public boolean insert(int newValue){
        writeLock.lock();
        try{
            return myList.add((Integer)newValue);
        }finally {
            writeLock.unlock();
        }
    }
}
