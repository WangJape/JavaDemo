package com.jape.course;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MysqlAccess {
	public static void main(String[] args) {
		Integer id;
    	String name;
    	Integer age;
    	String sex;
        String strMysqlURL="jdbc:mysql://localhost:3306/snake?characterEncoding=utf8&useSSL=false&serverTimezone=Hongkong";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection con=DriverManager.getConnection(strMysqlURL,"root","WJPSQ");
            System.out.println("A DB connection is created.");
            PreparedStatement pstmt=con.prepareStatement("select * from user");
//            pstmt.setString(1, userName);
//            pstmt.setString(2, password);
            ResultSet rs=pstmt.executeQuery();
            while(rs.next()){
            	id = rs.getInt("id");
            	name = rs.getString("name");
            	age = rs.getInt("age");
            	sex = rs.getString("sex");
            	System.out.println(id+":"+name+","+age+","+sex+";");
            }
            rs.close();
            con.close();
        } catch (Exception ex) {
           ex.printStackTrace();
        }				
	}

}
