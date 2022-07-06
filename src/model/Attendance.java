package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Attendance {
	
	private int lecturerId;
	private CourseModule module;
	private Student student;
	private LocalDate date;
	private boolean present;
	
	public Attendance(int lid, CourseModule m, Student s, LocalDate d, boolean p) {
		lecturerId = lid;
		module = m;
		student = s;
		date = d;
		present = p;
	}
	
	public Student getStudent() {
		return student;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public int getLecturerId() {
		return lecturerId;
	}
	
	public boolean getPresence() {
		return present;
	}
	
	public CourseModule getModule() {
		return module;
	}
	
	public String toString() {
		return "StudId: " + student.getStudentId() + " | ModuleCode: " + module.getModuleCode() + " | Date: " + date + " | Present: " + present;
	}
	
	public boolean add(Connection conn) {
		
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO attendance (lectID, moduleCode, studentID, date, present) VALUES (?, ?, ?, ?, ?);");
			stmt.setInt(1, this.lecturerId);
			stmt.setInt(2, this.module.getModuleCode());
			stmt.setInt(3, this.student.getStudentId());
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
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM attendance a INNER JOIN student s ON a.studentID = s.studentID INNER JOIN module m ON a.moduleCode = m.moduleCode WHERE s.studentID = ? AND s.active = 1 ORDER BY m.moduleCode, a.date;");
			stmt.setInt(1, s.getStudentId());
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				attendances.add( new Attendance(rs.getInt("lectID"), new CourseModule(rs.getInt("moduleCode"), rs.getString("moduleName")), new Student(rs.getInt("studentID"), rs.getString("fname"), rs.getString("lname")), rs.getObject("date", LocalDate.class), rs.getBoolean("present")) );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return attendances;
	}
	
	public static ArrayList<Attendance> readAttendance(Connection conn, CourseModule m) {
		ArrayList<Attendance> attendances = new ArrayList<>();
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM attendance a INNER JOIN student s ON a.studentID = s.studentID INNER JOIN module m ON a.moduleCode = m.moduleCode WHERE m.moduleCode = ? AND s.active = 1 ORDER BY a.date, s.studentID;");
			stmt.setInt(1, m.getModuleCode());
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				attendances.add( new Attendance(rs.getInt("lectID"), new CourseModule(rs.getInt("moduleCode"), rs.getString("moduleName")), new Student(rs.getInt("studentID"), rs.getString("fname"), rs.getString("lname")), rs.getObject("date", LocalDate.class), rs.getBoolean("present")) );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return attendances;
	}
	
	public static ArrayList<Attendance> readAttendance(Connection conn, CourseModule m, LocalDate from, LocalDate to) {
		ArrayList<Attendance> attendances = new ArrayList<>();
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM attendance a INNER JOIN student s ON a.studentID = s.studentID INNER JOIN module m ON a.moduleCode = m.moduleCode WHERE m.moduleCode = ? AND s.active = 1 AND a.date BETWEEN ? AND ? ORDER BY a.date, s.studentID;");
			stmt.setInt(1, m.getModuleCode());
			stmt.setObject(2, from);
			stmt.setObject(3, to);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				attendances.add( new Attendance(rs.getInt("lectID"), new CourseModule(rs.getInt("moduleCode"), rs.getString("moduleName")), new Student(rs.getInt("studentID"), rs.getString("fname"), rs.getString("lname")), rs.getObject("date", LocalDate.class), rs.getBoolean("present")) );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return attendances;
	}

}