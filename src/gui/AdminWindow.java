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

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

public class AdminWindow extends JFrame {
	
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
	
	public AdminWindow() {
		super("Admin");
				
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
		
		
		// Cards Panel
		pnlCards = new JPanel(new CardLayout());
		pnlCards.setBorder(BorderFactory.createEmptyBorder(7, 10, 7, 7));
		
		
		
		// Registry Card Panel
		pnlCardRegistry = new JPanel(new GridLayout(0, 1, 0, 5));
		
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
				( (DefaultTableModel) table.getModel() ).removeRow(modelRow);
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
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.weightx = 100.0;
		gc.weighty = 100.0;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.anchor = GridBagConstraints.EAST;
		gc.fill = GridBagConstraints.NONE;
		
		gc.gridx = 0;
		gc.gridy = 0;
		pnlAddRegistry.add(lblUserId, gc);
		
		gc.gridx = 0;
		gc.gridy = 1;
		pnlAddRegistry.add(lblName, gc);
		
		gc.gridx = 0;
		gc.gridy = 2;
		pnlAddRegistry.add(lblEmail, gc);
		
		gc.gridx = 0;
		gc.gridy = 3;
		pnlAddRegistry.add(lblPhone, gc);
		
		gc.gridx = 0;
		gc.gridy = 4;
		pnlAddRegistry.add(lblPassword, gc);
		
		gc.gridx = 1;
		gc.gridy = 0;
		gc.gridwidth = 2;
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.BOTH;
		pnlAddRegistry.add(jtfUserId, gc);
		
		gc.gridy = 1;
		pnlAddRegistry.add(jtfName, gc);
		
		gc.gridy = 2;
		pnlAddRegistry.add(jtfEmail, gc);

		gc.gridy = 3;
		pnlAddRegistry.add(jtfPhone, gc);
		
		gc.gridy = 4;
		pnlAddRegistry.add(jtfPassword, gc);
		
		gc.gridx = 2;
		gc.gridy = 1;
		pnlAddRegistry.add(Box.createHorizontalGlue(), gc);
		
		gc.gridy = 2;
		pnlAddRegistry.add(Box.createHorizontalGlue(), gc);
		
		gc.gridy = 3;
		pnlAddRegistry.add(Box.createHorizontalGlue(), gc);

		gc.gridy = 4;
		pnlAddRegistry.add(Box.createHorizontalGlue(), gc);
		
		gc.gridy = 5;
		pnlAddRegistry.add(Box.createHorizontalGlue(), gc);
		
		gc.gridx = 1;
		gc.gridwidth = 1;
		gc.anchor = GridBagConstraints.NORTHWEST;
		pnlAddRegistry.add(btnAdd, gc);

		
		pnlCardRegistry.add(scpRegistryList);
		pnlCardRegistry.add(pnlAddRegistry);
		pnlCards.add(pnlCardRegistry, REGISTRY);
		
		
		
		// Lecturer Card Panel
		pnlCardLecturer= new JPanel(new GridLayout(0, 1, 0, 5));
		
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

				table.getModel().setValueAt(newStatus, modelRow, 6);
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
		
		gc.gridx = 0;
		gc.gridy = 0;
		pnlAddLecturer.add(lblLectId, gc);
		
		gc.gridx = 0;
		gc.gridy = 1;
		pnlAddLecturer.add(lblLectFname, gc);
		
		gc.gridx = 0;
		gc.gridy = 2;
		pnlAddLecturer.add(lblLectLname, gc);
		
		gc.gridx = 0;
		gc.gridy = 3;
		pnlAddLecturer.add(lblLectEmail, gc);
		
		gc.gridx = 0;
		gc.gridy = 4;
		pnlAddLecturer.add(lblLectPhone, gc);
		
		gc.gridx = 0;
		gc.gridy = 5;
		pnlAddLecturer.add(lblLectAddr, gc);
		
		gc.gridx = 1;
		gc.gridy = 0;
		gc.gridwidth = 2;
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.BOTH;
		pnlAddLecturer.add(jtfLectId, gc);
		
		gc.gridy = 1;
		pnlAddLecturer.add(jtfLectFname, gc);
		
		gc.gridy = 2;
		pnlAddLecturer.add(jtfLectLname, gc);
		
		gc.gridy = 3;
		pnlAddLecturer.add(jtfLectEmail, gc);
		
		gc.gridy = 4;
		pnlAddLecturer.add(jtfLectPhone, gc);
		
		gc.gridy = 5;
		pnlAddLecturer.add(jtfLectAddr, gc);
				
		gc.gridx = 1;
		gc.gridy = 6;
		gc.gridwidth = 1;
		gc.anchor = GridBagConstraints.NORTHWEST;
		pnlAddLecturer.add(btnAddLect, gc);

		
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
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.weightx = 100.0;
		gc.weighty = 100.0;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.anchor = GridBagConstraints.EAST;
		gc.fill = GridBagConstraints.NONE;
		
		pnlAssignLecturer.add(lblAssignLect, gc);
		
		gc.gridy = 1;
		pnlAssignLecturer.add(lblAssignCourse, gc);
		
		gc.gridy = 2;
		pnlAssignLecturer.add(lblAssignModule, gc);
		
		gc.gridx = 1;
		gc.gridy = 0;
		gc.gridwidth = 2;
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.BOTH;
		pnlAssignLecturer.add(jcbAssignLectNames, gc);
		
		gc.gridx = 2;
		pnlAssignLecturer.add(Box.createHorizontalGlue(), gc);
		
		gc.gridx = 1;
		gc.gridy = 1;
		pnlAssignLecturer.add(jcbAssignCourseNames, gc);
		
		gc.gridx = 2;
		pnlAssignLecturer.add(Box.createHorizontalGlue(), gc);
		
		gc.gridx = 1;
		gc.gridy = 2;
		pnlAssignLecturer.add(jcbAssignModuleNames, gc);
		
		gc.gridx = 2;
		pnlAssignLecturer.add(Box.createHorizontalGlue(), gc);
		
		gc.gridy = 3;
		pnlAssignLecturer.add(Box.createHorizontalGlue(), gc);
		
		gc.gridx = 1;
		gc.gridwidth = 1;
		gc.anchor = GridBagConstraints.NORTHWEST;
		pnlAssignLecturer.add(btnAssignLect, gc);

		pnlManageLecturer = new JPanel(new GridLayout(1, 0, 5, 0));
		pnlManageLecturer.add(pnlAddLecturer);
		pnlManageLecturer.add(pnlAssignLecturer);
		
		pnlCardLecturer.add(scpLecturerList);
		pnlCardLecturer.add(pnlManageLecturer);
		pnlCards.add(pnlCardLecturer, LECTURER);
		
		// Course Card Panel
		pnlCardCourse = new JPanel(new GridLayout(0, 1, 0, 5));
		
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
				int modelRow = Integer.parseInt(e.getActionCommand());
				
				String offerStatus = (String) table.getModel().getValueAt(modelRow, 2);
				String newStatus = (offerStatus.equalsIgnoreCase("Available") ? "Not Available" : "Available");

				table.getModel().setValueAt(newStatus, modelRow, 2);
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
		
		mdlCourseList.addRow(new Object[] {1, "CS", "Available"});
		mdlCourseList.addRow(new Object[] {2, "IS", "Available"});
		mdlCourseList.addRow(new Object[] {3, "DS", "Available"});
		mdlCourseList.addRow(new Object[] {4, "SE", "Not Available"});
		mdlCourseList.addRow(new Object[] {5, "AP", "Available"});
		
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
		jtfCourseName = new JTextField(15);
		jtfCourseName.setFont(ROBOTO_PLAIN_SUB);
		jtfModuleCode1 = new JTextField(15);
		jtfModuleCode1.setFont(ROBOTO_PLAIN_SUB);
		jtfModuleCode2 = new JTextField(15);
		jtfModuleCode2.setFont(ROBOTO_PLAIN_SUB);
		jtfModuleCode3 = new JTextField(15);
		jtfModuleCode3.setFont(ROBOTO_PLAIN_SUB);
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
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.weightx = 100.0;
		gc.weighty = 100.0;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.anchor = GridBagConstraints.EAST;
		gc.fill = GridBagConstraints.NONE;
		
		pnlAddCourse.add(lblCourseCode, gc);
		
		gc.gridy = 1;
		pnlAddCourse.add(lblCourseName, gc);
		
		gc.gridx = 1;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.BOTH;
		pnlAddCourse.add(jtfCourseCode, gc);
		
		gc.gridy = 1;
		pnlAddCourse.add(jtfCourseName, gc);
		
		gc.gridx = 3;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.SOUTHWEST;
		gc.fill = GridBagConstraints.NONE;
		pnlAddCourse.add(lblModuleCode, gc);
		
		gc.gridx = 4;
		gc.gridwidth = 2;
		pnlAddCourse.add(lblModuleName, gc);

		gc.gridx = 3;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.BOTH;
		pnlAddCourse.add(jtfModuleCode1, gc);
		
		gc.gridy = 2;
		pnlAddCourse.add(jtfModuleCode2, gc);
		
		gc.gridy = 3;
		pnlAddCourse.add(jtfModuleCode3, gc);
		
		gc.gridx = 4;
		gc.gridy = 1;
		gc.gridwidth = 2;
		pnlAddCourse.add(jtfModuleName1, gc);
		
		gc.gridy = 2;
		pnlAddCourse.add(jtfModuleName2, gc);
		
		gc.gridy = 3;
		pnlAddCourse.add(jtfModuleName3, gc);
		
		gc.gridx = 2;
		gc.gridy = 4;
		gc.gridwidth = 4;
		gc.anchor = GridBagConstraints.NORTHWEST;
		pnlAddCourse.add(btnAddCourse, gc);

		pnlCardCourse.add(scpCourseList);
		pnlCardCourse.add(pnlAddCourse);
		pnlCards.add(pnlCardCourse, COURSE);
		
		// Attendance Card Panel		
		pnlCardAttendance = new JPanel(new GridLayout(0, 1, 0, 5));
		
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
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.weightx = 100.0;
		gc.weighty = 100.0;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.BOTH;
		
		pnlSearchOptions.add(jcbSearchCourseNames, gc);
		
		gc.gridx = 1;
		pnlSearchOptions.add(jcbSearchModuleNames, gc);
		
		gc.gridx = 0;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.EAST;
		gc.fill = GridBagConstraints.NONE;
		pnlSearchOptions.add(lblFrom, gc);
		
		gc.gridx = 3;
		pnlSearchOptions.add(lblTo, gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.BOTH;
		pnlSearchOptions.add(jtfDateFrom, gc);
		
		gc.gridx = 4;
		pnlSearchOptions.add(jtfDateTo, gc);
		
		gc.gridx = 2;
		gc.gridy = 0;
		pnlSearchOptions.add(btnSearchModuleAttendance, gc);
		
		gc.gridx = 2;
		gc.gridy = 1;
		pnlSearchOptions.add(btnDateFrom, gc);
		
		gc.gridx = 5;
		pnlSearchOptions.add(btnDateTo, gc);
		
		
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
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.weightx = 100.0;
		gc.weighty = 100.0;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.BOTH;
		
		pnlSearchStudent.add(Box.createHorizontalGlue(), gc);
		
		gc.gridy = 1;
		pnlSearchStudent.add(lblSearchStudent, gc);
		
		gc.gridx = 1;
		pnlSearchStudent.add(Box.createHorizontalGlue(), gc);
		
		gc.gridx = 0;
		gc.gridy = 2;
		pnlSearchStudent.add(jtfSearchStudent, gc);
		
		gc.gridx = 1;
		pnlSearchStudent.add(btnSearchStudentAttendance, gc);
		
		gc.gridx = 2;
		pnlSearchStudent.add(Box.createHorizontalGlue(), gc);
		
		gc.gridx = 3;
		pnlSearchStudent.add(Box.createHorizontalGlue(), gc);
		

		
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
		
		pnlCardAttendance.add(boxModuleAttendanceList);
		pnlCardAttendance.add(boxStudentAttendance);
		pnlCards.add(pnlCardAttendance, ATTENDANCE);
		
		
		pnlContent = new JPanel(new BorderLayout());
		pnlContent.add(pnlMenu, BorderLayout.LINE_START);
		pnlContent.add(pnlCards, BorderLayout.CENTER);
		
		add(pnlContent);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	
	// Menu JButton Click Event Handler
	class MenuBtnActionHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// If LOGOUT, show confirm dialog
			CardLayout cl = (CardLayout) pnlCards.getLayout();
			cl.show(pnlCards, e.getActionCommand());
		}
		
	}
}
