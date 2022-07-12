package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class Student implements TableObjectInterface {
	
	private int studentId;
	private String fname;
	private String lname;
	private String address;
	private String email;
	private int phoneNo;
	private int courseCode;
	private LocalDate enrollDate;
	
	public Student(int id) {
		studentId = id;
	}
	
	public Student(String searchName) {
		this.fname = searchName;
	}
	
	public Student(int id, String fname, String lname) {
		studentId = id;
		this.fname = fname;
		this.lname = lname;
	}
	
	public Student(int id, String fname, String lname, String address, String email, int phoneNo, int courseCode, LocalDate enrollDate) {
		this.studentId = id;
		this.fname = fname;
		this.lname = lname;
		this.address = address;
		this.email = email;
		this.phoneNo = phoneNo;
		this.courseCode = courseCode;
		this.enrollDate = enrollDate;
	}
	
	public int getStudentId() {
		return studentId;
	}
	
	public String getStudentName() {
		return fname + " " + lname;
	}
	
	public int getCourseCodeEnrolled() {
		return courseCode;
	}
	
	@Override
	public Object[] getObjectInfo() {
		return new Object[] {studentId, fname, lname, address, email, phoneNo, courseCode, enrollDate, "Drop"};
	}
	
	public boolean read(Connection conn, boolean searchById) {
		
		try {
			PreparedStatement stmt;
			
			if (searchById) {
				stmt = conn.prepareStatement("SELECT * FROM student s INNER JOIN enrollment e ON s.studentID = e.studentID WHERE s.studentID = ? AND active = 1;");
				stmt.setInt(1, this.studentId);
			} else {
				stmt = conn.prepareStatement("SELECT * FROM student s INNER JOIN enrollment e ON s.studentID = e.studentID WHERE (CONCAT(s.fname, ' ', s.lname) LIKE ?) OR (CONCAT(s.lname, ' ', s.fname) LIKE ?) AND active = 1 LIMIT 1;");
				stmt.setString(1, "%" + this.fname + "%");
				stmt.setString(2, "%" + this.fname + "%");
			}
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				this.studentId = rs.getInt("studentID");
				this.fname = rs.getString("fname");
				this.lname = rs.getString("lname");
				this.address = rs.getString("address");
				this.email = rs.getString("email");
				this.phoneNo = rs.getInt("phoneNo");
				this.courseCode = rs.getInt("courseCode");
				this.enrollDate = rs.getObject("enrollDate", LocalDate.class);
				
				return true;
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean add(Connection conn) {
		
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO student (fname, lname, address, phoneNo, email) VALUES (?, ?, ?, ?, ?);");
			stmt.setString(1, this.fname);
			stmt.setString(2, this.lname);
			stmt.setString(3, this.address);
			stmt.setInt(4, this.phoneNo);
			stmt.setString(5, this.email);
			stmt.executeUpdate();
			
			stmt = conn.prepareStatement("INSERT INTO enrollment (studentID, courseCode, enrollDate) VALUES (?, ?, ?);");
			stmt.setInt(1, this.studentId);
			stmt.setInt(2, courseCode);
			stmt.setObject(3, enrollDate);
			
			return (stmt.executeUpdate() == 1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean update(Connection conn) {
		
		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE student set active = 0 WHERE studentID = ?;");
			stmt.setInt(1, this.studentId);
			
			return (stmt.executeUpdate() == 1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<TableObjectInterface> readAll(Connection conn) {
		ArrayList<TableObjectInterface> students = new ArrayList<>();
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM student s INNER JOIN enrollment e ON s.studentID = e.studentID WHERE active = 1;");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				students.add( new Student( rs.getInt("studentID"), rs.getString("fname"), rs.getString("lname"), rs.getString("address"), rs.getString("email"), rs.getInt("phoneNo"), rs.getInt("courseCode"), rs.getObject("enrollDate", LocalDate.class)) );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return students;
	}
	
	public static ArrayList<TableObjectInterface> readAll(Connection conn, int moduleCode) {
		ArrayList<TableObjectInterface> students = new ArrayList<>();
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM student s INNER JOIN enrollment e ON s.studentID = e.studentID INNER JOIN module m ON e.courseCode = m.courseCode WHERE s.active = 1 AND m.moduleCode = ?;");
			stmt.setInt(1, moduleCode);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				students.add( new Student( rs.getInt("studentID"), rs.getString("fname"), rs.getString("lname"), rs.getString("address"), rs.getString("email"), rs.getInt("phoneNo"), rs.getInt("courseCode"), rs.getObject("enrollDate", LocalDate.class)) );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return students;
	}
	
	public static int getNextAvailableStudentId(Connection conn) {
		int nextStudentId = 1;
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(studentID) AS studentId FROM student;");
			if (rs.next()) {
				nextStudentId = (rs.getInt("studentId")) + 1;				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
		return nextStudentId;
	
	}
}