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
	
	public Course(int cc) {
		courseCode = cc;
	}
	
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
	
	public int getCourseCode() {
		return courseCode;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public String[] getModuleNames() {
		String[] moduleNames = new String[3];
		
		for (int i = 0; i < 3; i++) {
			moduleNames[i] = modules[i].getModuleName();			
		}
		
		return moduleNames;
	}
	
	public CourseModule[] getModules() {
		return modules;
	}
	
	@Override
	public Object[] getObjectInfo() {
		return new Object[] {courseCode, courseName, onOffer};
	}
	
	public String toString() {
		return courseCode + " | " + courseName + " | " + onOffer;
	}
	
	public boolean read(Connection conn) {
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM course WHERE courseCode = ?;");
			stmt.setInt(1, this.courseCode);
			ResultSet rs = stmt.executeQuery();
			
			this.modules = new CourseModule[3];
			if (rs.next()) {
				this.courseName = rs.getString("courseName");
				this.onOffer = (rs.getInt("onOffer") == 1) ? "Available" : "Not Available";
				
				stmt = conn.prepareStatement("SELECT * FROM module WHERE courseCode = ?;");
				stmt.setInt(1, this.courseCode);
				rs = stmt.executeQuery();
				
				int i = 0;
				while (rs.next()) {
					this.modules[i++] = new CourseModule(rs.getInt("moduleCode"), rs.getString("moduleName"));
				}
				
				return true;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
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
			PreparedStatement moduleStmt = conn.prepareStatement("SELECT * FROM module WHERE courseCode = ?;");
			ResultSet moduleRs;
			PreparedStatement courseStmt = conn.prepareStatement("SELECT * FROM course;");
			ResultSet rs = courseStmt.executeQuery();
			
			while (rs.next()) {
				moduleStmt.setInt(1, rs.getInt("courseCode"));
				moduleRs = moduleStmt.executeQuery();
				CourseModule[] modules = new CourseModule[3];
				int i = 0;
				
				while (moduleRs.next()) {
					modules[i++] = new CourseModule(moduleRs.getInt("moduleCode"), moduleRs.getString("moduleName"));
				}
				courses.add( new Course(rs.getInt("courseCode"), rs.getString("courseName"), modules, (rs.getInt("onOffer") == 1) ? "Available" : "Not Available") );
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
