import gui.AdminWindow;
import gui.RegistryWindow;
import model.Admin;
import model.Registry;

public class Main {
	public static void main(String[] args) {
		Registry registry = new Registry(2, "1234");
		registry.login();
		new RegistryWindow(registry);
	}
}
