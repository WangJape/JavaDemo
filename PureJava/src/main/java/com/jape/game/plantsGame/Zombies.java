package com.jape.game.plantsGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 8
 * 100
 * 200
 * 300
 * 400
 */
abstract public class Zombies {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int speed;
	protected int life;
	public Zombies(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.life = life;
	}
	public Zombies(int x, int y, int width, int height,int speed,int life) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.life = life;
	}
	public static BufferedImage loadImage(String fileName) {
		try {
			BufferedImage img=ImageIO.read(Game.class.getResource(fileName));
			return img;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	/**获取图片*/
	public abstract BufferedImage getImage();
	public void paintObject(Graphics g) {
		g.drawImage(getImage(),x,y,width,height,null);
			
	}

	abstract protected void step();
	int a = 1;
	//僵尸随即出现的路�?
	public int nextY() {
		a = (int) (Math.random()*4);
		if(a==0) {
			return 8;
		}else if(a==1) {
			return 100;
		}else if(a==2) {
			return 200;
		}else if(a==3) {
			return 300;
		}else {
			return 400;
		}
	}
	public boolean booleanfile() {
		life-=1;
		return false;
	}
	
}
