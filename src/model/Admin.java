package model;

public class Admin extends User {
	
	public Admin(int userId, String password) {
		super(userId, password);
	}
	
	public boolean addRegistry(Registry r) {
		return r.add(this.conn);
	}
	
	public boolean deleteRegistry(Registry r) {
		return r.delete(this.conn);
	}
	
	public boolean addLecturer(Lecturer l) {
		return l.add(this.conn);
	}
	
	public boolean updateLecturer(Lecturer l) {
		return l.update(this.conn);
	}
	
	public boolean assignModuleToLecturer(Lecturer l, int moduleCode) {
		
		if (l.teaches(moduleCode)) return false;
		
		return l.assignModule(this.conn, moduleCode);
	}
	
	public boolean addCourse(Course c) {
		return c.add(this.conn);
	}
	
	public boolean updateCourse(Course c) {
		return c.update(this.conn);
	}
}
