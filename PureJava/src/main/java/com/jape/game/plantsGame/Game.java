package com.jape.game.plantsGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel{
	private int score;
	Background background = new Background();
	Hammer hammer = new Hammer();
	Zombies enemy[] = {
		new Ordinary(),
		new Roadblock(),
		new Iron()
	};
	int a = 0;
	//画笔
	public void paint(Graphics g) {
		g.drawImage(background.image, 0, 0, 1400, 600, null);
		for(int i=0;i<enemy.length;i++) {
			enemy[i].paintObject(g);
		}
		hammer.paintObject(g);
		g.setFont(new Font("Tahoma", Font.BOLD, 20));
		g.drawString("SCORE:"+score,1200,550);//画分�?
		g.drawString("AUTHOR:"+"烧包哥哥",1200,570);//作�??
		if(space==false) {
			g.setFont(new Font("Tahoma", Font.BOLD, 80));
			g.drawString("STOP",620,640/2);//画分�?
		}
		
	}
	//随即生成僵尸
	public Zombies enemyRandom() {
		a = (int) (Math.random()*20);
		if(a<=12) {
			return new Ordinary();
		}else if(a>12&&a<=17) {
			return new Roadblock();
		}else {
			return new Iron();
		}
	}
	//生成僵尸
	int enterIndex=0;
	public void enterAction() {
		enterIndex++;
		if(enterIndex%200==0) {
			//多长时间走一�?
			Zombies obj=enemyRandom();//获取敌人数组
			enemy=Arrays.copyOf(enemy, enemy.length+1);//扩容
			enemy[enemy.length-1]=obj;
		}
	}
	//僵尸走动
	public void step() {
		for(int a = 0;a<enemy.length;a++) {
			enemy[a].step();
			if(enemy.length<=10) {
				enterAction();
			}
		}
	}
	//判断生命�?0的僵尸删�?
	public void outOfBoundsAction() {
		int index = 0;
		Zombies[] flyingLives = new Zombies[enemy.length];
		for (int i = 0; i < enemy.length; i++) {
			Zombies f = enemy[i];
			if (f.life!=0) {
				flyingLives[index++] = f;
			}else {
				score+=1;
			}
		}
		enemy = Arrays.copyOf(flyingLives, index); 
	}
		/**启动程序的执�?*/
	public void action() {	
		MouseAdapter l=new MouseAdapter() {//创建侦听器对�?
			public void mouseMoved(MouseEvent e) {
				if(space==true) {
					if(true) {
						int x=e.getX();
						int y=e.getY();
						hammer.moveTo(x, y);
					}
				}
			}
			//鼠标左键单击事件
			public void mouseClicked(MouseEvent arg0) {
				if(space==true) {
				if(arg0.getButton() == MouseEvent.BUTTON1) {
			   	hammer.getButton(1);
				}
				//判断僵尸是否被打中，打中了减掉一�?
				boolean duan = false;
				if(true) {
					int x=arg0.getX();
					int y=arg0.getY();
					for(int a = 0;a<enemy.length;a++) {
						duan = hammer.duang(enemy[a].x,enemy[a].width, enemy[a].y,enemy[a].height);
						if(duan==true) {
							duan = enemy[a].booleanfile();
						}
					}
				}
			}
			}
		};

		this.addMouseListener(l);
		this.addMouseMotionListener(l);	
		Timer timer=new Timer();//创建定时�?
		int intervel=70;//时间间隔，以毫秒为单位，�?10毫秒运行�?�?
		timer.schedule(new TimerTask() {
			public void run() {//定时干的�?
				if(space==true) {
					step();//僵尸走动
					outOfBoundsAction();//判断僵尸生命并删�?
					enterAction();//生成僵尸
				}

			}

		},intervel, intervel);//（定时干的事，几毫秒后开始，每几毫秒运行�?次）
	}
	public void action1() {
		Timer timer=new Timer();//创建定时�?
		int intervel=180;//时间间隔，以毫秒为单位，�?10毫秒运行�?�?
		timer.schedule(new TimerTask() {
			public void run() {//定时干的�?
				repaint();//重画，调用paint方法
			}

		},intervel, intervel);//（定时干的事，几毫秒后开始，每几毫秒运行�?次）
	}
	private KeyboardPanel keyboardPanel = new KeyboardPanel();
	//键盘监控
	public Game(){
	  add(keyboardPanel);   
	  	keyboardPanel.setFocusable(true);       
  	}
	static boolean space = true;
	 //键盘监控事件�?
	static class KeyboardPanel extends JPanel{
		public KeyboardPanel(){
			addKeyListener( new KeyAdapter(){
				public void keyPressed(KeyEvent e){
					switch(e.getKeyCode()){
						case KeyEvent.VK_ENTER: 
							if(space==true) {
								space=false;
								System.out.println(space);
							}else if(space==false){
								space=true;
								System.out.println(space);
							}

						}
					}
			});
		}
	}
	//主方�?
	public static void main(String[] args) {
		JFrame jf = new JFrame();
		Game game = new Game();
		if(space==true) {
			game.action();
			game.action1();
		}
		jf.add(game);
		jf.setSize(1402,637);//宽高
		jf.setVisible(true);//窗口可见
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
