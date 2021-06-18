package com.jape.game.plantsGame;

import java.awt.image.BufferedImage;

public class Background extends Zombies{
	static BufferedImage image;
	static {
		image = loadImage("background.png");//背景图片
	}
	public Background() {
		super(0, 0, 1402, 637);
	}
	protected void step() {
	}
	@Override
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return null;
	}


}
