package com.jape.course;

public class ThreadSaleOfTickets {
	public static void main(String[] args) {
		saleThread sale = new saleThread();//任务
		Thread thread1 = new Thread(sale,"窗口1");
		Thread thread2 = new Thread(sale,"窗口2");
		Thread thread3 = new Thread(sale,"窗口3");
		Thread thread4 = new Thread(sale,"窗口4");
		Thread thread5 = new Thread(sale,"窗口5");
		thread1.start();
		System.out.println(thread1.getName()+":已经建立！");
		thread2.start();
		System.out.println(thread2.getName()+":已经建立！");
		thread3.start();
		System.out.println(thread3.getName()+":已经建立！");
		thread4.start();
		System.out.println(thread4.getName()+":已经建立！");
		thread5.start();
		System.out.println(thread5.getName()+":已经建立！");
	}
}

class saleThread implements Runnable{
	static int tickets = 100;
	public void run() {
		while(true) {
			System.out.println(Thread.currentThread().getName()+":准备进入同步卖票代码块！");
			saleTicket();
			if(tickets == 0) {
				System.out.println(Thread.currentThread().getName()+":进程结束！");
				break;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public synchronized void saleTicket() {
		if(tickets > 0) {
			System.out.println(Thread.currentThread().getName()+":卖出第"+tickets+"张票！");
			tickets--;
		}
		else {
			System.out.println(Thread.currentThread().getName()+":票已经卖完！");
		}
		
	}
}