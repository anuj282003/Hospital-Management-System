package com.hospitalmanagemtSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalApp {
	private static final String  url="jdbc:mysql://localhost:3306/hospital";
	private static final String username="root";
	private static final String password="Anuj@123";


	public static void main(String[] args) {
		Scanner scan =new Scanner(System.in);
			while(true) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection =DriverManager.getConnection(url,username,password);
				Patient patient =new Patient(connection,scan);
				Doctor doctor =new Doctor(connection,scan);
				System.out.println("Welcome Hotel Management ");
	//			patient.addPatient();
	//			patient.viewPatient();
				
				System.out.println("1.Add a Patient");
				System.out.println("2.View a Patient");
				System.out.println("3.View Doctors");
				System.out.println("4.Book Appointment");
				System.out.println("5. Exit");
				System.out.println("Please Choose a Option");
				int choose=scan.nextInt();
				
				switch(choose) {
				case 1:
					patient.addPatient();
					break;
				case 2:
					patient.viewPatient();
					break;
				case 3:
					doctor.viewDoctor();
					break;
				case 4:
					bookAppointment(patient,doctor,connection,scan);
					break;
				case 5:
					System.out.println("Thank you for Using Hospital Management System!!!!!!!!!");
					return;
				default:
					System.out.println("Please choose Valid option");
				
					
					
				
				}
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			}

	}
	public static void bookAppointment(Patient patient, Doctor doctor,Connection conn,Scanner scan)
	{
		System.out.println("please Enter the Patient Id");
		int p_id=scan.nextInt();
		System.out.println("please Enter the the Doctor Id");
		int d_id=scan.nextInt();
		System.out.println("please provide date in YYYY-MM-DD");
		String date=scan.next();
		
		try {
			if(patient.checkPatient(p_id)&& doctor.checkDoctor(d_id)) {
				if(checkDoctorAvailability(d_id,date,conn)){
					String query ="insert into appointmenttable (patient_id,doctor_id, appointment_date) values (?,?,?)";
					try {
						PreparedStatement pstmt=conn.prepareStatement(query);
						pstmt.setInt(1,p_id);
						pstmt.setInt(2, d_id);
						pstmt.setString(3, date);
					    int rowsEffect =pstmt.executeUpdate();
					    if(rowsEffect>0) {
					    	System.out.println("Appointment Booked");
					    }
					    else {
					    	System.out.println("Appointment can'nt  Book");
					    }
						
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					
				}
				else {
					System.out.println("Doctor is not Available For this Date");
				}
			
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
//		System.out.println("please provide date in YYYY-MM-DD");
//		String date=scan.next();
		
		
	}
	 public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection){
	        String query = "SELECT COUNT(*) FROM appointmenttable WHERE doctor_id = ? AND appointment_date = ?";
	        try{
	            PreparedStatement preparedStatement = connection.prepareStatement(query);
	            preparedStatement.setInt(1, doctorId);
	            preparedStatement.setString(2, appointmentDate);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            if(resultSet.next()){
	                int count = resultSet.getInt(1);
	                if(count==0){
	                    return true;
	                }else{
	                    return false;
	                }
	            }
	        } catch (SQLException e){
	            e.printStackTrace();
	        }
	        return false;
	    }
}
