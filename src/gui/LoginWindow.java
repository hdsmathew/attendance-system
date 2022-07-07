package gui;

import javax.swing.*;

import model.Admin;
import model.Lecturer;
import model.Registry;
import model.User;

import java.awt.*;
import java.awt.event.*;

public class LoginWindow extends JFrame {
	
	private JLabel idL,title,passwordL;
	private JPasswordField password;
	private JTextField id;
	private JButton switchUser,login;
	private LandingWindow landingWindow;
	
	public LoginWindow(String userType, JFrame previousWindow) {
		super("Login");
		LayoutManager layout = new BorderLayout();  
	    setLayout(layout);  
		
		this.title = new JLabel(userType);
		this.title.setFont(new Font("Algerian",Font.BOLD,45));
		this.title.setForeground(new Color(0,51,51));
		idL = new JLabel("ID ");
		idL.setFont(new Font("Castellar",Font.BOLD,25));
		passwordL = new JLabel("PASSWORD ");
		passwordL.setFont(new Font("Castellar",Font.BOLD,25));
		
		password = new JPasswordField(20);
		id = new JTextField (20);
		
		login = new JButton("Login");
		switchUser = new JButton("<< Switch User");
		
		login.setBackground(new Color(47,79,79));
		login.setFont(new Font("Castellar",Font.BOLD,30));
		login.setForeground(Color.WHITE);
		
		switchUser.setBackground(new Color(47,79,79));
		switchUser.setFont(new Font("Castellar",Font.BOLD,15));
		switchUser.setForeground(Color.WHITE);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel outer = new JPanel();
		outer.setLayout(new GridLayout(3,1,25,25));
		
		JPanel inner = new JPanel();
	
		inner.setLayout(new GridLayout(2,2,25,25));
		
		JPanel loginButtonPanel = new JPanel();
		loginButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		
		inner.add(idL);
		inner.add(id);
		
		inner.add(passwordL);
		inner.add(password);
		titlePanel.add(this.title);
		titlePanel.add(new JLabel(""));
		outer.add(titlePanel);
		outer.add(inner);
		loginButtonPanel.add(new JLabel(""));
		loginButtonPanel.add(new JLabel(""));
		loginButtonPanel.add(login);
		outer.add(loginButtonPanel);
			
		JPanel center = new JPanel();
		center.setLayout(new FlowLayout(FlowLayout.CENTER));
		center.add(outer);
		
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout(FlowLayout.LEFT));
		top.setPreferredSize(new Dimension(120,120));
		top.add(switchUser);
		top.add(new JLabel(""));
		
		
		add(top,BorderLayout.NORTH);
		add(center,BorderLayout.CENTER);
		
		this.landingWindow = (LandingWindow) previousWindow;
		
		MyHandler ah = new MyHandler();
		switchUser.addActionListener(ah);
		
		login.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				User user = null;
				JFrame nextWindow;
				
				int userId = Integer.parseInt( id.getText() );
				String passwordStr = String.valueOf( password.getPassword() );
				
				switch (userType) {
				case LandingWindow.ADMIN:
					user = new Admin();
					break;
				case LandingWindow.REGISTRY:
					user = new Registry();
					break;
				case LandingWindow.LECTURER:
					user = new Lecturer();
					break;
				default:
					break;
				}
				
				user.setUserId(userId);
				user.setPassword(passwordStr);
				
				if (!user.login()) {
					JOptionPane.showMessageDialog(null, "Invalid Credential", "Login Error", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				switch (userType) {
				case LandingWindow.ADMIN:
					new AdminWindow( (Admin) user, LoginWindow.this.landingWindow );
					break;
				case LandingWindow.REGISTRY:
					new RegistryWindow( (Registry) user, LoginWindow.this.landingWindow );
					break;
				case LandingWindow.LECTURER:
					new AdminWindow( (Admin) user, LoginWindow.this.landingWindow );
					break;
				default:
					break;
				}
				dispose();
			}
		} );
		
		// Confirm exit
		addWindowListener( new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				// Confirm exit
				// 0 OK
				// 2 CANCEL
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to close the application", "Confirm Exit", JOptionPane.WARNING_MESSAGE);
				if (choice != JOptionPane.OK_OPTION) return; // CANCEL
				
				System.exit(0);
			}
		} );
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setPreferredSize(new Dimension(850,600));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	public class MyHandler implements ActionListener{
		public void actionPerformed(ActionEvent ac) {
			landingWindow.setVisible(true);
			dispose();
		}
	}
	
}
