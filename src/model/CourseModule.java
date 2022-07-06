package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CourseModule {
	private int moduleCode;
	private String moduleName;
	private int courseCode;
	
	public CourseModule(int mc) {
		moduleCode = mc;
	}
	
	public CourseModule(String n) {
		moduleName = n;
	}
	
	public CourseModule(int mc, String n) {
		moduleCode = mc;
		moduleName = n;
	}
	
	public CourseModule(int mc, String n, int cc) {
		moduleCode = mc;
		moduleName = n;
		courseCode = cc;
	}
	
	public int getModuleCode() {
		return moduleCode;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	
	public int getCourseCode() {
		return courseCode;
	}
	
	public static ArrayList<CourseModule> readAll(Connection conn) {
		ArrayList<CourseModule> modules = new ArrayList<>();
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM module;");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				modules.add( new CourseModule( rs.getInt("moduleCode"), rs.getString("moduleName") ) );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return modules;
	}
	
	public static int getNextAvailableModuleCode(Connection conn) {
		int nextModuleCode = 1;
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(moduleCode) AS moduleCode FROM module");
			if (rs.next()) {				
				nextModuleCode = (rs.getInt("moduleCode")) + 1;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
		return nextModuleCode;
	}
	
}
