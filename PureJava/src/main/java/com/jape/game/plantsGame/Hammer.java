package com.jape.game.plantsGame;

import java.awt.image.BufferedImage;

/**
 * 锤子
 * @author soft01
 *	this.x = x-40;
 *	this.y = y-90;
 */
public class Hammer extends Zombies{
	private int mouse = 0;
	public static BufferedImage images[] = new BufferedImage[2];
	static {
		images[0] = loadImage("hammer.png");
		images[1] = loadImage("hammer_down.png");
	}
	public Hammer() {
		super(10, 10, 70, 110);
	}
	public BufferedImage getImage() {
		if(mouse==1) {
			mouse-=1;
			return images[1];
		}
		return images[0];
	}
	protected void step() {
	}
	/*
	 * 锤子跟着鼠标移动
	 */
	public void moveTo(int x,int y) {
		this.x = x-width;
		this.y = y-height;
	}
	public int getx() {
		return x;
	}
	public int gety() {
		return y;
	}
//	public boolean bang(int x,int x1,int y,int y1) {
//		System.out.println('b');
//		if(this.x>=x&&this.x<=x1&&this.y>=y&&this.y>=y1) {
//			System.out.println('a');
//			return true;
//		}else {
//			return false;
//		}
//	}
	/*
	 * 鼠标被左键单�?
	 */
	public void getButton(int mouse) {
		this.mouse = mouse;
	}
	//判断是否击中僵尸
	public boolean duang(int x,int width,int y,int height) {
		if(this.x-20>=x-70&&this.x-20<=x&&this.y-50<=y-60&&this.y-30>=(y-60)-140) {
			return true;
		}else {
			return false;
		}
		
	}
}
