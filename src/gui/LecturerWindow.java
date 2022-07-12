package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import model.Attendance;
import model.CourseModule;
import model.Lecturer;
import model.Student;
import model.TableObjectInterface;

public class LecturerWindow extends JFrame {
	
	private JFrame landingWindow;
	private Lecturer lecturer;
	
	private Map<String, Integer> moduleNameCodeMap = new HashMap <String, Integer>();
	private ArrayList<Attendance> attendances; 
	private CourseModule module;
	private Student student;
	private boolean isPresent;
	
	private static final String ATTENDANCE = "ATTENDANCE";
	private static final String SAVE = "Save";
	private static final String LOGOUT = "Logout";
	
	private static final Font ROBOTO_BOLD_TITLE = new Font("roboto", Font.BOLD, 20);
	private static final Font ROBOTO_BOLD_SUB = new Font("roboto", Font.BOLD, 16);
	private static final Font ROBOTO_PLAIN_TITLE = new Font("roboto", Font.PLAIN, 16);
	private static final Font ROBOTO_PLAIN_SUB = new Font("roboto", Font.PLAIN, 14);
	
	
	private JPanel pnlContent;
	private JPanel pnlMenu;
	private JPanel pnlDate;
	private JPanel pnlCards;
	private JPanel pnlCardAttendance;
	
	private LocalDate today;
	private JLabel lblDate;
	private JLabel lblDay;
	
	private JButton[] btnsMenu = new JButton[2];
	private JButton btnLogout;
	
	// Attendance Components
	private JTable tblAttendanceList;
	private DefaultTableModel mdlAttendanceList;
	private JScrollPane scpAttendanceList;
	private String[] attendanceTableCols;
	private JComboBox<String> jcbModulesTaught;


	public LecturerWindow(Lecturer user, JFrame frame) {
		super("Lecturer");
		landingWindow = frame;
		lecturer = user;
	
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
		btnsMenu[0] = new JButton(SAVE);
		btnsMenu[1] = btnLogout = new JButton(LOGOUT);
		
		jcbModulesTaught = new JComboBox<>();
		jcbModulesTaught.setFont(ROBOTO_PLAIN_SUB);
		jcbModulesTaught.setMaximumRowCount(5);		
		jcbModulesTaught.setEditable(false);
		       
		pnlMenu.add(jcbModulesTaught);
		add(pnlMenu);
		
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
		
		// Attendance Card
		pnlCardAttendance = new JPanel(new GridLayout(0, 1, 0, 5));
		
		attendanceTableCols = new String[] {"Student ID", "Student Name", "Present"};
		mdlAttendanceList = new DefaultTableModel();
		mdlAttendanceList.setColumnIdentifiers(attendanceTableCols);
		
		tblAttendanceList = new JTable() {
		
			@Override
			public boolean isCellEditable(int row, int column) {
				return (column == 2);
			}
			
			@Override
			public Class getColumnClass(int column) {
				if (column == 2) {
					return Boolean.class;
				}
				return String.class;
			}
		};
		tblAttendanceList.setModel(mdlAttendanceList);
		tblAttendanceList.setRowHeight(30);
		tblAttendanceList.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblAttendanceList.setFillsViewportHeight(true);
		tblAttendanceList.setPreferredScrollableViewportSize(new Dimension(1000, 300));
		
		scpAttendanceList = new JScrollPane(tblAttendanceList);
		scpAttendanceList.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createBevelBorder(BevelBorder.LOWERED),
			"Module Attendance",
			TitledBorder.LEFT,
			TitledBorder.ABOVE_TOP,
			ROBOTO_BOLD_SUB)
		);
		scpAttendanceList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scpAttendanceList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		pnlCardAttendance.add(scpAttendanceList);
		pnlCards.add(pnlCardAttendance, ATTENDANCE);
		
		pnlContent = new JPanel(new BorderLayout());
		pnlContent.add(pnlMenu, BorderLayout.LINE_START);
		pnlContent.add(pnlCards, BorderLayout.CENTER);
		
		
		// Read lecturer modules
		lecturer.read(lecturer.getUserConnection());
		ArrayList<CourseModule> modulesTaught = lecturer.getModulesTaught();
		
		// Set module name-code map for modules for which attendance not taken yet
		ArrayList<String> moduleNamesTaught = new ArrayList<>();
		for(CourseModule cm: modulesTaught) {

			if ( Attendance.isAttendanceAlreadyTaken(lecturer.getUserConnection(), cm.getModuleCode()) ) continue;
			
			moduleNamesTaught.add(cm.getModuleName());
			moduleNameCodeMap.put(cm.getModuleName(), cm.getModuleCode());
		}
		
		// Set JComboBox for modules taught by lecturer; For which attendance not taken yet for current date
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>( moduleNamesTaught.toArray( new String[moduleNamesTaught.size()] ) );
		jcbModulesTaught.setModel(model);
		
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
				
				lecturer.logout();
				System.exit(0);
			}
		} );
		
		add(pnlContent);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		// Initialise table with student list of selected module, if any
		initialiseAttendanceTable();
	}
	
	private void registerButtonListeners() {
		
		// Populate table with student list of selected module
		jcbModulesTaught.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				
				if (e.getStateChange() == ItemEvent.SELECTED) {
					// Initialise table with student list of selected module, if any
					initialiseAttendanceTable();
				}
			}
		} );
	}
	
	private void initialiseAttendanceTable() {
		// Initialise table with student list of selected module, if any
		String selectedModule = (String) jcbModulesTaught.getSelectedItem();
		
		if (selectedModule == null || selectedModule.isBlank()) {
			mdlAttendanceList.setRowCount(0); // Clear Table
			JOptionPane.showMessageDialog(null, "No modules to take attendance");
			
		} else {
			ArrayList<TableObjectInterface> studentList = Student.readAll(lecturer.getUserConnection(), moduleNameCodeMap.get(selectedModule));
			
			if (studentList.isEmpty()) {
				JOptionPane.showMessageDialog(null, "No Students Enrolled in '" + selectedModule + "'");
				return;
			}
			
			populateTable(tblAttendanceList, studentList);			
		}
	}

	private void populateTable(JTable table, ArrayList<TableObjectInterface> objList) {
		// Clear any existing data
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		
		for (TableObjectInterface obj : objList) {
			model.addRow( new Object[] { ((Student) obj).getStudentId(), ((Student) obj).getStudentName(), false } );
		}
	}
	
	// Menu JButton Click Event Handler
	class MenuBtnActionHandler implements ActionListener {
	
		public void actionPerformed(ActionEvent e) {
			
			// If LOGOUT, show confirm dialog
			if(e.getSource() == btnLogout) {
				// 0 OK
				// 2 CANCEL
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.WARNING_MESSAGE);
				if (choice != JOptionPane.OK_OPTION) return; // CANCEL
				
				lecturer.logout();
				landingWindow.setVisible(true);
				dispose();
				
			} else { // SAVE attendance
				int numRows = mdlAttendanceList.getRowCount();
				attendances = new ArrayList<>();
				
				if (numRows  == 0) return; // Empty Table

				while (--numRows >= 0) { // Table rows/columns indexed from 0
					student = new Student( (Integer) mdlAttendanceList.getValueAt(numRows, 0) );
					module = new CourseModule( moduleNameCodeMap.get(jcbModulesTaught.getSelectedItem()) );
					isPresent = (Boolean) mdlAttendanceList.getValueAt(numRows, 2);
					
					attendances.add( new Attendance( lecturer.getId(), module, student, today, isPresent ) );
				}
				
				
				lecturer.takeAttendance(attendances);
				JOptionPane.showMessageDialog(null, "Attendance Taken");
				
				// Remove module from JComboBox
				DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) jcbModulesTaught.getModel();
				model.removeElementAt( jcbModulesTaught.getSelectedIndex() );
				
				// Initialise table with student list of selected module, if any
				initialiseAttendanceTable();
				
			}
		}
	
	}
}
