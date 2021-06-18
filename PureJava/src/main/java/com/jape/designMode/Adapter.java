package com.jape.designMode;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

public class Adapter extends Stack implements Queue {

    private Stack<Object> stack = new Stack<Object>();
    private ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(1024);


    //***********************************************************************************
    //***********************************************************************************
    //***********************************************************************************
    //queue适配
    @Override
    public Object element() {
        // TODO Auto-generated method stub
        System.out.println("不支持的方法:element()");
        return null;
    }

    @Override
    public boolean offer(Object e) {
        // TODO Auto-generated method stub
        stack.push(e);
        return true;
    }

    @Override
    public Object poll() {
        // TODO Auto-generated method stub
        return stack.pop();
    }

    @Override
    public Object remove() {
        // TODO Auto-generated method stub
        return stack.pop();
    }

    //***********************************************************************************
    //***********************************************************************************
    //***********************************************************************************
    //stack适配
    public void clear() {
        queue.clear();
    }

    public Object peek() {
        return queue.peek();
    }

    @Override
    public Object push(Object e) {
        // TODO Auto-generated method stub
        //System.out.println(e);
        try {
            queue.put(e);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return true;
    }

    public Object pop() {
        return queue.poll();
    }

    public boolean empty() {
        System.out.println("不支持的方法:empty()");
        return false;

    }


}
