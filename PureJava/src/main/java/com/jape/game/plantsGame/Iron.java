package com.jape.game.plantsGame;

import java.awt.image.BufferedImage;

public class Iron extends Zombies{
	public static BufferedImage images[] = new BufferedImage[8];
	static {
		for(int a=1;a<=8;a++) {
			images[a-1] = loadImage("z_02_0"+a+".png");
		}

	}
	public Iron() {
		super(1300, 8, 90,130,3,4);
		this.y = nextY();
		System.out.println(life);
	}
	

	protected void step() {
		x-=speed;
	}
	int index=0;
	public BufferedImage getImage() {
		if(index==8) {
			index=0;
		}
		return images[index++];	
	}
}
