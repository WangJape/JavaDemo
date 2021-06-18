package com.jape.game;

import javax.swing.*;

public class MaxNum {
	public static String sum(int a,int b,int c,int d){
        if(a>b){
            d=a;
        }
        if(b>d){
            d=b;
        }
        if(c>d){
            d=c;
        }
        return "你输入的三个数字中，最大的数是"+d;
        }
    public static void main(String[] args) {
        int number1,number2,number3;
        try{
            number1=Integer.parseInt(JOptionPane.showInputDialog("Enter the first Number: "));
            number2=Integer.parseInt(JOptionPane.showInputDialog("Enter the second Number: "));
            number3=Integer.parseInt(JOptionPane.showInputDialog("Enter the third Number: "));
            System.out.println(sum(number1,number2,number3,0));    
            }catch(NumberFormatException ne){
                System.out.println(ne.toString());
             }
            System.exit(0);
            }
}
