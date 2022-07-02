package model;

public class Admin extends User {
	
	public Admin(int userId, String password) {
		super(userId, password);
	}
	
	public boolean addCourse(Course c) {
		return c.add(this.conn);
	}
	
	public boolean updateCourse(Course c) {
		return c.update(this.conn);
	}
}
