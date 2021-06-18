package com.jape.game.plantsGame;

import java.awt.image.BufferedImage;

public class Ordinary extends Zombies{
	public static BufferedImage images[] = new BufferedImage[8];
	static {
		for(int a=1;a<=8;a++) {
			images[a-1] = loadImage("z_00_0"+a+".png");
		}

	}
	public Ordinary() {
		super(1300, 10, 90,130,1,1);
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
