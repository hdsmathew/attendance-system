package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Attendance {
	
	private int lecturerId;
	private int moduleCode;
	private int studentId;
	private LocalDate date;
	private boolean present;
	
	public Attendance(int lid, int mc, int sid, LocalDate d, boolean p) {
		lecturerId = lid;
		moduleCode = mc;
		studentId = sid;
		date = d;
		present = p;
	}
	
	public String toString() {
		return "StudId: " + studentId + " | ModuleCode: " + moduleCode + " | Date: " + date + " | Present: " + present;
	}
	
	public boolean add(Connection conn) {
		
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO attendance (lectID, moduleCode, studentID, date, present) VALUES (?, ?, ?, ?, ?);");
			stmt.setInt(1, this.lecturerId);
			stmt.setInt(2, this.moduleCode);
			stmt.setInt(3, this.studentId);
			stmt.setObject(4, this.date);
			stmt.setInt(5, (this.present) ? 1 : 0);
			
			return (stmt.executeUpdate() == 1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<Attendance> readAttendance(Connection conn, Student s) {
		ArrayList<Attendance> attendances = new ArrayList<>();
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM attendance WHERE studentID = ?;");
			stmt.setInt(1, s.getStudentId());
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				attendances.add( new Attendance(rs.getInt("lectID"), rs.getInt("moduleCode"), rs.getInt("studentID"), rs.getObject("date", LocalDate.class), rs.getBoolean("present")) );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return attendances;
	}
	
	public static ArrayList<Attendance> readAttendance(Connection conn, CourseModule m) {
		ArrayList<Attendance> attendances = new ArrayList<>();
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM attendance WHERE moduleCode = ?;");
			stmt.setInt(1, m.getModuleCode());
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				attendances.add( new Attendance(rs.getInt("lectID"), rs.getInt("moduleCode"), rs.getInt("studentID"), rs.getObject("date", LocalDate.class), rs.getBoolean("present")) );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return attendances;
	}
	
	public static ArrayList<Attendance> readAttendance(Connection conn, CourseModule m, LocalDate from, LocalDate to) {
		ArrayList<Attendance> attendances = new ArrayList<>();
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM attendance WHERE moduleCode = ? AND date BETWEEN ? AND ?;");
			stmt.setInt(1, m.getModuleCode());
			stmt.setObject(2, from);
			stmt.setObject(3, to);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				attendances.add( new Attendance(rs.getInt("lectID"), rs.getInt("moduleCode"), rs.getInt("studentID"), rs.getObject("date", LocalDate.class), rs.getBoolean("present")) );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return attendances;
	}

}