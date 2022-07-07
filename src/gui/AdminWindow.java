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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import model.Attendance;
import model.Course;
import model.CourseModule;
import model.Lecturer;
import model.Registry;
import model.Student;
import model.TableObjectInterface;

public class AdminWindow extends JFrame {
	
	private JFrame landingWindow;
	
	private Admin admin;
	private Registry registry;
	private Lecturer lecturer;
	private int lecturerId;
	private Course course;
	private CourseModule module;
	private Student student;
	private ArrayList<Attendance> attendances;
	private ArrayList<TableObjectInterface> courses;
	private ArrayList<TableObjectInterface> lecturers;
	private ArrayList<TableObjectInterface> students;
	private ArrayList<Integer> studentIds;
	private ArrayList<String> studentNames;
	private CourseModule[] studentModules;
	
	private Map<Integer, String> defaultersIdNameMap = new HashMap<>();
	private Map<Integer, ArrayList<LocalDate>> defaultersDateAbsentMap;
	private Map<LocalDate, ArrayList<Boolean>> moduleAttendancesColsData;
	private Map<String, String[]> courseModulesMap = new HashMap<>();
	private Map<String, Integer> moduleNameCodeMap = new HashMap<>();
	private Map<String, Integer> lecturerNameIdMap = new HashMap<>();
	private DefaultComboBoxModel<String> mdlLecturerNames;
	private DefaultComboBoxModel<String> mdlCourseNames;
	private DefaultComboBoxModel<String> mdlCourseModuleNames;
	
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
	public JTextField jtfRegistryId;
	public JTextField jtfFname;
	public JTextField jtfLname;
	public JTextField jtfEmail;
	public JTextField jtfPhone;
	public JPasswordField jtfPassword;
	public JLabel lblRegistryId;
	public JLabel lblFname;
	public JLabel lblLname;
	public JLabel lblEmail;
	public JLabel lblPhone;
	public JLabel lblPassword;
	public JButton btnAddRegistry;
	
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
	public String[] courseNames;
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
	public JTable[] tblStudentAttendanceModules = new JTable[3];
	public JScrollPane[] scpStudentAttendanceModules = new JScrollPane[3];
	public DefaultTableModel[] mdlStudentAttendanceModules = new DefaultTableModel[3];
	public JLabel lblSearchStudent;
	public JTextField jtfSearchStudent;
	public Box boxModuleAttendanceList;
	public JPanel pnlSearchOptions;
	public Box boxBtnLoadReport;
	public JTable tblModuleAttendanceList;
	public JScrollPane scpModuleAttendanceList;
	public DefaultTableModel mdlModuleAttendanceList;
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
	public JButton btnLoadDefaulterList;
	
	public AdminWindow(Admin user, JFrame frame) {
		super("Admin");
		landingWindow = frame;
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
				
		// Confirm exit and logout
		addWindowListener( new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				// Confirm exit
				// 0 OK
				// 2 CANCEL
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to close the application", "Confirm Exit", JOptionPane.WARNING_MESSAGE);
				if (choice != JOptionPane.OK_OPTION) return; // CANCEL
				
				admin.logout();
				System.exit(0);
			}
		} );
		
		add(pnlContent);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
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
		
		registryCols = new String[] {"registryId", "fullname", "email", "phome", "manage"};
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
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int modelRow = Integer.parseInt(e.getActionCommand());
				int registryId = (int) model.getValueAt(modelRow, 0);
				
				// Confirm delete
				// 0 OK
				// 2 CANCEL
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete registry with id = " + registryId + "?", "Confirm Delete", JOptionPane.WARNING_MESSAGE);
				if (choice != JOptionPane.OK_OPTION) return; // CANCEL
				
				registry = new Registry(registryId);
				
				if ( admin.deleteRegistry(registry) ) { // Delete successful
					model.removeRow(modelRow);
					initializeJTextFieldAndComboBox(REGISTRY);
				} else {
					JOptionPane.showMessageDialog(null, "Cannot Delete");
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
		
		pnlAddRegistry = new JPanel(new GridBagLayout());
		pnlAddRegistry.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(BevelBorder.LOWERED),
				"Add Registry",
				TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP,
				ROBOTO_BOLD_SUB)
			);
		
		jtfRegistryId = new JTextField(15);
		jtfRegistryId.setFont(ROBOTO_PLAIN_SUB);
		jtfRegistryId.setEditable(false);
		jtfFname = new JTextField(15);
		jtfFname.setFont(ROBOTO_PLAIN_SUB);
		jtfLname = new JTextField(15);
		jtfLname.setFont(ROBOTO_PLAIN_SUB);
		jtfEmail = new JTextField(15);
		jtfEmail.setFont(ROBOTO_PLAIN_SUB);
		jtfPhone = new JTextField(15);
		jtfPhone.setFont(ROBOTO_PLAIN_SUB);
		jtfPassword = new JPasswordField(15);
		jtfPassword.setFont(ROBOTO_PLAIN_SUB);
		lblRegistryId = new JLabel("RegistryId");
		lblRegistryId.setFont(ROBOTO_PLAIN_TITLE);
		lblFname = new JLabel("fname");
		lblFname.setFont(ROBOTO_PLAIN_TITLE);
		lblLname = new JLabel("lname");
		lblLname.setFont(ROBOTO_PLAIN_TITLE);
		lblEmail = new JLabel("Email");
		lblEmail.setFont(ROBOTO_PLAIN_TITLE);
		lblPhone = new JLabel("Phone");
		lblPhone.setFont(ROBOTO_PLAIN_TITLE);
		lblPassword = new JLabel("Password");
		lblPassword.setFont(ROBOTO_PLAIN_TITLE);
		btnAddRegistry = new JButton("Add Registry");
		btnAddRegistry.setFont(ROBOTO_PLAIN_TITLE);
		
		gc = new GridBagConstraints();
		
		
		addGridBagComponent(pnlAddRegistry, lblRegistryId, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddRegistry, lblFname, 0, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddRegistry, lblLname, 0, 2, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddRegistry, lblEmail, 0, 3, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddRegistry, lblPhone, 0, 4, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddRegistry, lblPassword, 0, 5, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddRegistry, jtfRegistryId, 1, 0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddRegistry, jtfFname, 1, 1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddRegistry, jtfLname, 1, 2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddRegistry, jtfEmail, 1, 3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddRegistry, jtfPhone, 1, 4, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddRegistry, jtfPassword, 1, 5, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddRegistry, (JComponent) Box.createHorizontalGlue(), 2, 1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddRegistry, (JComponent) Box.createHorizontalGlue(), 2, 2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddRegistry, (JComponent) Box.createHorizontalGlue(), 2, 3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddRegistry, (JComponent) Box.createHorizontalGlue(), 2, 4, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddRegistry, (JComponent) Box.createHorizontalGlue(), 2, 5, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddRegistry, btnAddRegistry, 1, 6, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH);

		// Populate Table And Initialise fields for first card
		populateTable( tblRegistryList, Registry.readAll(admin.getUserConnection()) );
		initializeJTextFieldAndComboBox(REGISTRY);
		
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
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int modelRow = Integer.parseInt(e.getActionCommand());
				
				int lecturerId = (int) model.getValueAt(modelRow, 0);
				String teachingStatus = (String) model.getValueAt(modelRow, 6);
				String newStatus = (teachingStatus.equalsIgnoreCase("Active") ? "Not Active" : "Active");
						
				int choice = JOptionPane.showConfirmDialog(null, "Set teachingStatus to '" + newStatus + "' for lecturer with id: '" + lecturerId + "' ?", "Confirm Update", JOptionPane.WARNING_MESSAGE);
				if (choice != JOptionPane.OK_OPTION) return; // CANCEL
				
				lecturer = new Lecturer(lecturerId, newStatus);
				if ( admin.updateLecturer(lecturer) ) { // Update successful
					model.setValueAt(newStatus, modelRow, 6);
				} else {
					JOptionPane.showMessageDialog(null, "Cannot Update");
				}
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
		jtfLectId.setEditable(false);
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
		jtfLectPassword = new JPasswordField(15);
		jtfLectPassword.setFont(ROBOTO_PLAIN_SUB);
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
		lblLectPassword = new JLabel("Password");
		lblLectPassword.setFont(ROBOTO_PLAIN_TITLE);
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
		addGridBagComponent(pnlAddLecturer, lblLectPassword, 0, 6, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addGridBagComponent(pnlAddLecturer, jtfLectId, 1, 0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddLecturer, jtfLectFname, 1, 1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddLecturer, jtfLectLname, 1, 2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddLecturer, jtfLectEmail, 1, 3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddLecturer, jtfLectPhone, 1, 4, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddLecturer, jtfLectAddr, 1, 5, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddLecturer, jtfLectPassword, 1, 6, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		addGridBagComponent(pnlAddLecturer, btnAddLect, 1, 7, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH);

		
		pnlAssignLecturer = new JPanel(new GridBagLayout());
		pnlAssignLecturer.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(BevelBorder.LOWERED),
				"Assign Module",
				TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP,
				ROBOTO_BOLD_SUB)
			);

		jcbAssignLectNames = new JComboBox<>();
		jcbAssignLectNames.setFont(ROBOTO_PLAIN_SUB);
		jcbAssignLectNames.setMaximumRowCount(5);
		jcbAssignCourseNames = new JComboBox<>();
		jcbAssignCourseNames.setFont(ROBOTO_PLAIN_SUB);
		jcbAssignCourseNames.setMaximumRowCount(5);
		jcbAssignModuleNames = new JComboBox<>();
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
				
				int choice = JOptionPane.showConfirmDialog(null, "Set onOffer to '" + newStatus + "' for '" + courseName + "' ?", "Confirm Update", JOptionPane.WARNING_MESSAGE);
				if (choice != JOptionPane.OK_OPTION) return; // CANCEL
				
				course = new Course(courseCode, newStatus);
				if ( admin.updateCourse(course) ) { // Update successful
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
		
		jcbSearchCourseNames = new JComboBox<>();
		jcbSearchCourseNames.setFont(ROBOTO_PLAIN_SUB);
		jcbSearchModuleNames = new JComboBox<>();
		jcbSearchModuleNames.setFont(ROBOTO_PLAIN_SUB);
		lblFrom = new JLabel("From");
		lblFrom.setFont(ROBOTO_PLAIN_TITLE);
		lblTo = new JLabel("To");
		lblTo.setFont(ROBOTO_PLAIN_TITLE);
		jtfDateFrom = new JTextField(7);
		jtfDateFrom.setEditable(false);
		jtfDateFrom.setFont(ROBOTO_PLAIN_SUB);
		jtfDateTo = new JTextField(7);
		jtfDateTo.setEditable(false);
		jtfDateTo.setFont(ROBOTO_PLAIN_SUB);
		btnSearchModuleAttendance = new JButton("Search Module Attendance");
		btnSearchModuleAttendance.setFont(ROBOTO_PLAIN_TITLE);
		btnDateFrom = new JButton("Choose Date From");
		btnDateFrom.setFont(ROBOTO_PLAIN_SUB);
		btnDateTo = new JButton("Choose Date To");
		btnDateTo.setFont(ROBOTO_PLAIN_SUB);
		btnLoadReport = new JButton("Load Report");
		btnLoadReport.setFont(ROBOTO_PLAIN_TITLE);
		btnLoadDefaulterList = new JButton("Load Defaulter List");
		btnLoadDefaulterList.setFont(ROBOTO_PLAIN_TITLE);
		
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
		boxBtnLoadReport.add(btnLoadDefaulterList);
		boxBtnLoadReport.add(Box.createHorizontalStrut(14));
		boxBtnLoadReport.add(btnLoadReport);
		
		tblModuleAttendanceList = new JTable() {
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
			@Override
			public Class getColumnClass(int column) {
				if (column == 0 || column == 1) {
					return String.class;
				}
				return Boolean.class;
			}
		};

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
		
		boxStudentAttendance = Box.createVerticalBox();
		pnlSearchStudent = new JPanel(new GridBagLayout());
		
		jtfSearchStudent = new JTextField(7);
		jtfSearchStudent.setFont(ROBOTO_PLAIN_SUB);
		lblSearchStudent = new JLabel("Student Id/Name:");
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
		
		for (int i = 0; i < 3; i++) {
			tblStudentAttendanceModules[i] = new JTable() {
				
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
				
				@Override
				public Class getColumnClass(int column) {
					if (column == 0 || column == 1) {
						return String.class;
					}
					return Boolean.class;
				}
			};
			tblStudentAttendanceModules[i].setRowHeight(30);
			tblStudentAttendanceModules[i].setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tblStudentAttendanceModules[i].setFillsViewportHeight(true);
			tblStudentAttendanceModules[i].setPreferredScrollableViewportSize(new Dimension(1000, 100));
			
			scpStudentAttendanceModules[i] = new JScrollPane(tblStudentAttendanceModules[i]);
			scpStudentAttendanceModules[i].setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createBevelBorder(BevelBorder.LOWERED),
					"Student Module Attendance",
					TitledBorder.LEFT,
					TitledBorder.ABOVE_TOP,
					ROBOTO_BOLD_SUB)
				);
			scpStudentAttendanceModules[i].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scpStudentAttendanceModules[i].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		}
		
		boxModuleAttendanceList.add(pnlSearchOptions);
		boxModuleAttendanceList.add(Box.createVerticalStrut(10));
		boxModuleAttendanceList.add(scpModuleAttendanceList);
		boxModuleAttendanceList.add(Box.createVerticalStrut(7));
		boxModuleAttendanceList.add(boxBtnLoadReport);
		
		boxStudentAttendance.add(pnlSearchStudent);
		boxStudentAttendance.add(Box.createVerticalStrut(10));
		for (JScrollPane scp : scpStudentAttendanceModules) {
			boxStudentAttendance.add(scp);
		}
		
		panel.add(boxModuleAttendanceList);
		panel.add(boxStudentAttendance);
		
		return panel;
	}
	
	private void registerButtonListeners() {
		
		// Lecturer Card :: Add Lecturer
		btnAddLect.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int lectId = Integer.parseInt(jtfLectId.getText());
				String fname = jtfLectFname.getText();
				String lname = jtfLectLname.getText();
				String email = jtfLectEmail.getText();
				String address = jtfLectAddr.getText();
				String password = String.valueOf( jtfLectPassword.getPassword() );
				int phoneNo = Integer.parseInt(jtfLectPhone.getText());

				lecturer = new Lecturer(lectId, fname, lname, email, address, phoneNo, "Active", password);
				
				String message = "Cannot add lecturer";
				if ( admin.addLecturer(lecturer) ) { // Insert Successful
					message = "Lecturer Added";
					mdlLecturerList.addRow( lecturer.getObjectInfo() );
					initializeJTextFieldAndComboBox(LECTURER);
				}
				JOptionPane.showMessageDialog(null, message);
			}
		} );
		
		// Lecturer Card :: Show modules for selected Course
		jcbAssignCourseNames.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				
				if (e.getStateChange() == ItemEvent.SELECTED) {
					mdlCourseModuleNames = new DefaultComboBoxModel<>( courseModulesMap.get( (String) jcbAssignCourseNames.getSelectedItem() ) );
					jcbAssignModuleNames.setModel(mdlCourseModuleNames);
				}
			}
		} );
		
		// Lecturer Card :: Assign Lecturer to Module
		btnAssignLect.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int lecturerId = lecturerNameIdMap.get( (String) jcbAssignLectNames.getSelectedItem() );
				int moduleCode = moduleNameCodeMap.get ( (String) jcbAssignModuleNames.getSelectedItem() );
				
				// Read lecturer info
				lecturer = new Lecturer(lecturerId);
				lecturer.read(admin.getUserConnection());
				
				if ( !admin.assignModuleToLecturer(lecturer, moduleCode) ) {
					JOptionPane.showMessageDialog(null, "Cannot assign module with code: " + moduleCode + " to lecturer woth id: " + lecturerId, "Invalid Assignment", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				JOptionPane.showMessageDialog(null, "Module Assigned");
			}
		} );
		
		// Registry Card :: Add Registry
		btnAddRegistry.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int registryId = Integer.parseInt(jtfRegistryId.getText());
				String fname = jtfFname.getText();
				String lname = jtfLname.getText();
				String email = jtfEmail.getText();
				String password = String.valueOf( jtfPassword.getPassword() );
				int phoneNo = Integer.parseInt(jtfPhone.getText());

				registry = new Registry(registryId, fname, lname, phoneNo, email, password);
				
				String message = "Cannot add registry";
				if ( admin.addRegistry(registry) ) { // Insert Successful
					message = "Registry Added";
					mdlRegistryList.addRow( registry.getObjectInfo() );
					initializeJTextFieldAndComboBox(REGISTRY);
				}
				JOptionPane.showMessageDialog(null, message);
			}
		} );
		
		// Course Card :: Add Course
		btnAddCourse.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String courseName = jtfCourseName.getText();
				CourseModule[] modules = new CourseModule[3];
				
				modules[0] = new CourseModule(jtfModuleName1.getText());
				modules[1] = new CourseModule(jtfModuleName2.getText());
				modules[2] = new CourseModule(jtfModuleName3.getText());
				
				course = new Course(courseName, modules, "Available");
				
				String message = "Cannot add course";
				if ( admin.addCourse(course) ) { // Insert Successful
					message = "Course Added";
					mdlCourseList.addRow( course.getObjectInfo() );
					initializeJTextFieldAndComboBox(COURSE);
				}
				JOptionPane.showMessageDialog(null, message);
			}
		} );
		
		// Attendance Card :: Show modules for selected Course
		jcbSearchCourseNames.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				
				if (e.getStateChange() == ItemEvent.SELECTED) {
					mdlCourseModuleNames = new DefaultComboBoxModel<>( courseModulesMap.get( (String) jcbSearchCourseNames.getSelectedItem() ) );
					jcbSearchModuleNames.setModel(mdlCourseModuleNames);
				}
			}
		} );
		
		// Attendance Card :: Search Module attendances
		btnSearchModuleAttendance.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				String moduleSelected = (String) jcbSearchModuleNames.getSelectedItem();
				int moduleCode = moduleNameCodeMap.get(moduleSelected);
				module = new CourseModule( moduleCode, moduleSelected );
				
				String dateFromStr = jtfDateFrom.getText();
				String dateToStr = jtfDateTo.getText();
				
				if ( !(dateFromStr.isEmpty() || dateToStr.isEmpty()) ) {
					
					LocalDate dateFrom = LocalDate.parse(dateFromStr);
					LocalDate dateTo = LocalDate.parse(dateToStr);
					
					if (dateFrom.isAfter(dateTo)) {
						JOptionPane.showMessageDialog(null, "Date From cannot be after Date To", "Invalid Date", JOptionPane.WARNING_MESSAGE);
						return;
					}
					attendances = Attendance.readAttendance(admin.getUserConnection(), module, dateFrom, dateTo);
					
				} else {
					attendances = Attendance.readAttendance(admin.getUserConnection(), module);
				}
				
				lecturerId = attendances.get(0).getLecturerId();
				
				ArrayList<Boolean> presence;
				ArrayList<LocalDate> datesAbsent;
				moduleAttendancesColsData = new HashMap<>();
				defaultersDateAbsentMap = new HashMap<>();
				defaultersIdNameMap = new HashMap<>();
				for (Attendance a : attendances) {
					
					// Setting attendance for each date in same order as studentId
					if (!moduleAttendancesColsData.containsKey(a.getDate())) {
						moduleAttendancesColsData.put(a.getDate(), new ArrayList<>());
					}
					
					presence = moduleAttendancesColsData.get(a.getDate());
					presence.add(a.getPresence());
					moduleAttendancesColsData.put(a.getDate(), presence);
					
					// Setting defaulter-dates absent map
					if (a.getPresence()) continue; // Present
					
					int studentId = a.getStudent().getStudentId();
					
					if (!defaultersDateAbsentMap.containsKey(studentId)) {
						defaultersDateAbsentMap.put(studentId, new ArrayList<>());
						defaultersIdNameMap.put(studentId, a.getStudent().getStudentName());
					}
					
					datesAbsent = defaultersDateAbsentMap.get(studentId);
					datesAbsent.add(a.getDate());
					defaultersDateAbsentMap.put(studentId, datesAbsent);
				}
				
				// Retrieving student names and ids
				students = Student.readAll(admin.getUserConnection(), moduleCode);
				studentIds = new ArrayList<>();
				studentNames = new ArrayList<>();
				for (TableObjectInterface student : students) {
					studentIds.add( ((Student) student).getStudentId() );
					studentNames.add( ((Student) student).getStudentName() );
				}

				mdlModuleAttendanceList = new DefaultTableModel();
				mdlModuleAttendanceList.addColumn("studentId", studentIds.toArray());
				mdlModuleAttendanceList.addColumn("studentName", studentNames.toArray());
				
				for (Map.Entry<LocalDate, ArrayList<Boolean>> entry : moduleAttendancesColsData.entrySet()) {
					mdlModuleAttendanceList.addColumn(entry.getKey(), entry.getValue().toArray());
				}
				
				tblModuleAttendanceList.setModel(mdlModuleAttendanceList);
			}
		} );
		
		// Attendance Card :: Search Student attendances
		btnSearchStudentAttendance.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String studentIdStr = jtfSearchStudent.getText();
				if (studentIdStr.isEmpty()) return;
				
				boolean searchById = true;
				try {
					student = new Student( Integer.parseInt(studentIdStr) );
					
				} catch (NumberFormatException ex) {
					// Search by name
					student = new Student( studentIdStr );
					searchById = false;
				}
				
				if ( !student.read(admin.getUserConnection(), searchById) ) { // Invalid id
					JOptionPane.showMessageDialog(null,  "Student Not Found");
					return;
				}
				
				course = new Course(student.getCourseCode()); // Student enrolled course
				course.read(admin.getUserConnection()); // Get course modules
				studentModules = course.getModules();
								
				// Initialise first and second columns
				for (int i = 0; i < 3; i++) {
					scpStudentAttendanceModules[i].setBorder(BorderFactory.createTitledBorder(
							BorderFactory.createBevelBorder(BevelBorder.LOWERED),
							studentModules[i].getModuleName() + " Attendance",
							TitledBorder.LEFT,
							TitledBorder.ABOVE_TOP,
							ROBOTO_BOLD_SUB)
						);
					
					mdlStudentAttendanceModules[i] = new DefaultTableModel();					
					mdlStudentAttendanceModules[i].addColumn("moduleCode", new Integer[] { studentModules[i].getModuleCode() });
					mdlStudentAttendanceModules[i].addColumn("moduleName", new String[] { studentModules[i].getModuleName() });

					tblStudentAttendanceModules[i].setModel(mdlStudentAttendanceModules[i]);
				}
				
				// Fill Tables
				int i;
				attendances = Attendance.readAttendance(admin.getUserConnection(), student);
				for (Attendance a : attendances) {
					
					for (i = 0; studentModules[i].getModuleCode() != a.getModule().getModuleCode(); i++);
					mdlStudentAttendanceModules[i].addColumn(a.getDate(), new Boolean[] { a.getPresence() });
				}
			}
		} );
		
		// Attendance Card :: Load Report
		btnLoadReport.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Attendance not loaded in table yet, return;
				if (tblModuleAttendanceList.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Search Module Attendance First");
					return;
				}
				
				String message = "Excel Report Cannot be created.";
				if ( admin.loadReport(moduleAttendancesColsData, studentIds, studentNames, (String) jcbSearchModuleNames.getSelectedItem(), lecturerId) ) {
					message = "Excel Report Created Successfully";
				}
				
				JOptionPane.showMessageDialog(null, message);
			}
		} );
		
		// Attendance Card :: Load Defaulter List
		btnLoadDefaulterList.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Attendance not loaded in table yet, return;
				if (tblModuleAttendanceList.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Search Module Attendance First");
					return;
				}
				
				
				String message = "Excel Report Cannot be created.";
				if ( admin.loadDefaultersList(defaultersDateAbsentMap, defaultersIdNameMap, (String) jcbSearchModuleNames.getSelectedItem(), lecturerId) ) {
					message = "Excel Report Created Successfully";
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
	
	private void initializeJTextFieldAndComboBox(String command) {
		switch (command) {
			case REGISTRY:
				jtfRegistryId.setText( Integer.toString( Registry.getNextAvailableUserId(admin.getUserConnection()) ) );
				
				jtfFname.setText("");
				jtfLname.setText("");
				jtfEmail.setText("");
				jtfPhone.setText("");
				jtfPassword.setText("");

				break;
				
			case LECTURER:
				jtfLectId.setText( Integer.toString( Lecturer.getNextAvailableLecturerId(admin.getUserConnection()) ) );
				
				jtfLectFname.setText("");
				jtfLectLname.setText("");
				jtfLectEmail.setText("");
				jtfLectPhone.setText("");
				jtfLectAddr.setText("");
				
				lecturers = Lecturer.readAll(admin.getUserConnection());
				for (TableObjectInterface lecturer : lecturers) {
					if ( !((Lecturer) lecturer).isActive() ) continue;
					lecturerNameIdMap.put( ((Lecturer) lecturer).getFullName(), ((Lecturer) lecturer).getId() );
				}
				
				mdlLecturerNames = new DefaultComboBoxModel<>( lecturerNameIdMap.keySet().toArray(new String[lecturerNameIdMap.size()]) );
				jcbAssignLectNames.setModel(mdlLecturerNames);
				
				courses = Course.readAll(admin.getUserConnection());
				for (TableObjectInterface course : courses) {
					courseModulesMap.put( ((Course) course).getCourseName(), ((Course) course).getModuleNames());
					
					for (CourseModule m : ((Course) course).getModules()) {
						moduleNameCodeMap.put( m.getModuleName(), m.getModuleCode() );
					}
				}
				
				mdlCourseNames = new DefaultComboBoxModel<>( courseModulesMap.keySet().toArray(new String[courseModulesMap.size()]) );
				jcbAssignCourseNames.setModel(mdlCourseNames);
				
				mdlCourseModuleNames = new DefaultComboBoxModel<>( courseModulesMap.get( (String) jcbAssignCourseNames.getItemAt(0) ) );
				jcbAssignModuleNames.setModel(mdlCourseModuleNames);

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
				courses = Course.readAll(admin.getUserConnection());
				for (TableObjectInterface course : courses) {
					courseModulesMap.put( ((Course) course).getCourseName(), ((Course) course).getModuleNames());
					
					for (CourseModule m : ((Course) course).getModules()) {
						moduleNameCodeMap.put( m.getModuleName(), m.getModuleCode() );
					}
				}
																
				mdlCourseNames = new DefaultComboBoxModel<>( courseModulesMap.keySet().toArray(new String[courseModulesMap.size()]) );
				jcbSearchCourseNames.setModel(mdlCourseNames);
				
				mdlCourseModuleNames = new DefaultComboBoxModel<>( courseModulesMap.get( (String) jcbSearchCourseNames.getItemAt(0) ) );
				jcbSearchModuleNames.setModel(mdlCourseModuleNames);

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
				if (choice != JOptionPane.OK_OPTION) return; // CANCEL
				
				admin.logout();
				landingWindow.setVisible(true);
				dispose();
				
			} else {
				String command = e.getActionCommand();
				JTable table = null;
				ArrayList<TableObjectInterface> objList = new ArrayList<>();
				
				switch (command) {
					case REGISTRY:
						table = tblRegistryList;
						objList = Registry.readAll(admin.getUserConnection());
						break;
					case LECTURER:
						table = tblLecturerList;
						objList = Lecturer.readAll(admin.getUserConnection());
						break;	
					case COURSE:
						table = tblCourseList;
						objList = Course.readAll(admin.getUserConnection());
						break;
					case ATTENDANCE:
						break;
					default:
						CardLayout cl = (CardLayout) pnlCards.getLayout();
						cl.show(pnlCards, command);
						return;
						
				}
				
				if ( !command.equalsIgnoreCase(ATTENDANCE) ) populateTable(table, objList);
				initializeJTextFieldAndComboBox(command);
				
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
