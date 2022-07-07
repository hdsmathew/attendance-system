package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class LandingWindow extends JFrame{
	
	public static final String ADMIN = "Admin";
	public static final String REGISTRY = "Registry";
	public static final String LECTURER = "Lecturer";
	
	private JLabel welcome,label;
	private JLabel datefield,datefield2;
	private JButton[] buttons;
	private JPanel top, north;
	private JPanel bottom;
	private JPanel buttonPlacement;
	private LoginWindow loginWindow;
	
	
	public LandingWindow() {
		super("Landing");
		LayoutManager layout = new BorderLayout();  
	    setLayout(layout);  
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(850,600);
		setLocationRelativeTo(null);
		setVisible(true);
		
		welcome = new JLabel("Welcome");
		welcome.setFont(new Font("Algerian",Font.BOLD,50));
		//welcome.setPreferredSize(new Dimension(200,180));
		welcome.setForeground(new Color(0,51,51));
		top = new JPanel();
		top.setPreferredSize(new Dimension(120,120));
		top.setBorder(new BevelBorder(BevelBorder.RAISED));
	    //top.setBorder(new LineBorder (Color.black, 3));
		top.setLayout(new BorderLayout());
		top.setBackground(new Color(95,158,160));
		top.add(welcome,BorderLayout.WEST);
		
		
		//date n time
		datefield = new JLabel();
		datefield2 = new JLabel();
		
		north = new JPanel();
		north.setLayout(new GridLayout(2,1));
		north.setBackground(new Color(95,158,160));
	
		Date date=new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String dayWeekText = new SimpleDateFormat("EEEE").format(date);
        String strDateFormat = "dd-MMM-yyyy"; //Date format is Specified
        SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
        datefield.setText(dayWeekText);
        datefield.setFont(new Font("Algerian",Font.BOLD,40));
		datefield.setForeground(new Color(0,51,51));
        datefield2.setText(objSDF.format(date));
        datefield2.setFont(new Font("Castellar",Font.BOLD,30));
		datefield2.setForeground(new Color(0,51,51));
		
        north.add(datefield);
        north.add(datefield2);
        top.add(north,BorderLayout.EAST);
				
        label = new JLabel("");
		//the bottom part
		bottom = new JPanel();
		bottom.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPlacement = new JPanel();
		buttonPlacement.setLayout(new GridLayout(4,1,35,35));
		buttonPlacement.add(label);
		
		//initialise the button part
		buttons = new JButton[3];
		
		for(int i = 0; i<3; i++) {
			buttons[i] = new JButton();
			
		}
		buttons[0] = new JButton(ADMIN);
		buttons[1] = new JButton(REGISTRY);
		buttons[2] = new JButton(LECTURER);
		
		for(int i = 0; i<3; i++) {
			buttons[i].setBackground(new Color(47,79,79));
			buttons[i].setFont(new Font("Castellar",Font.BOLD,35));
			buttons[i].setForeground(Color.WHITE);
			buttonPlacement.add(buttons[i]);
		}
		
		bottom.add(buttonPlacement);
		
		add(top,BorderLayout.NORTH);
		add(bottom, BorderLayout.CENTER);
		MyHandler ah = new MyHandler();
		buttons[0].addActionListener(ah);
		buttons[1].addActionListener(ah);
		buttons[2].addActionListener(ah);
		
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
			String titlePassed = ac.getActionCommand();
			LoginWindow loginWindow = new LoginWindow(titlePassed, LandingWindow.this);
			
			setVisible(false);
			loginWindow.setVisible(true);
		}
	}

}