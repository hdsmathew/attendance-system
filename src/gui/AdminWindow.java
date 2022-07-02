package gui;

import vendor.DatePick;
import vendor.ButtonColumn;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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

import model.Admin;
import model.Course;
import model.CourseModule;
import model.TableObjectInterface;

public class AdminWindow extends JFrame {
	
	private Admin admin;
	private Course course;
	
	public static final String REGISTRY = "Registry";
	public static final String LECTURER = "Lecturer";
	public static final String COURSE = "Course";
	public static final String ATTENDANCE = "Attendance";
	public static final String LOGOUT = "Logout";
	
	public static final Font ROBOTO_BOLD_TITLE = new Font("roboto", Font.BOLD, 20);
	public static final Font ROBOTO_BOLD_SUB = new Font("roboto", Font.BOLD, 16);
	public static final Font ROBOTO_PLAIN_TITLE = new Font("roboto", Font.PLAIN, 16);
	public static final Font ROBOTO_PLAIN_SUB = new Font("roboto", Font.PLAIN, 14);

	
	public JPanel pnlContent;
	public JPanel pnlMenu;
	public JPanel pnlDate;
	public JPanel pnlCards;
	public JPanel pnlCardRegistry;
	public JPanel pnlCardLecturer;
	public JPanel pnlCardCourse;
	public JPanel pnlCardAttendance;

	
	public LocalDate today;
	public JLabel lblDate;
	public JLabel lblDay;
	
	public JButton[] btnsMenu = new JButton[5];
	public JButton btnRegistry;
	public JButton btnLecturer;
	public JButton btnCourse;
	public JButton btnAttendance;
	public JButton btnLogout;
	
	public GridBagConstraints gc;
	
	// Registry Components
	public JPanel pnlAddRegistry;
	public JPanel pnlRegistryList;
	public JTable tblRegistryList;
	public JScrollPane scpRegistryList;
	public DefaultTableModel mdlRegistryList;
	public String[] registryCols;
	public JTextField jtfUserId;
	public JTextField jtfName;
	public JTextField jtfEmail;
	public JTextField jtfPhone;
	public JPasswordField jtfPassword;
	public JLabel lblUserId;
	public JLabel lblName;
	public JLabel lblEmail;
	public JLabel lblPhone;
	public JLabel lblPassword;
	public JButton btnAdd;
	
	// Lecturer Components
	public JPanel pnlManageLecturer;
	public JPanel pnlAddLecturer;
	public JPanel pnlAssignLecturer;
	public JPanel pnlLecturerList;
	public JTable tblLecturerList;
	public JScrollPane scpLecturerList;
	public DefaultTableModel mdlLecturerList;
	public String[] lecturerCols;
	public String[] lecturerNames;
	public int[] lecturerIds;
	public int[] courseCodes;
	public int[] moduleCodes;
	public String[] courseNames;
	public String[] moduleNames;
	public JComboBox<String> jcbAssignLectNames;
	public JComboBox<String> jcbAssignCourseNames;
	public JComboBox<String> jcbAssignModuleNames;
	public JLabel lblAssignLect;
	public JLabel lblAssignCourse;
	public JLabel lblAssignModule;
	public JTextField jtfLectId;
	public JTextField jtfLectFname;
	public JTextField jtfLectLname;
	public JTextField jtfLectEmail;
	public JTextField jtfLectAddr;
	public JTextField jtfLectPhone;
	public JPasswordField jtfLectPassword;
	public JLabel lblLectId;
	public JLabel lblLectFname;
	public JLabel lblLectLname;
	public JLabel lblLectEmail;
	public JLabel lblLectAddr;
	public JLabel lblLectPhone;
	public JLabel lblLectPassword;
	public JButton btnAddLect;
	public JButton btnAssignLect;
	
	// Course Components
	public JPanel pnlAddCourse;
	public JPanel pnlCourseList;
	public JTable tblCourseList;
	public JScrollPane scpCourseList;
	public DefaultTableModel mdlCourseList;
	public String[] courseCols;
	public JTextField jtfCourseCode;
	public JTextField jtfCourseName;
	public JTextField jtfModuleCode1;
	public JTextField jtfModuleCode2;
	public JTextField jtfModuleCode3;
	public JTextField jtfModuleName1;
	public JTextField jtfModuleName2;
	public JTextField jtfModuleName3;
	public JLabel lblCourseCode;
	public JLabel lblCourseName;
	public JLabel lblModuleCode;
	public JLabel lblModuleName;
	public JButton btnAddCourse;
	
	// Attendance Components
	public Box boxStudentAttendance;
	public JPanel pnlSearchStudent;
	public JTable tblStudentAttendance;
	public JScrollPane scpStudentAttendance;
	public DefaultTableModel mdlStudentAttendance;
	public String[] studentAttendanceCols;
	public JLabel lblSearchStudent;
	public JTextField jtfSearchStudent;
	public Box boxModuleAttendanceList;
	public JPanel pnlSearchOptions;
	public Box boxBtnLoadReport;
	public JTable tblModuleAttendanceList;
	public JScrollPane scpModuleAttendanceList;
	public DefaultTableModel mdlModuleAttendanceList;
	public String[] moduleAttendanceCols;
	public JComboBox<String> jcbSearchCourseNames;
	public JComboBox<String> jcbSearchModuleNames;
	public JLabel lblFrom;
	public JLabel lblTo;
	public JTextField jtfDateFrom;
	public JTextField jtfDateTo;
	public DatePick dateFrom;
	public DatePick dateTo;
	public JButton btnDateFrom;
	public JButton btnDateTo;
	public JButton btnSearchStudentAttendance;
	public JButton btnSearchModuleAttendance;
	public JButton btnLoadReport;
	
	public AdminWindow(Admin user) {
		super("Admin");
		
		admin = user;
		
		// Menu Panel
		createMenu();
		
		// Cards Panel
		pnlCards = new JPanel(new CardLayout());
		pnlCards.setBorder(BorderFactory.createEmptyBorder(7, 10, 7, 7));
		
		// Registry Card Panel
		pnlCardRegistry = createRegistryCard();
		pnlCards.add(pnlCardRegistry, REGISTRY);
		
		// Lecturer Card Panel
		pnlCardLecturer = createLecturerCard();
		pnlCards.add(pnlCardLecturer, LECTURER);
		
		// Course Card Panel
		pnlCardCourse = createCourseCard();
		pnlCards.add(pnlCardCourse, COURSE);
		
		// Attendance Card Panel		
		pnlCardAttendance = createAttendanceCard();
		pnlCards.add(pnlCardAttendance, ATTENDANCE);
		
		pnlContent = new JPanel(new BorderLayout());
		pnlContent.add(pnlMenu, BorderLayout.LINE_START);
		pnlContent.add(pnlCards, BorderLayout.CENTER);
		
		registerButtonListeners();
				
		add(pnlContent);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	private void createMenu() {
		// Menu Panel
		pnlMenu = new JPanel(new GridLayout(0, 1));
		pnlMenu.setBackground(new Color(2, 24, 28));
		pnlMenu.setPreferredSize(new Dimension(150, 500));
		
		// Menu Top
		pnlDate = new JPanel(new GridLayout(0, 1, 0, 5));
		today = LocalDate.now();
		lblDay = new JLabel(today.getDayOfWeek().toString(), SwingConstants.RIGHT);
		lblDay.setFont(ROBOTO_BOLD_TITLE);
		lblDate = new JLabel(today.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)), SwingConstants.RIGHT);
		lblDate.setFont(ROBOTO_BOLD_SUB);
		pnlDate.add(lblDay);
		pnlDate.add(lblDate);
		pnlMenu.add(pnlDate);

		// Menu Items
		btnsMenu[0] = btnRegistry = new JButton(REGISTRY);
		btnsMenu[1] = btnLecturer = new JButton(LECTURER);
		btnsMenu[2] = btnCourse = new JButton(COURSE);
		btnsMenu[3] = btnAttendance = new JButton(ATTENDANCE);
		btnsMenu[4] = btnLogout = new JButton(LOGOUT);

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
			
			if (btnMenu == btnAttendance) {
				pnlMenu.add(btnMenu);
				pnlMenu.add(Box.createVerticalGlue());
				continue;
			}
			pnlMenu.add(btnMenu);
		}
	}
	
	private JPanel createRegistryCard() {
		JPanel panel = new JPanel(new GridLayout(0, 1, 0, 5));
		
		registryCols = new String[] {"registryId", "name", "email", "phome", "manage"};
		mdlRegistryList = new DefaultTableModel();
		mdlRegistryList.setColumnIdentifiers(registryCols);
		
		tblRegistryList = new JTable() {
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return (column == 4);
			}
		};
		tblRegistryList.setModel(mdlRegistryList);
		tblRegistryList.setRowHeight(30);
		tblRegistryList.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblRegistryList.setFillsViewportHeight(true);
		tblRegistryList.setPreferredScrollableViewportSize(new Dimension(1000, 300));

		// Delete Registry Action
		Action delete = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.parseInt(e.getActionCommand());
				
				// Confirm delete
				// 0 OK
				// 2 CANCEL
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + table.getModel().getValueAt(modelRow, 1) + "?", "Confirm Delete", JOptionPane.WARNING_MESSAGE);
				JOptionPane.showMessageDialog(null, "You chose " + choice, "Choice", JOptionPane.PLAIN_MESSAGE);
				
				if (choice == 0) {
					( (DefaultTableModel) table.getModel() ).removeRow(modelRow);
				}
			}
		};
		
		// Set Delete button in table
		ButtonColumn buttonColumnRegistry = new ButtonColumn(tblRegistryList, delete, 4);
		
		scpRegistryList = new JScrollPane(tblRegistryList);
		scpRegistryList.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(BevelBorder.LOWERED),
				"Registry List",
				TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP,
				ROBOTO_BOLD_SUB)
			);
		scpRegistryList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scpRegistryList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		mdlRegistryList.addRow(new Object[] {1, "Mathew", "hdsmathew@gmail.com", 57087019, "Delete"});
		mdlRegistryList.addRow(new Object[] {2, "Mathew1", "hdsmathew@gmail.com", 57087019, "Delete"});
		mdlRegistryList.addRow(new Object[] {3, "Mathew2", "hdsmathew@gmail.com", 57087019, "Delete"});
		mdlRegistryList.addRow(new Object[] {4, "Mathew3", "hdsmathew@gmail.com", 57087019, "Delete"});
		mdlRegistryList.addRow(new Object[] {5, "Mathew4", "hdsmathew@gmail.com", 57087019, "Delete"});
		mdlRegistryList.addRow(new Object[] {6, "Mathew5", "hdsmathew@gmail.com", 57087019, "Delete"});
		
		pnlAddRegistry = new JPanel(new GridBagLayout());
		pnlAddRegistry.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(BevelBorder.LOWERED),
				"Add Registry",
				TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP,
				ROBOTO_BOLD_SUB)
			);
		
		jtfUserId = new JTextField(15);
		jtfUserId.setFont(ROBOTO_PLAIN_SUB);
		jtfName = new JTextField(15);
		jtfName.setFont(ROBOTO_PLAIN_SUB);
		jtfEmail = new JTextField(15);
		jtfEmail.setFont(ROBOTO_PLAIN_SUB);
		jtfPhone = new JTextField(15);
		jtfPhone.setFont(ROBOTO_PLAIN_SUB);
		jtfPassword = new JPasswordField(15);
		jtfPassword.setFont(ROBOTO_PLAIN_SUB);
		lblUserId = new JLabel("UserId");
		lblUserId.setFont(ROBOTO_PLAIN_TITLE);
		lblName = new JLabel("Name");
		lblName.setFont(ROBOTO_PLAIN_TITLE);
		lblEmail = new JLabel("Email");
		lblEmail.setFont(ROBOTO_PLAIN_TITLE);
		lblPhone = new JLabel("Phone");
		lblPhone.setFont(ROBOTO_PLAIN_TITLE);
		lblPassword = new JLabel("Password");
		lblPassword.setFont(ROBOTO_PLAIN_TITLE);
		btnAdd = new JButton("Add Registry");
		btnAdd.setFont(ROBOTO_PLAIN_TITLE);
		
		gc = new GridBagConstraints();
		
		
		addGridBagComponent(pnlAddRegistry, lblUserId, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddRegistry, lblName, 0, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddRegistry, lblEmail, 0, 2, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddRegistry, lblPhone, 0, 3, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddRegistry, lblPassword, 0, 4, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddRegistry, jtfUserId, 1, 0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddRegistry, jtfName, 1, 1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddRegistry, jtfEmail, 1, 2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddRegistry, jtfPhone, 1, 3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddRegistry, jtfPassword, 1, 4, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddRegistry, (JComponent) Box.createHorizontalGlue(), 2, 1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddRegistry, (JComponent) Box.createHorizontalGlue(), 2, 2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddRegistry, (JComponent) Box.createHorizontalGlue(), 2, 3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddRegistry, (JComponent) Box.createHorizontalGlue(), 2, 4, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddRegistry, (JComponent) Box.createHorizontalGlue(), 2, 5, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddRegistry, btnAdd, 1, 5, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH);

		
		panel.add(scpRegistryList);
		panel.add(pnlAddRegistry);
		
		return panel;
	}
	
	private JPanel createLecturerCard() {
		JPanel panel = new JPanel(new GridLayout(0, 1, 0, 5));
		
		lecturerCols = new String[] {"lecturerId", "fname", "lname", "email", "address", "phome", "status"};
		mdlLecturerList = new DefaultTableModel();
		mdlLecturerList.setColumnIdentifiers(lecturerCols);
		
		tblLecturerList = new JTable() {
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return (column == 6);
			}
		};
		tblLecturerList.setModel(mdlLecturerList);
		tblLecturerList.setRowHeight(30);
		tblLecturerList.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblLecturerList.setFillsViewportHeight(true);
		tblLecturerList.setPreferredScrollableViewportSize(new Dimension(1000, 300));

		// Toggle Lecturer Status Action
		Action toggleTeaching = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.parseInt(e.getActionCommand());
				
				String teachingStatus = (String) table.getModel().getValueAt(modelRow, 6);
				String newStatus = (teachingStatus.equalsIgnoreCase("Active") ? "Not Active" : "Active");

				( (DefaultTableModel) table.getModel() ).setValueAt(newStatus, modelRow, 6);
			}
		};
		
		// Set Toggle button in table
		ButtonColumn buttonColumnLecturer = new ButtonColumn(tblLecturerList, toggleTeaching, 6);
		
		scpLecturerList = new JScrollPane(tblLecturerList);
		scpLecturerList.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(BevelBorder.LOWERED),
				"Lecturer List",
				TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP,
				ROBOTO_BOLD_SUB)
			);
		scpLecturerList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scpLecturerList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		mdlLecturerList.addRow(new Object[] {1, "Mathew", "Chan", "hdsmathew@gmail.com", "bdt", 57087019, "Active"});
		mdlLecturerList.addRow(new Object[] {2, "Mathew1", "Chan", "hdsmathew@gmail.com", "bdt", 57087019, "Active"});
		mdlLecturerList.addRow(new Object[] {3, "Mathew2", "Chan", "hdsmathew@gmail.com", "bdt", 57087019, "Active"});
		mdlLecturerList.addRow(new Object[] {4, "Mathew3", "Chan", "hdsmathew@gmail.com", "bdt", 57087019, "Active"});
		mdlLecturerList.addRow(new Object[] {5, "Mathew4", "Chan", "hdsmathew@gmail.com", "bdt", 57087019, "Active"});
		mdlLecturerList.addRow(new Object[] {6, "Mathew5", "Chan", "hdsmathew@gmail.com", "bdt", 57087019, "Not Active"});
		
		pnlAddLecturer = new JPanel(new GridBagLayout());
		pnlAddLecturer.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(BevelBorder.LOWERED),
				"Add Lecturer",
				TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP,
				ROBOTO_BOLD_SUB)
			);
		
		jtfLectId = new JTextField(15);
		jtfLectId.setFont(ROBOTO_PLAIN_SUB);
		jtfLectFname = new JTextField(15);
		jtfLectFname.setFont(ROBOTO_PLAIN_SUB);
		jtfLectLname = new JTextField(15);
		jtfLectLname.setFont(ROBOTO_PLAIN_SUB);
		jtfLectEmail = new JTextField(15);
		jtfLectEmail.setFont(ROBOTO_PLAIN_SUB);
		jtfLectPhone = new JTextField(15);
		jtfLectPhone.setFont(ROBOTO_PLAIN_SUB);
		jtfLectAddr = new JTextField(15);
		jtfLectAddr.setFont(ROBOTO_PLAIN_SUB);
		lblLectId = new JLabel("LecturerId");
		lblLectId.setFont(ROBOTO_PLAIN_TITLE);
		lblLectFname = new JLabel("First name");
		lblLectFname.setFont(ROBOTO_PLAIN_TITLE);
		lblLectLname = new JLabel("Last name");
		lblLectLname.setFont(ROBOTO_PLAIN_TITLE);
		lblLectEmail = new JLabel("Email");
		lblLectEmail.setFont(ROBOTO_PLAIN_TITLE);
		lblLectPhone = new JLabel("Phone");
		lblLectPhone.setFont(ROBOTO_PLAIN_TITLE);
		lblLectAddr = new JLabel("Address");
		lblLectAddr.setFont(ROBOTO_PLAIN_TITLE);
		btnAddLect = new JButton("Add Lecturer");
		btnAddLect.setFont(ROBOTO_PLAIN_TITLE);
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.weightx = 100.0;
		gc.weighty = 100.0;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.anchor = GridBagConstraints.EAST;
		gc.fill = GridBagConstraints.NONE;
		
		
		addGridBagComponent(pnlAddLecturer, lblLectId, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddLecturer, lblLectFname, 0, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddLecturer, lblLectLname, 0, 2, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddLecturer, lblLectEmail, 0, 3, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddLecturer, lblLectPhone, 0, 4, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddLecturer, lblLectAddr, 0, 5, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddLecturer, jtfLectId, 1, 0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddLecturer, jtfLectFname, 1, 1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddLecturer, jtfLectLname, 1, 2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddLecturer, jtfLectEmail, 1, 3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddLecturer, jtfLectPhone, 1, 4, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddLecturer, jtfLectAddr, 1, 5, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddLecturer, btnAddLect, 1, 6, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH);

		
		pnlAssignLecturer = new JPanel(new GridBagLayout());
		pnlAssignLecturer.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(BevelBorder.LOWERED),
				"Assign Module",
				TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP,
				ROBOTO_BOLD_SUB)
			);
		
		lecturerIds = new int[] {
				1, 2, 3, 4, 5
		};
		courseCodes = new int[] {
				1, 2, 3, 4, 5
		};
		moduleCodes = new int[] {
				1, 2, 3, 4, 5
		};
		lecturerNames = new String[] {
				"Pava", "Vara", "Suda", "Nuja", "Sona"
		};
		courseNames = new String[] {
				"CS", "IS", "SE", "AP", "DS"
		};
		moduleNames = new String[] {
				"Maths", "Architecture", "Formal", "Database", "OOP"
		};
		
		jcbAssignLectNames = new JComboBox<>(lecturerNames);
		jcbAssignLectNames.setFont(ROBOTO_PLAIN_SUB);
		jcbAssignLectNames.setMaximumRowCount(5);
		jcbAssignCourseNames = new JComboBox<>(courseNames);
		jcbAssignCourseNames.setFont(ROBOTO_PLAIN_SUB);
		jcbAssignCourseNames.setMaximumRowCount(5);
		jcbAssignModuleNames = new JComboBox<>(moduleNames);
		jcbAssignModuleNames.setFont(ROBOTO_PLAIN_SUB);
		jcbAssignModuleNames.setMaximumRowCount(5);
		btnAssignLect = new JButton("Assign Module");
		btnAssignLect.setFont(ROBOTO_PLAIN_TITLE);
		
		lblAssignLect = new JLabel("Lecturer");
		lblAssignLect.setFont(ROBOTO_PLAIN_TITLE);
		lblAssignCourse = new JLabel("Course");
		lblAssignCourse.setFont(ROBOTO_PLAIN_TITLE);
		lblAssignModule = new JLabel("Module");
		lblAssignModule.setFont(ROBOTO_PLAIN_TITLE);
		
		
		addGridBagComponent(pnlAssignLecturer, lblAssignLect, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAssignLecturer, lblAssignCourse, 0, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAssignLecturer, lblAssignModule, 0, 2, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAssignLecturer, jcbAssignLectNames, 1, 0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAssignLecturer, (JComponent) Box.createHorizontalGlue(), 2, 0);
		addGridBagComponent(pnlAssignLecturer, jcbAssignCourseNames, 1, 1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAssignLecturer, (JComponent) Box.createHorizontalGlue(), 2, 1);
		addGridBagComponent(pnlAssignLecturer, jcbAssignModuleNames, 1, 2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAssignLecturer, (JComponent) Box.createHorizontalGlue(), 2, 2);
		addGridBagComponent(pnlAssignLecturer, (JComponent) Box.createHorizontalGlue(), 2, 3);
		addGridBagComponent(pnlAssignLecturer, btnAssignLect, 1, 3, GridBagConstraints.NORTHEAST, GridBagConstraints.BOTH);


		pnlManageLecturer = new JPanel(new GridLayout(1, 0, 5, 0));
		pnlManageLecturer.add(pnlAddLecturer);
		pnlManageLecturer.add(pnlAssignLecturer);
		
		panel.add(scpLecturerList);
		panel.add(pnlManageLecturer);
		
		return panel;
	}
	
	private JPanel createCourseCard() {
		JPanel panel = new JPanel(new GridLayout(0, 1, 0, 5));
		
		courseCols = new String[] {"courseCode", "courseName", "on offer"};
		mdlCourseList = new DefaultTableModel();
		mdlCourseList.setColumnIdentifiers(courseCols);
		
		tblCourseList = new JTable() {
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return (column == 2);
			}
		};
		tblCourseList.setModel(mdlCourseList);
		tblCourseList.setRowHeight(30);
		tblCourseList.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblCourseList.setFillsViewportHeight(true);
		tblCourseList.setPreferredScrollableViewportSize(new Dimension(1000, 300));

		// Toggle Course Status Action
		Action toggleOnOffer = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int modelRow = Integer.parseInt(e.getActionCommand());
				
				int courseCode = (int) model.getValueAt(modelRow, 0);
				String courseName = (String) model.getValueAt(modelRow, 1);
				String offerStatus = (String) model.getValueAt(modelRow, 2);
				String newStatus = (offerStatus.equalsIgnoreCase("Available") ? "Not Available" : "Available");
				
				course = new Course(courseCode, newStatus);
				
				int choice = JOptionPane.showConfirmDialog(null, "Set onOffer to '" + newStatus + "' for '" + courseName + "' ?", "Confirm Update", JOptionPane.WARNING_MESSAGE);
				if (choice != 0) return; // CANCEL
				
				if ( course.update(admin.getUserConnection()) ) { // Update successful
					model.setValueAt(newStatus, modelRow, 2);
				} else {
					JOptionPane.showMessageDialog(null, "Cannot Update");
				}
			}
		};
		
		// Set Toggle button in table
		ButtonColumn buttonColumnCourse = new ButtonColumn(tblCourseList, toggleOnOffer, 2);
		
		scpCourseList = new JScrollPane(tblCourseList);
		scpCourseList.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(BevelBorder.LOWERED),
				"Course List",
				TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP,
				ROBOTO_BOLD_SUB)
			);
		scpCourseList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scpCourseList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		pnlAddCourse = new JPanel(new GridBagLayout());
		pnlAddCourse.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(BevelBorder.LOWERED),
				"Add Course",
				TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP,
				ROBOTO_BOLD_SUB)
			);
		
		jtfCourseCode = new JTextField(15);
		jtfCourseCode.setFont(ROBOTO_PLAIN_SUB);
		jtfCourseCode.setEditable(false);
		jtfCourseName = new JTextField(15);
		jtfCourseName.setFont(ROBOTO_PLAIN_SUB);
		jtfModuleCode1 = new JTextField(15);
		jtfModuleCode1.setFont(ROBOTO_PLAIN_SUB);
		jtfModuleCode1.setEditable(false);
		jtfModuleCode2 = new JTextField(15);
		jtfModuleCode2.setFont(ROBOTO_PLAIN_SUB);
		jtfModuleCode2.setEditable(false);
		jtfModuleCode3 = new JTextField(15);
		jtfModuleCode3.setFont(ROBOTO_PLAIN_SUB);
		jtfModuleCode3.setEditable(false);
		jtfModuleName1 = new JTextField(15);
		jtfModuleName1.setFont(ROBOTO_PLAIN_SUB);
		jtfModuleName2 = new JTextField(15);
		jtfModuleName2.setFont(ROBOTO_PLAIN_SUB);
		jtfModuleName3 = new JTextField(15);
		jtfModuleName3.setFont(ROBOTO_PLAIN_SUB);
		lblCourseCode = new JLabel("CourseCode");
		lblCourseCode.setFont(ROBOTO_PLAIN_TITLE);
		lblCourseName = new JLabel("Course name");
		lblCourseName.setFont(ROBOTO_PLAIN_TITLE);
		lblModuleCode = new JLabel("ModuleCode");
		lblModuleCode.setFont(ROBOTO_PLAIN_TITLE);
		lblModuleName = new JLabel("Module name");
		lblModuleName.setFont(ROBOTO_PLAIN_TITLE);
		btnAddCourse = new JButton("Add Course");
		btnAddCourse.setFont(ROBOTO_PLAIN_TITLE);
		
		
		addGridBagComponent(pnlAddCourse, lblCourseCode, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddCourse, lblCourseName, 0, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddCourse, jtfCourseCode, 1, 0);
		addGridBagComponent(pnlAddCourse, jtfCourseName, 1, 1);
		addGridBagComponent(pnlAddCourse, lblModuleCode, 3, 0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddCourse, lblModuleName, 4, 0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddCourse, jtfModuleCode1, 3, 1);
		addGridBagComponent(pnlAddCourse, jtfModuleCode2, 3, 2);
		addGridBagComponent(pnlAddCourse, jtfModuleCode3, 3, 3);
		addGridBagComponent(pnlAddCourse, jtfModuleName1, 4, 1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddCourse, jtfModuleName2, 4, 2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddCourse, jtfModuleName3, 4, 3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddCourse, btnAddCourse, 2, 4, 4, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH);


		panel.add(scpCourseList);
		panel.add(pnlAddCourse);
		
		return panel;
	}
	
	private JPanel createAttendanceCard() {
		JPanel panel = new JPanel(new GridLayout(0, 1, 0, 5));
		
		boxModuleAttendanceList = Box.createVerticalBox();
		pnlSearchOptions = new JPanel(new GridBagLayout());
		boxBtnLoadReport = Box.createHorizontalBox();
		
		jcbSearchCourseNames = new JComboBox<>(courseNames);
		jcbSearchCourseNames.setFont(ROBOTO_PLAIN_SUB);
		jcbSearchModuleNames = new JComboBox<>(moduleNames);
		jcbSearchModuleNames.setFont(ROBOTO_PLAIN_SUB);
		lblFrom = new JLabel("From");
		lblFrom.setFont(ROBOTO_PLAIN_TITLE);
		lblTo = new JLabel("To");
		lblTo.setFont(ROBOTO_PLAIN_TITLE);
		jtfDateFrom = new JTextField(7);
		jtfDateFrom.setFont(ROBOTO_PLAIN_SUB);
		jtfDateTo = new JTextField(7);
		jtfDateTo.setFont(ROBOTO_PLAIN_SUB);
		btnSearchModuleAttendance = new JButton("Search Module Attendance");
		btnSearchModuleAttendance.setFont(ROBOTO_PLAIN_TITLE);
		btnDateFrom = new JButton("Choose Date From");
		btnDateFrom.setFont(ROBOTO_PLAIN_SUB);
		btnDateTo = new JButton("Choose Date To");
		btnDateTo.setFont(ROBOTO_PLAIN_SUB);
		btnLoadReport = new JButton("Load Report");
		btnLoadReport.setFont(ROBOTO_PLAIN_TITLE);
		
		btnDateFrom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jtfDateFrom.setText(new DatePick(AdminWindow.this).Set_Picked_Date());
			}
		});
		
		btnDateTo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jtfDateTo.setText(new DatePick(AdminWindow.this).Set_Picked_Date());
			}
		});
		
		addGridBagComponent(pnlSearchOptions, jcbSearchCourseNames, 0, 0);		
		addGridBagComponent(pnlSearchOptions, jcbSearchModuleNames, 1, 0);
		addGridBagComponent(pnlSearchOptions, lblFrom, 0, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlSearchOptions, lblTo, 3, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlSearchOptions, jtfDateFrom, 1, 1);
		addGridBagComponent(pnlSearchOptions, jtfDateTo, 4, 1);
		addGridBagComponent(pnlSearchOptions, btnSearchModuleAttendance, 2, 0);
		addGridBagComponent(pnlSearchOptions, btnDateFrom, 2, 1);
		addGridBagComponent(pnlSearchOptions, btnDateTo, 5, 1);
		
		
		boxBtnLoadReport.add(Box.createHorizontalGlue());
		boxBtnLoadReport.add(btnLoadReport);

		
		moduleAttendanceCols = new String[] {"studentName", "2022-01-01", "2022-01-08", "2022-01-15", "2022-01-22", "2022-01-29"};
		mdlModuleAttendanceList = new DefaultTableModel();
		mdlModuleAttendanceList.setColumnIdentifiers(moduleAttendanceCols);
		
		tblModuleAttendanceList = new JTable() {
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
			@Override
			public Class getColumnClass(int column) {
				if (column != 0) {
					return Boolean.class;
				}
				return String.class;
			}
		};
		tblModuleAttendanceList.setModel(mdlModuleAttendanceList);
		tblModuleAttendanceList.setRowHeight(30);
		tblModuleAttendanceList.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblModuleAttendanceList.setFillsViewportHeight(true);
		tblModuleAttendanceList.setPreferredScrollableViewportSize(new Dimension(1000, 300));
		
		scpModuleAttendanceList = new JScrollPane(tblModuleAttendanceList);
		scpModuleAttendanceList.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(BevelBorder.LOWERED),
				"Attendance List",
				TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP,
				ROBOTO_BOLD_SUB)
			);
		scpModuleAttendanceList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scpModuleAttendanceList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		mdlModuleAttendanceList.addRow(new Object[] {"mathew1", true, false, false, true, true});
		mdlModuleAttendanceList.addRow(new Object[] {"mathew2", false, false, false, false, false});
		mdlModuleAttendanceList.addRow(new Object[] {"mathew3", true, true, true, false, false});
		mdlModuleAttendanceList.addRow(new Object[] {"mathew4", false, false, true, true, true});
		mdlModuleAttendanceList.addRow(new Object[] {"mathew5", true, true, true, true, true});
		
		
		boxStudentAttendance = Box.createVerticalBox();
		pnlSearchStudent = new JPanel(new GridBagLayout());
		
		jtfSearchStudent = new JTextField(7);
		jtfSearchStudent.setFont(ROBOTO_PLAIN_SUB);
		lblSearchStudent = new JLabel("Student Id:");
		lblSearchStudent.setFont(ROBOTO_PLAIN_TITLE);
		btnSearchStudentAttendance = new JButton("Search Student Attendance");
		btnSearchStudentAttendance.setFont(ROBOTO_PLAIN_TITLE);
		
		addGridBagComponent(pnlSearchStudent, (JComponent) Box.createHorizontalGlue(), 0, 0);
		addGridBagComponent(pnlSearchStudent, lblSearchStudent, 0, 1);
		addGridBagComponent(pnlSearchStudent, (JComponent) Box.createHorizontalGlue(), 1, 1);
		addGridBagComponent(pnlSearchStudent, jtfSearchStudent, 0, 2);
		addGridBagComponent(pnlSearchStudent, btnSearchStudentAttendance, 1, 2);
		addGridBagComponent(pnlSearchStudent, (JComponent) Box.createHorizontalGlue(), 2, 2);
		addGridBagComponent(pnlSearchStudent, (JComponent) Box.createHorizontalGlue(), 3, 2);
		

		studentAttendanceCols = new String[] {"moduleName", "2022-01-01", "2022-01-08", "2022-01-15", "2022-01-22", "2022-01-29"};
		mdlStudentAttendance = new DefaultTableModel();
		mdlStudentAttendance.setColumnIdentifiers(studentAttendanceCols);
		
		tblStudentAttendance = new JTable() {
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
			@Override
			public Class getColumnClass(int column) {
				if (column != 0) {
					return Boolean.class;
				}
				return String.class;
			}
		};
		tblStudentAttendance.setModel(mdlStudentAttendance);
		tblStudentAttendance.setRowHeight(30);
		tblStudentAttendance.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblStudentAttendance.setFillsViewportHeight(true);
		tblStudentAttendance.setPreferredScrollableViewportSize(new Dimension(1000, 300));
		
		scpStudentAttendance = new JScrollPane(tblStudentAttendance);
		scpStudentAttendance.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(BevelBorder.LOWERED),
				"Student Attendance",
				TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP,
				ROBOTO_BOLD_SUB)
			);
		scpStudentAttendance.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scpStudentAttendance.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		mdlStudentAttendance.addRow(new Object[] {"Maths", true, false, false, true, true});
		mdlStudentAttendance.addRow(new Object[] {"Formal", false, false, false, false, false});
		mdlStudentAttendance.addRow(new Object[] {"Database", true, true, true, false, false});
		mdlStudentAttendance.addRow(new Object[] {"OOP", false, false, true, true, true});
		mdlStudentAttendance.addRow(new Object[] {"Architecture", true, true, true, true, true});
		
		boxModuleAttendanceList.add(pnlSearchOptions);
		boxModuleAttendanceList.add(Box.createVerticalStrut(10));
		boxModuleAttendanceList.add(scpModuleAttendanceList);
		boxModuleAttendanceList.add(Box.createVerticalStrut(7));
		boxModuleAttendanceList.add(boxBtnLoadReport);
		
		boxStudentAttendance.add(pnlSearchStudent);
		boxStudentAttendance.add(Box.createVerticalStrut(10));
		boxStudentAttendance.add(scpStudentAttendance);
		
		panel.add(boxModuleAttendanceList);
		panel.add(boxStudentAttendance);
		
		return panel;
	}
	
	private void registerButtonListeners() {
		btnAddCourse.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int courseCode;
				String courseName;
				CourseModule[] modules = new CourseModule[3];
				
				courseCode = Integer.parseInt(jtfCourseCode.getText());
				courseName = jtfCourseName.getText();
				modules[0] = new CourseModule(jtfModuleName1.getText());
				modules[1] = new CourseModule(jtfModuleName2.getText());
				modules[2] = new CourseModule(jtfModuleName3.getText());
				
				course = new Course(courseCode, courseName, modules, "Available");
				
				String message = "Cannot add course";
				if ( admin.addCourse(course) ) { // Insert Successful
					message = "Course Added";
					mdlCourseList.addRow( course.getObjectInfo() );
					initializeJTextFields(COURSE);
				}
				JOptionPane.showMessageDialog(null, message);
			}
		} );
	}
	
	private void populateTable(JTable table, ArrayList<TableObjectInterface> objList) {
		// Clear any existing data
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		
		for (TableObjectInterface obj : objList) {
			model.addRow(obj.getObjectInfo());
		}
	}
	
	private void initializeJTextFields(String command) {
		switch (command) {
			case REGISTRY:

				break;
				
			case LECTURER:

				break;	
				
			case COURSE:
				int nextModuleCode = CourseModule.getNextAvailableModuleCode(admin.getUserConnection());
				jtfModuleCode1.setText( Integer.toString(nextModuleCode++) );
				jtfModuleCode2.setText( Integer.toString(nextModuleCode++) );
				jtfModuleCode3.setText( Integer.toString(nextModuleCode++) );
				jtfCourseCode.setText( Integer.toString(Course.getNextAvailableCourseCode(admin.getUserConnection())) );
				
				jtfModuleName1.setText("");
				jtfModuleName2.setText("");
				jtfModuleName3.setText("");
				jtfCourseName.setText("");
						
				break;
				
			case ATTENDANCE:

				break;	
				
			default:
		}
	}
	
	// Menu JButton Click Event Handler
	private class MenuBtnActionHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			// If LOGOUT, show confirm dialog
			if (e.getSource() == btnLogout) {
				// 0 OK
				// 2 CANCEL
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.WARNING_MESSAGE);
				JOptionPane.showMessageDialog(null, "You chose " + choice, "Choice", JOptionPane.PLAIN_MESSAGE);
				
			} else {
				String command = e.getActionCommand();
				JTable table = null;
				ArrayList<TableObjectInterface> objList = new ArrayList<>();
				
				switch (command) {
					case REGISTRY:
						table = tblRegistryList;
						objList = Course.readAll(admin.getUserConnection());
						break;
					case LECTURER:
						table = tblLecturerList;
						objList = Course.readAll(admin.getUserConnection());
						break;	
					case COURSE:
						table = tblCourseList;
						objList = Course.readAll(admin.getUserConnection());
						break;
					case ATTENDANCE:
						table = tblModuleAttendanceList;
						objList = Course.readAll(admin.getUserConnection());
						break;	
					default:
				}
				
				populateTable(table, objList);
				initializeJTextFields(command);
				
				CardLayout cl = (CardLayout) pnlCards.getLayout();
				cl.show(pnlCards, command);
			}
		}
		
	}
	
	// Helpers for gridbagconstraints
	private void addGridBagComponent(JPanel p, JComponent c, int x, int y, int width, int height, int align, int fill) {
		gc.gridx = x;
		gc.gridy = y;
		gc.gridwidth = width;
		gc.gridheight = height;
		gc.weightx = 100.0;
		gc.weighty = 100.0;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.anchor = align;
		gc.fill = fill;
		
		p.add(c, gc);
	}
	
	private void addGridBagComponent(JPanel p, JComponent c, int x, int y, int align, int fill) {
		gc.gridx = x;
		gc.gridy = y;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.weightx = 100.0;
		gc.weighty = 100.0;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.anchor = align;
		gc.fill = fill;
		
		p.add(c, gc);
	}
	
	private void addGridBagComponent(JPanel p, JComponent c, int x, int y) {
		gc.gridx = x;
		gc.gridy = y;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.weightx = 100.0;
		gc.weighty = 100.0;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.BOTH;
		
		p.add(c, gc);
	}
}
