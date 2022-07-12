package gui;

import vendor.ButtonColumn;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import model.Course;
import model.Registry;
import model.Student;
import model.TableObjectInterface;


public class RegistryWindow extends JFrame {
	
	private JFrame landingWindow;
	
	private Registry registry;
	private Student student;
	
	private ArrayList<TableObjectInterface> courses;
	private Map<String, Integer> courseNameCodeMap = new HashMap<>();
	
	private static final String STUDENT = "Student";
	private static final String LOGOUT = "Logout";
	
	private static final Font ROBOTO_BOLD_TITLE = new Font("roboto", Font.BOLD, 20);
	private static final Font ROBOTO_BOLD_SUB = new Font("roboto", Font.BOLD, 16);
	private static final Font ROBOTO_PLAIN_TITLE = new Font("roboto", Font.PLAIN, 16);
	private static final Font ROBOTO_PLAIN_SUB = new Font("roboto", Font.PLAIN, 14);

	private JPanel pnlContent;
	private JPanel pnlMenu;
	private JPanel pnlDate;
	private JPanel pnlCards;
	private JPanel pnlCardStudent;

	private LocalDate today;
	private JLabel lblDate;
	private JLabel lblDay;
	
	private JButton[] btnsMenu = new JButton[2];
	private JButton btnLogout;
	
	// Registry Components
	private JPanel pnlAddStudent;
	private JTable tblStudent;
	private JScrollPane scpStudent;
	private DefaultTableModel mdlStudent;
	private String[] StudentCols;
	private JTextField jtfStudentId;
	private JTextField jtfSurname,jtfUseraddress;
	private JTextField jtfName;
	private JTextField jtfEmail;
	private JTextField jtfPhone;
	private JPasswordField jtfPassword;
	private JLabel lblStudentId;
	private JLabel lblSurname,lblUseraddress;
	private JLabel lblName;
	private JLabel lblEmail;
	private JLabel lblPhone;
	private JLabel lblPassword;
	private JLabel lblcourse;
	private JButton btnAdd;
	private JComboBox<String> Coursecombo;
	private JPanel pnlSearchtop;
	private JTextField searchStud;
	private JButton search;
	
	
	public RegistryWindow(Registry user, JFrame frame) {
		super("Registry");
		landingWindow = frame;
		registry = user;
		
		//search part 
		pnlSearchtop = new JPanel();
		pnlSearchtop.setLayout(new FlowLayout(FlowLayout.CENTER));
		searchStud = new JTextField(15);
		searchStud.setPreferredSize(new Dimension(50,27));
		searchStud.setFont(ROBOTO_PLAIN_TITLE);
		search = new JButton("Search");
		search.setFont(ROBOTO_PLAIN_TITLE);
		pnlSearchtop.add(searchStud);
		pnlSearchtop.add(search);
		
		// Menu Panel
		pnlMenu = new JPanel(new GridLayout(0, 1));
		pnlMenu.setBackground(new Color(2, 24, 28));
		pnlMenu.setPreferredSize(new Dimension(150, 500));
		
		// Menu Top
		pnlDate = new JPanel(new FlowLayout());
		today = LocalDate.now();
		lblDay = new JLabel(today.getDayOfWeek().toString(), SwingConstants.RIGHT);
		lblDay.setFont(ROBOTO_BOLD_TITLE);
		lblDate = new JLabel(today.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)), SwingConstants.RIGHT);
		lblDate.setFont(ROBOTO_BOLD_SUB);
		pnlDate.add(lblDay);
		pnlDate.add(lblDate);
		pnlMenu.add(pnlDate);

		// Menu Items
		btnsMenu[0] = new JButton(STUDENT);
		btnsMenu[1] = btnLogout = new JButton(LOGOUT);

		for (JButton btnMenu : btnsMenu) {
			btnMenu.setHorizontalTextPosition(SwingConstants.CENTER);
			btnMenu.setBackground(new Color(2, 24, 28));
			btnMenu.setForeground(new Color(231, 233, 234));
			btnMenu.setFont(ROBOTO_BOLD_SUB);
			btnMenu.addActionListener(new MenuBtnActionHandler());
			
			btnMenu.getModel().addChangeListener( new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					ButtonModel model = (ButtonModel) e.getSource();
					if (model.isRollover()) {
						btnMenu.setBackground(new Color(27, 27, 27));
					} else {
						btnMenu.setBackground(new Color(2, 24, 28));
					}
				}
			});
			
			pnlMenu.add(btnMenu);
		}
		
		// Cards Panel
		pnlCards = new JPanel(new CardLayout());
		pnlCards.setBorder(BorderFactory.createEmptyBorder(7, 10, 7, 7));
		
		
		
		// Student Card Panel
		pnlCardStudent = new JPanel(new GridLayout(0, 1, 0, 5));
		
		StudentCols = new String[] {"StudentId","fname", "lname", "Address","Email", "Phone", "courseCode", "enrollDate", "Manage"};
		mdlStudent = new DefaultTableModel();
		mdlStudent.setColumnIdentifiers(StudentCols);
		
		tblStudent = new JTable() {
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return (column == 8);
			}
		};
		tblStudent.setModel(mdlStudent);
		tblStudent.setRowHeight(30);
		tblStudent.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblStudent.setFillsViewportHeight(true);
		tblStudent.setPreferredScrollableViewportSize(new Dimension(1000, 300));

		// Drop Student Action
        Action drop = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int modelRow = Integer.parseInt(e.getActionCommand());
				
				int studentId = (int) model.getValueAt(modelRow, 0);
				
				// Confirm delete
				// 0 OK
				// 2 CANCEL
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to drop student with id: " + studentId + "?", "Confirm Drop Student", JOptionPane.WARNING_MESSAGE);
				if (choice != JOptionPane.OK_OPTION) return; // CANCEL
				
				student = new Student(studentId);
				if (registry.dropStudent(student)) { // Update successful
					model.removeRow(modelRow);
				} else {
					JOptionPane.showMessageDialog(null, "Cannot Drop Student");
				}
								
			}//actionPerformed
			
		};
		
		// Set Drop button in table
		ButtonColumn buttonColumnStudent = new ButtonColumn(tblStudent, drop, 8);
		
		scpStudent = new JScrollPane(tblStudent);
		scpStudent.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(BevelBorder.LOWERED),
				"Student List",
				TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP,
				ROBOTO_BOLD_SUB)
			);
		scpStudent.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scpStudent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		pnlAddStudent = new JPanel(new GridBagLayout());
		pnlAddStudent.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(BevelBorder.LOWERED),
				"Add Student",
				TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP,
				ROBOTO_BOLD_SUB)
			);
		
		jtfStudentId = new JTextField(15);
		jtfStudentId.setFont(ROBOTO_PLAIN_SUB);
		jtfStudentId.setEditable(false);
		jtfSurname = new JTextField(15);
		jtfSurname.setFont(ROBOTO_PLAIN_SUB);
		jtfName = new JTextField(15);
		jtfName.setFont(ROBOTO_PLAIN_SUB);
		jtfEmail = new JTextField(15);
		jtfEmail.setFont(ROBOTO_PLAIN_SUB);
		jtfUseraddress = new JTextField(15);
		jtfUseraddress.setFont(ROBOTO_PLAIN_SUB);
		jtfPhone = new JTextField(15);
		jtfPhone.setFont(ROBOTO_PLAIN_SUB);
		jtfPassword = new JPasswordField(15);
		jtfPassword.setFont(ROBOTO_PLAIN_SUB);
		lblStudentId = new JLabel("StudentId");
		lblStudentId.setFont(ROBOTO_PLAIN_TITLE);
		lblSurname = new JLabel("Surname");
		lblSurname.setFont(ROBOTO_PLAIN_TITLE);
		lblName = new JLabel("Name");
		lblName.setFont(ROBOTO_PLAIN_TITLE);
		lblEmail = new JLabel("Email");
		lblEmail.setFont(ROBOTO_PLAIN_TITLE);
		lblUseraddress = new JLabel("Address");
		lblUseraddress.setFont(ROBOTO_PLAIN_TITLE);
		lblPhone = new JLabel("Phone");
		lblPhone.setFont(ROBOTO_PLAIN_TITLE);
		lblPassword = new JLabel("Password");
		lblPassword.setFont(ROBOTO_PLAIN_TITLE);
		btnAdd = new JButton("Add Student");
		btnAdd.setFont(ROBOTO_PLAIN_TITLE);
		//label for course
		lblcourse = new JLabel("Course");
		lblcourse.setFont(ROBOTO_PLAIN_TITLE);
			
		Coursecombo = new JComboBox<String>();
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.weightx = 100.0;
		gc.weighty = 100.0;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.anchor = GridBagConstraints.EAST;
		gc.fill = GridBagConstraints.BOTH;
		
		gc.gridx = 0;
		gc.gridy = 0;
		pnlAddStudent.add(lblStudentId, gc);
		
		gc.gridx = 0;
		gc.gridy = 1;
		pnlAddStudent.add(lblSurname, gc);
		
		gc.gridx = 0;
		gc.gridy = 2;
		pnlAddStudent.add(lblName, gc);
		
		gc.gridx = 0;
		gc.gridy = 3;
		pnlAddStudent.add(lblEmail, gc);
		
		gc.gridx = 0;
		gc.gridy = 4;
		pnlAddStudent.add(lblUseraddress, gc);
		
		
		gc.gridx = 0;
		gc.gridy = 5;
		pnlAddStudent.add(lblPhone, gc);
		
		gc.gridx = 0;
		gc.gridy = 6;
		pnlAddStudent.add(lblPassword, gc);
		
		gc.gridx = 0;
		gc.gridy = 7;
		pnlAddStudent.add(lblcourse, gc);
		
			
		gc.gridx = 1;
		gc.gridy = 0;
		gc.gridwidth = 2;
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.BOTH;
		pnlAddStudent.add(jtfStudentId, gc);
		
		gc.gridy = 1;
		pnlAddStudent.add(jtfSurname, gc);
		
		gc.gridy = 2;
		pnlAddStudent.add(jtfName, gc);
		
		gc.gridy = 3;
		pnlAddStudent.add(jtfEmail, gc);

		gc.gridy = 4;
		pnlAddStudent.add(jtfUseraddress, gc);
		
		gc.gridy = 5;
		pnlAddStudent.add(jtfPhone, gc);
		
		gc.gridy = 6;
		pnlAddStudent.add(jtfPassword, gc);
		
		gc.gridy = 7;
		pnlAddStudent.add(Coursecombo, gc);
		
		gc.gridx = 2;
		gc.gridy = 1;
		pnlAddStudent.add(Box.createHorizontalGlue(), gc);
		
		gc.gridy = 2;
		pnlAddStudent.add(Box.createHorizontalGlue(), gc);
		
		gc.gridy = 3;
		pnlAddStudent.add(Box.createHorizontalGlue(), gc);

		gc.gridy = 4;
		pnlAddStudent.add(Box.createHorizontalGlue(), gc);
		
		gc.gridy = 5;
		pnlAddStudent.add(Box.createHorizontalGlue(), gc);
		gc.gridy = 6;
		pnlAddStudent.add(Box.createHorizontalGlue(), gc);
		gc.gridy = 7;
		pnlAddStudent.add(Box.createHorizontalGlue(), gc);
		
		
		gc.gridx = 1;
		gc.gridy = 8;
		gc.gridwidth = 1;
		gc.anchor = GridBagConstraints.NORTHWEST;
		pnlAddStudent.add(btnAdd, gc);

		pnlCardStudent.add(scpStudent);
		
		pnlCardStudent.add(pnlAddStudent);
		pnlCards.add(pnlCardStudent, STUDENT);
		
		pnlContent = new JPanel(new BorderLayout());
		pnlContent.add(pnlSearchtop,BorderLayout.NORTH);
		pnlContent.add(pnlMenu, BorderLayout.LINE_START);
		pnlContent.add(pnlCards, BorderLayout.CENTER);
				
		// Populate student table
		populateTable(tblStudent, Student.readAll(registry.getUserConnection()));
		initializeJTextFieldAndComboBox();
		
		registerButtonListeners();
		
		// Confirm exit and logout
		addWindowListener( new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				// Confirm exit
				// 0 OK
				// 2 CANCEL
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to close the application", "Confirm Exit", JOptionPane.WARNING_MESSAGE);
				if (choice != JOptionPane.OK_OPTION) return; // CANCEL
				
				registry.logout();
				System.exit(0);
			}
		} );
		
		add(pnlContent);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void populateTable(JTable table, ArrayList<TableObjectInterface> objList) {
		// Clear any existing data
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		
		for (TableObjectInterface obj : objList) {
			model.addRow(obj.getObjectInfo());
		}
	}
	
	private void initializeJTextFieldAndComboBox() {
		jtfStudentId.setText( Integer.toString( Student.getNextAvailableStudentId(registry.getUserConnection()) ) );
		
		jtfSurname.setText("");
		jtfName.setText("");
		jtfEmail.setText("");
		jtfUseraddress.setText("");
		jtfPhone.setText("");
		jtfPassword.setText("");
		
		searchStud.setText("");
		
		// Set course combobox and course name-code map
		courses = Course.readAll(registry.getUserConnection());
		for (TableObjectInterface course : courses) {
			courseNameCodeMap.put( ((Course) course).getCourseName(), ((Course) course).getCourseCode());
		}
		
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(courseNameCodeMap.keySet().toArray( new String[courseNameCodeMap.size()] ));
		Coursecombo.setModel(model);
	}
	
	private void registerButtonListeners() {
		btnAdd.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int studentId = Integer.parseInt( jtfStudentId.getText() );
				String fname = jtfSurname.getText();
				String lname = jtfName.getText();
				String address = jtfUseraddress.getText();
				String email = jtfEmail.getText();
				int phoneNo = Integer.parseInt( jtfPhone.getText() );
				int courseCode = courseNameCodeMap.get( (String) Coursecombo.getSelectedItem() );
				LocalDate enrollDate = LocalDate.now();
				
				student = new Student(studentId, fname, lname, address, email, phoneNo, courseCode, enrollDate);
				
				String message = "Cannot add student";
				if ( registry.addStudent(student) ) { // Insert Successful
					message = "Student Added";
					mdlStudent.addRow( student.getObjectInfo() );
					initializeJTextFieldAndComboBox();
				}
				JOptionPane.showMessageDialog(null, message);
			}
		} );
		
		search.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String studentIdStr = searchStud.getText();
				if (studentIdStr.isEmpty()) return;
				
				boolean searchById = true;
				try {
					student = new Student( Integer.parseInt(studentIdStr) );
					
				} catch (NumberFormatException ex) {
					// Search by name
					student = new Student( studentIdStr );
					searchById = false;
				}
				
				if ( !student.read(registry.getUserConnection(), searchById) ) { // Invalid id
					JOptionPane.showMessageDialog(null,  "Student Not Found");
					return;
				}
				
				mdlStudent.setRowCount(0);
				mdlStudent.addRow(student.getObjectInfo());
			}
		} );
	}
		
	// Menu JButton Click Event Handler
	class MenuBtnActionHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// If LOGOUT, show confirm dialog
			if (e.getSource() == btnLogout) {
				// 0 OK
				// 2 CANCEL
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.WARNING_MESSAGE);
				if (choice != JOptionPane.OK_OPTION) return; // CANCEL
				
				registry.logout();
				landingWindow.setVisible(true);
				dispose();
				
			} else {
				populateTable(tblStudent, Student.readAll(registry.getUserConnection()));
				initializeJTextFieldAndComboBox();
			}
		}
		
	}
	
}

		