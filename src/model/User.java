package model;

import java.sql.Connection;
import java.sql.SQLException;


public abstract class User {
	protected int userId;
	protected String password;
	protected Connection conn;
	
	public User() {
	}
	
	public User(int id) {
		userId = id;
		conn = null;
	}
	
	public User(int id, String p) {
		userId = id;
		password = p;
		conn = null;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setPassword(String password ) {
		this.password = password;
	}
	
	public boolean isPasswordValid(String p) {
		return this.password.equals(p);
	}
	
	public abstract boolean login();
	
	public boolean logout() {
		
		try {
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public Connection getUserConnection() {
		return conn;
	}
	
}
