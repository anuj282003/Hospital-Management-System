package com.hospitalmanagemtSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
	private Connection conn;
	private Scanner scan ;
	public Patient(Connection conn, Scanner scan) {
		this.conn=conn;
		this.scan=scan;
	}
	public void addPatient() {
		String query="insert into patient(name , age ,gender) values(?,?,?)";
		try {
			System.out.println("Please Enter Your Name");
			String name=scan.next();
			System.out.println("please Enter Your Age");
			int age =scan.nextInt();
			System.out.println("please Enter Your  Gender");
			String gender=scan.next();
			
			PreparedStatement pstmt=conn.prepareStatement(query);
			pstmt.setString(1,name);
			pstmt.setInt(2, age);
			pstmt.setString(3, gender);
			int affectedRows=pstmt.executeUpdate();
			if(affectedRows>0) {
				System.out.println("Adding Patient Succesfully");
			}
			else {
				System.out.println("failed To adding Patient");
			}
			
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void  viewPatient() {
		String query ="Select * from patient where id =?";
		try {
			System.out.println("Please Enter the ID Of Patient");
		     int id =scan.nextInt();
			PreparedStatement pstmt =conn.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet rs=pstmt.executeQuery();
			System.out.println("Patients:");
			System.out.println("+-------+-------------------+------+--------+");
			System.out.println("|ID     | Patient Name      | Age  | Gender |");
			System.out.println("+-------+-------------------+------+--------+");
			while(rs.next()) {
				int p_id=rs.getInt("id");
				String p_name=rs.getString("name");
				int p_age=rs.getInt("age");
				String p_gender=rs.getString("gender");
				System.out.printf("|%-7s| %-17s | %-4s | %-7s|\n",p_id,p_name,p_age,p_gender);
				System.out.print("+-------+-------------------+------+--------+");
			
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public boolean checkPatient(int p_id) {
		String query ="Select * from patient where id=?";
		try {
//		System.out.println("please Enter the Patient Id");
//		int p_id=scan.nextInt();
//		int id=scan.nextInt();
		PreparedStatement pstmt=conn.prepareStatement(query);
		pstmt.setInt(1,p_id);
		pstmt.execute();
		ResultSet rs =pstmt.executeQuery();
		if(rs.next()) {
			return true;
		}
		else {
			return false;
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
