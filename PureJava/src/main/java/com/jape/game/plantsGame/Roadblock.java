package com.jape.game.plantsGame;

import java.awt.image.BufferedImage;

public class Roadblock extends Zombies{
	public static BufferedImage images[] = new BufferedImage[8];
	static {
		for(int a=1;a<=8;a++) {
			images[a-1] = loadImage("z_01_0"+a+".png");
		}

	}
	public Roadblock() {
		super(1300, 10, 90,130,2,2);
		this.y = nextY();
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
