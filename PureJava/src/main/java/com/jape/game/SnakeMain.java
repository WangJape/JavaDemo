package com.jape.game;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class SnakeMain extends JFrame{
  public SnakeMain(){
      SnakeWin win = new SnakeWin();
      add(win);
      setTitle("贪吃蛇");
      setSize(435,390);
      setLocation(200,200);
      setVisible(true);
  }
  public static void main(String[] args){
      new SnakeMain();
  }
}


class SnakeWin extends JPanel implements ActionListener,KeyListener,Runnable{
   JButton newGame,stopGame,/*skin,*/pause,continuegame;
   JComboBox<String> skin;
   int fenShu=0,Speed=0,red,green,blue;//分数与速度还有颜色
   boolean start = false,islive=true,colorful=false;
   Random r = new Random();//产生随机数，生成果实
   int rx = 0,ry = 0;//果实坐标
   List<snakeBody> list = new ArrayList<snakeBody>();//蛇身
   int temp = 0;//控制蛇身自动移动
   int u=4;//防止蛇头反向
   int tempeat1=0,tempeat2=0;
   JDialog dialog = new JDialog();
   JLabel label = new JLabel("你挂了，你的分数是"+fenShu);
   JButton ok = new JButton("好吧");
   Thread nThread;//增加线程，蛇身自动移动

   public SnakeWin(){
       newGame = new JButton("开始");
       stopGame = new JButton("退出");
       skin = new JComboBox<String>();
       skin=new JComboBox(new String[]{"黑蛇", "绿蛇", "红蛇", "随机蛇","七彩皮皮蛇"});
       pause = new JButton("暂停");
       continuegame = new JButton("继续"); 
       this.setLayout(new FlowLayout(FlowLayout.LEFT));
       newGame.addActionListener(this);
       stopGame.addActionListener(this);
       skin.addItemListener(new ItemListener(){
           public void itemStateChanged(final ItemEvent e) {
            int index = skin.getSelectedIndex();
            switch(index){
            case 0:{
                changeSkin(1);
                break;
            }
            case 1:{
                changeSkin(2);
                break;
            }
            case 2:{
                changeSkin(3);
                break;
            }
            case 3:{
                changeSkin(4);
                break;
            }
            case 4:{
                changeSkin(5);
                break;
            }
            default:
                break;
            }
            requestFocus(true);
        }
       });
       continuegame.addActionListener(this);
       pause.addActionListener(this);
       this.addKeyListener(this);
       this.add(newGame);
       this.add(stopGame);
       this.add(skin); 
       this.add(pause);
       this.add(continuegame);
       ok.addActionListener(this );
       dialog.setLayout(new GridLayout(1,2));
       dialog.add(label);
       dialog.add(ok);
       dialog.setSize(400,100);
       dialog.setLocation(200, 200);
       dialog.setVisible(false);
   }
class Food{
    private int x;
    private int y;
    void produceFood(){
        x=r.nextInt(40);
        y=r.nextInt(30);
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
Food food = new Food();
Wall wall=new Wall();
   public void paintComponent(Graphics g){
       super.paintComponent(g);
       g.drawRect(10, 40, 400, 300);
       g.drawString("分数"+fenShu, 150+180+35, 20);
       g.drawString("速度"+Speed, 150+180+35, 35);
       g.setColor(new Color(0,0,255));
       if(start){
            if(colorful){changeSkin(5);}
            g.fillRect(10+food.getX()*10, 40+food.getY()*10, 10, 10);
            g.setColor(new Color(red,green,blue));
            for(int i = 0;i<list.size();i++){
                g.fillRect(10+list.get(i).getX()*10, 40+list.get(i).getY()*10, 10, 10);
            }
            g.setColor(new Color(255,0,0));
            for(int i=0;i<10;i++){
                g.fillRect(10+wall.list1.get(i).x*10,40+wall.list1.get(i).y*10, 10, 10);
                g.fillRect(10+wall.list2.get(i).x*10,40+wall.list2.get(i).y*10, 10, 10);
                g.fillRect(10+wall.list3.get(i).x*10,40+wall.list3.get(i).y*10, 10, 10);
                g.fillRect(10+wall.list4.get(i).x*10,40+wall.list4.get(i).y*10, 10, 10);
            }
       }
   }
 
@Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource()==newGame){
        newGame.setEnabled(false);
        wall.d();
        start = true;
        food.produceFood();
        snakeBody piece = new snakeBody();
        piece.setX(20);
        piece.setY(15);
        list.add(piece);
        requestFocus(true);
        nThread = new Thread(this);
        nThread.start();
        repaint();
    }
     
    if(e.getSource()==stopGame){
        System.exit(0);
    }
    if(e.getSource()==ok){
           
        list.clear();
        fenShu=0;
        Speed=0;
        start = false;
        newGame.setEnabled(true);
        dialog.setVisible(false);
        repaint();
    }
    if(e.getSource()==continuegame){
        islive=true;
        requestFocus(true);
    }
    if(e.getSource()==pause){
        islive=false;
    }
}
private void changeSkin(int i){
    switch(i){
        case 1:{
            red = 0;
            green=0;
            blue = 0;
            colorful=false;
            break;
        }
        case 2:{
            red=0;
            blue=0;
            green=255;
            colorful=false;
            break;
         
        }
        case 3:{
            red=255;
            green=0;
            blue=0;
            colorful=false;
            break;
        }
        case 4:{
            Random r = new Random();
            Random g = new Random();
            Random b = new Random();
            this.red = r.nextInt(255);
            this.green = g.nextInt(255);
            this.blue = b.nextInt(255);
            colorful=false;
            break;
        }
        case 5:{
            colorful=true;
            Random r = new Random();
            Random g = new Random();
            Random b = new Random();
            this.red = r.nextInt(255);
            this.green = g.nextInt(255);
            this.blue = b.nextInt(255);
            break;
        }
    }
}
 
//实现水果不与蛇身重合
private boolean apple(int rx,int ry){
    boolean apple = false;
    for(int i=0;i<list.size();i++){
        if(rx==list.get(i).getX()&&ry==list.get(i).getY()){
            apple = true;
        }
    }
         
    for(int j=0;j<10;j++){
        if(rx==wall.list1.get(j).x&&ry==wall.list1.get(j).y){
            apple =true;
        }
    }
    for(int j=0;j<10;j++){
        if(rx==wall.list2.get(j).x&&ry==wall.list2.get(j).y){
            apple = true;
        }
    }
    for(int j=0;j<10;j++){
        if(rx==wall.list3.get(j).x&&ry==wall.list3.get(j).y){
            apple = true;
        }
    }
    for(int j=0;j<10;j++){
        if(rx==wall.list4.get(j).x&&ry==wall.list4.get(j).y){
            apple = true;
        }
    }
    return apple;
}
private void eat(){
    if(food.getX()==list.get(0).getX()&&food.getY()==list.get(0).getY()){
        do{
            food.produceFood();
            }while(apple (food.getX(),food.getY()));                //当水果与蛇身重合时，重新对水果坐标赋值
        snakeBody piece = new snakeBody();
        piece.setX(list.get(list.size()-1).getX());
        piece.setY(list.get(list.size()-1).getY());
        list.add(piece);
        fenShu+=1;
        tempeat1+=1;
        if(tempeat1-tempeat2>=5){
            tempeat2=tempeat1;
            if(Speed<=9){
                Speed+=1;
            }
        }
    }
}
 
       //蛇身移动
public void otherMove(){
    snakeBody piece = new snakeBody();
    for(int i= 0; i< list.size();i++){
        if(i==1){
            list.get(i).setX(list.get(0).getX());
            list.get(i).setY(list.get(0).getY());
        }else if(i>1){
            piece = list.get(i-1);
            list.set(i-1,list.get(i));
            list.set(i,piece);
        }
    }
}
 
       //蛇头移动
public void move(int x,int y){
    if(islive){
        if (!minYes(x,y)){
            otherMove();
            list.get(0).setX(list.get(0).getX()+x);
            list.get(0).setY(list.get(0).getY()+y);
            eat();
            repaint();
        }
        else {//死亡方法
            nThread=null;
            label.setText("你挂了，你的的分数是"+fenShu);
            dialog.setVisible(true);
        }
    }
}
public boolean minYes(int x,int y){
    if(maxYes(list.get(0).getX()+x,list.get(0).getY()+y)){
        return false;
    }
     
    return true;
}
 
public boolean maxYes(int x,int y){
    if(x<0||x>=40||y<0||y>=30){
        return false;
    }
    for(int i=0;i<list.size();i++){
        if(i>1&&list.get(0).getX()==list.get(i).getX()&&list.get(0).getY()==list.get(i).getY()){
            return false;
        }
    for(int j=0;j<10;j++){
        if(list.get(0).getX()==wall.list1.get(j).x&&list.get(0).getY()==wall.list1.get(j).y){
            return false;
        }
    }
    for(int j=0;j<10;j++){
        if(list.get(0).getX()==wall.list2.get(j).x&&list.get(0).getY()==wall.list2.get(j).y){
            return false;
        }
    }
    for(int j=0;j<10;j++){
        if(list.get(0).getX()==wall.list3.get(j).x&&list.get(0).getY()==wall.list3.get(j).y){
            return false;
        }
    }
    for(int j=0;j<10;j++){
        if(list.get(0).getX()==wall.list4.get(j).x&&list.get(0).getY()==wall.list4.get(j).y){
            return false;
        }
    }
    }
    return true;
}
@Override
public void keyPressed(KeyEvent e) {
    if(start){
         
        switch (e.getKeyCode()){
        case KeyEvent.VK_UP:
            if(u!=2){
                move(0,-1);
                u=1;
                temp=1;}
            break;
             
        case KeyEvent.VK_DOWN:
            if(u!=1){
                move(0,1);
                u=2;
                temp=2;}
            break;
             
        case KeyEvent.VK_LEFT:
            if(u!=4){
                move(-1,0);
                u=3;
                temp=3;}
            break;
             
        case KeyEvent.VK_RIGHT:
            if(u!=3){
                move(1,0);
                u=4;
                temp=4;}
            break;
             
            default:
                break;
        }
    }
 
}
    @Override
    public void keyReleased(KeyEvent arg0) {}
    @Override
    public void keyTyped(KeyEvent arg0) {}
    @Override
    public void run() {
        while(start){
            switch(temp){
            case 1:
                move(0,-1);
                break;
            case 2:
                move(0,1);
                break;
            case 3:
                move(-1,0);
                break;
            case 4:
                move(1,0);
                break;
            default:
                move(1,0);
                break;
            }       
            repaint();  
            try {
                Thread.sleep(500-(50*Speed));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }   
    }
}


class snakeBody {
    private int x;
    private int y;
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
 
}

class Brick{
    int x;
    int y;
    public void setXY(int x,int y){
        this.x=x;
        this.y=y;
    }
}

class Wall{
    List<Brick> list1=new ArrayList<Brick>();
    List<Brick> list2=new ArrayList<Brick>();
    List<Brick> list3=new ArrayList<Brick>();
    List<Brick> list4=new ArrayList<Brick>();
    public void d(){
        for(int i=0;i<10;i++){
            Brick brick1=new Brick();
            Brick brick2=new Brick();
            Brick brick3=new Brick();
            Brick brick4=new Brick();
            brick1.setXY(i, 15);
            list1.add(brick1);
            brick2.setXY(i+30, 15);
            list2.add(brick2);
            brick3.setXY(20, i);
            list3.add(brick3);
            brick4.setXY(20, i+20);
            list4.add(brick4);
            }
         
   
    }
} 
