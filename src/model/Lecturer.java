package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import config.Database;

public class Lecturer extends User implements TableObjectInterface {
	
	private String fname;
	private String lname;
	private String email;
	private String address;
	private int phoneNo;
	private String teachingStatus;
	private ArrayList<CourseModule> modulesTaught;
	
	public Lecturer() {}
	
	public Lecturer(int userId) {
		super(userId);
		modulesTaught = new ArrayList<>();
	}
	
	public Lecturer(int userId, String status) {
		super(userId);
		teachingStatus = status;
	}
	
	public Lecturer(int userId, String f, String l, String e, String a, int p, String s) {
		super(userId);
		fname = f;
		lname = l;
		email = e;
		address = a;
		phoneNo = p;
		teachingStatus = s;
	}
	
	public Lecturer(int userId, String f, String l, String e, String a, int p, String s, String password) {
		super(userId, password);
		fname = f;
		lname = l;
		email = e;
		address = a;
		phoneNo = p;
		teachingStatus = s;
	}
	
	@Override
	public boolean login() {
		super.conn = Database.getConnection();
		
		if (conn == null) return false;
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM lecturer WHERE lectID = ?;");
			stmt.setInt(1, super.userId);
			ResultSet rs = stmt.executeQuery();
			
			if (!rs.next()) return false;
			
			String password = rs.getString("password");
			return super.isPasswordValid(password);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public int getId() {
		return super.userId;
	}
	
	public String getFullName() {
		return fname + " " + lname;
	}
	
	public boolean isActive() {
		return teachingStatus.equalsIgnoreCase("active");
	}
	
	public boolean teaches(int moduleCode) {
		for (CourseModule module : modulesTaught) {
			if (module.getModuleCode() == moduleCode) return true; 
		}
		
		return false;
	}

	@Override
	public Object[] getObjectInfo() {
		return new Object[] {super.userId, fname, lname, email, address, phoneNo, teachingStatus};
	}
	
	public boolean assignModule(Connection conn, int moduleCode) {
		
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO teach (lectID, moduleCode) VALUES (?, ?);");
			stmt.setInt(1, super.userId);
			stmt.setInt(2, moduleCode);
			
			return (stmt.executeUpdate() == 1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean read(Connection conn) {
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM lecturer l INNER JOIN teach t ON l.lectID = t.lectID WHERE l.lectID = ?;");
			stmt.setInt(1, super.userId);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				this.fname = rs.getString("fname");
				this.lname = rs.getString("lname");
				this.address = rs.getString("address");
				this.email = rs.getString("email");
				this.phoneNo = rs.getInt("phoneNo");
				this.teachingStatus = (rs.getInt("teachingStatus") == 1) ? "Active" : "Not Active";
				
				modulesTaught.add( new CourseModule( rs.getInt("moduleCode")) );
				
				while (rs.next()) {
					modulesTaught.add( new CourseModule( rs.getInt("moduleCode")) );
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
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO lecturer (fname, lname, phoneNo, email, address, password) VALUES (?, ?, ?, ?, ?, ?);");
			stmt.setString(1, this.fname);
			stmt.setString(2, this.lname);
			stmt.setInt(3, this.phoneNo);
			stmt.setString(4, this.email);
			stmt.setString(5, address);
			stmt.setString(6, super.password);
			
			return (stmt.executeUpdate() == 1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean update(Connection conn) {
		
		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE lecturer SET teachingStatus = ? WHERE lectID = ?;");
			stmt.setInt(1, (this.teachingStatus.equalsIgnoreCase("active")) ? 1 : 0 );
			stmt.setInt(2, super.userId);
			
			return (stmt.executeUpdate() == 1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<TableObjectInterface> readAll(Connection conn) {
		ArrayList<TableObjectInterface> lecturers = new ArrayList<>();
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM lecturer;");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				lecturers.add( new Lecturer( rs.getInt("lectID"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("address"), rs.getInt("phoneNo"), (rs.getInt("teachingStatus") == 1) ? "Active" : "Not Active" ) );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lecturers;
	}
	
	public static int getNextAvailableLecturerId(Connection conn) {
		int nextLecturerId = 1;
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(lectID) AS id FROM lecturer");
			if (rs.next()) {
				nextLecturerId = (rs.getInt("id")) + 1;				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
		return nextLecturerId;
	}
	
}
