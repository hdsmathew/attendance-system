import gui.AdminWindow;
import model.Admin;

public class Main {
	public static void main(String[] args) {
		Admin admin = new Admin(1, "1234");
		admin.login();
		new AdminWindow(admin);
	}
}
