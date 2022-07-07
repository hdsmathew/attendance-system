package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Registry extends User implements TableObjectInterface {
	
	private String fname;
	private String lname;
	private int phoneNo;
	private String email;
	
	public Registry(int userId) {
		super(userId);
	}
	
	public Registry(int userId, String password) {
		super(userId, password);
	}
	
	public Registry(int userId, String f, String l, int p, String e, String password) {
		super(userId, password);
		fname = f;
		lname = l;
		phoneNo = p;
		email = e;
	}
	
	public Registry(int id, String f, String l, int p, String e) {
		super.userId = id;
		fname = f;
		lname = l;
		phoneNo = p;
		email = e;
	}
	
	public String toString() {
		return String.format("%d | %s | %s | %s | %d", super.userId, fname, lname, email, phoneNo);
	}
	
	@Override
	public Object[] getObjectInfo() {
		return new Object[] {super.userId, fname + " " + lname, email, phoneNo, "Delete"};
	}
	
	public boolean addStudent(Student s) {
		return s.add(this.conn);
	}
	
	public boolean dropStudent(Student s) {
		return s.update(this.conn);
	}
	
	public boolean add(Connection conn) {
		
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO administration (fname, lname, phoneNo, email, password, type) VALUES (?, ?, ?, ?, ?, 'registry');");
			stmt.setString(1, this.fname);
			stmt.setString(2, this.lname);
			stmt.setInt(3, this.phoneNo);
			stmt.setString(4, this.email);
			stmt.setString(5, super.password);
			
			return (stmt.executeUpdate() == 1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean delete(Connection conn) {
		
		try {
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM administration WHERE id = ?;");
			stmt.setInt(1, super.userId);
			stmt.executeUpdate();
			
			stmt = conn.prepareStatement("ALTER TABLE administration AUTO_INCREMENT = ?;");
			stmt.setInt(1, Registry.getNextAvailableUserId(conn));
			stmt.executeUpdate();
						
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static ArrayList<TableObjectInterface> readAll(Connection conn) {
		ArrayList<TableObjectInterface> registry = new ArrayList<>();
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM administration WHERE type LIKE 'registry';");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				registry.add( new Registry( rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getInt("phoneNo"), rs.getString("email") ) );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return registry;
	}
	
	public static int getNextAvailableUserId(Connection conn) {
		int nextUserId = 1;
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS id FROM administration;");
			if (rs.next()) {
				nextUserId = (rs.getInt("id")) + 1;				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
		return nextUserId;
	}

}
