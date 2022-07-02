package model;

import config.Database;

import java.sql.Connection;
import java.sql.SQLException;


public class User {
	private int userId;
	private String password;
	protected Connection conn;
	
	public User() {
		userId = -1;
		password = "";
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
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public Connection getUserConnection() {
		return conn;
	}
	
}
