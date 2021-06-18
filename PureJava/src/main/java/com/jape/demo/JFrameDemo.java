package com.jape.demo;

import java.awt.*;
import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;


public class JFrameDemo
{
	public static void main(String[] args) 
	{
		JFrame window = new JFrame("窗口1");
		window.setBounds(100,100,500,300);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("标题");


        System.out.println("打开窗口");

	}
}
