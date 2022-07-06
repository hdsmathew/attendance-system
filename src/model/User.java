package model;

import config.Database;

import java.sql.Connection;
import java.sql.SQLException;


public class User {
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
	
	public boolean login() {
		conn = Database.getConnection();
		
		return ( conn != null );
	}
	
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
