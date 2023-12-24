package com.hospitalmanagemtSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Doctor {
	
	private Connection conn;
	private Scanner scan;
	
	public Doctor(Connection conn, Scanner scan) {
		this.conn=conn;
		this.scan=scan;
		
	}
		
	
	public void  viewDoctor() {
		String query ="Select * from doctors where id =?";
		try {
			System.out.println("Please Enter the ID Of Doctor");
			int id=scan.nextInt();
			PreparedStatement pstmt =conn.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet rs=pstmt.executeQuery();
			System.out.println("Doctors:");
			System.out.println("+-------+-------------------+----------------+");
			System.out.println("|ID     | Doctor Name       | Specialization |");
			System.out.println("+-------+-------------------+----------------+");
			while(rs.next()) {
				int d_id=rs.getInt("id");
				String d_name=rs.getString("name");
				String spec=rs.getString("specialization");
				System.out.printf("|%-7s| %-17s | %-14s |\n",d_id,d_name,spec);
				System.out.print("+-------+-------------------+------+--------+");
			
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}

      }
	public boolean checkDoctor(int d_id) {
		String query ="Select * from doctors where id=?";
		try {
//		System.out.println("please Enter the Doctor Id");
//		int d_id=scan.nextInt();
//		System.out.println("Please Enter the ID Of Doctor");
//		int id=scan.nextInt();
		PreparedStatement pstmt=conn.prepareStatement(query);
		pstmt.setInt(1,d_id);
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
