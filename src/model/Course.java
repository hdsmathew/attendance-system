package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Course implements TableObjectInterface {
	private int courseCode;
	private String courseName;
	private CourseModule[] modules;
	private String onOffer;
	
	public Course(int cc, String o) {
		courseCode = cc;
		onOffer = o;
	}
	
	public Course(int cc, String n, String o) {
		courseCode = cc;
		courseName = n;
		onOffer = o;
	}
	
	public Course(int cc, String n, CourseModule[] m, String o) {
		courseCode = cc;
		courseName  = n;
		modules = m;
		onOffer = o;
	}
	
	@Override
	public Object[] getObjectInfo() {
		return new Object[] {courseCode, courseName, onOffer};
	}
	
	public String toString() {
		return courseCode + " | " + courseName + " | " + onOffer;
	}
	
	public boolean add(Connection conn) {
		
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO course (courseName) VALUES (?);");
			stmt.setString(1, this.courseName);
			stmt.executeUpdate();
			
			stmt = conn.prepareStatement("INSERT INTO module (moduleName, courseCode) VALUES (?, ?);");
			for (CourseModule m : this.modules) {
				stmt.setString(1, m.getModuleName());
				stmt.setInt(2, this.courseCode);
				stmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean update(Connection conn) {
		
		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE course SET onOffer = ? WHERE courseCode = ?;");
			stmt.setInt(1, (this.onOffer.equalsIgnoreCase("available")) ? 1 : 0 );
			stmt.setInt(2, this.courseCode);
			
			return (stmt.executeUpdate() == 1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<TableObjectInterface> readAll(Connection conn) {
		ArrayList<TableObjectInterface> courses = new ArrayList<>();
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM course;");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				courses.add( new Course(rs.getInt("courseCode"), rs.getString("courseName"), (rs.getInt("onOffer") == 1) ? "Available" : "Not Available") );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return courses;
	}
	
	public static int getNextAvailableCourseCode(Connection conn) {
		int nextCourseCode = 1;
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(courseCode) AS courseCode FROM course");
			if (rs.next()) {
				nextCourseCode = (rs.getInt("courseCode")) + 1;				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
		return nextCourseCode;
	}
	
}
