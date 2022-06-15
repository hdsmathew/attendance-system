package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonModel;
import javax.swing.JButton;
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
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class AdminWindow extends JFrame {
	
	public static final String REGISTRY = "Registry";
	public static final String LECTURER = "Lecturer";
	public static final String COURSE = "Course";
	public static final String ATTENDANCE = "Attendance";
	public static final String LOGOUT = "Logout";
	
	public static final Font ROBOTO_BOLD_TITLE = new Font("roboto", Font.BOLD, 18);
	public static final Font ROBOTO_BOLD_SUB = new Font("roboto", Font.BOLD, 14);
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
		
	public JPanel pnlAddRegistry;
	public JPanel pnlRegistryList;
	public JTable tblRegistryList;
	public DefaultTableModel mdlRegistryList;
	public String[] registryCols = new String[5];
	public JScrollPane scpRegistryList;
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
		btnsMenu[0] = btnRegistry = new JButton("Registry");
		btnsMenu[1] = btnLecturer = new JButton("Lecturer");
		btnsMenu[2] = btnCourse = new JButton("Course");
		btnsMenu[3] = btnAttendance = new JButton("Attendance");
		btnsMenu[4] = btnLogout = new JButton("Logout");

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
		
		registryCols = new String[] {"userId", "name", "email", "phome", "manage"};
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
		ButtonColumn buttonColumn = new ButtonColumn(tblRegistryList, delete, 4);
		
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
		jtfName = new JTextField(15);
		jtfEmail = new JTextField(15);
		jtfPhone = new JTextField(15);
		jtfPassword = new JPasswordField(15);
		lblUserId = new JLabel("UserId");
		lblName = new JLabel("Name");
		lblEmail = new JLabel("Email");
		lblPhone = new JLabel("Phone");
		lblPassword = new JLabel("Password");
		btnAdd = new JButton("Add");
		
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
		pnlAddRegistry.add(jtfUserId, gc);
		
		gc.gridy = 1;
		pnlAddRegistry.add(jtfName, gc);
		
		gc.gridy = 2;
		pnlAddRegistry.add(jtfEmail, gc);
		
		gc.gridy = 3;
		pnlAddRegistry.add(jtfPhone, gc);
		
		gc.gridy = 4;
		pnlAddRegistry.add(jtfPassword, gc);
				
		gc.gridx = 1;
		gc.gridy = 5;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		pnlAddRegistry.add(btnAdd, gc);

		
		pnlCardRegistry.add(scpRegistryList);
		pnlCardRegistry.add(pnlAddRegistry);
		pnlCards.add(pnlCardRegistry, REGISTRY);
		
		// Lecturer Card Panel
		pnlCardLecturer= new JPanel();
		pnlCards.add(pnlCardLecturer, LECTURER);
		
		// Course Card Panel
		pnlCardCourse = new JPanel();
		pnlCards.add(pnlCardCourse, COURSE);
		
		// Attendance Card Panel
		pnlCardAttendance = new JPanel();
		pnlCards.add(pnlCardAttendance, ATTENDANCE);
		
		
		pnlContent = new JPanel(new BorderLayout());
		pnlContent.add(pnlMenu, BorderLayout.LINE_START);
		pnlContent.add(pnlCards, BorderLayout.CENTER);
		
		add(pnlContent);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	// JButton Renderer/Editor
	class ButtonColumn extends AbstractCellEditor
		implements TableCellRenderer, TableCellEditor, ActionListener {
		
		private JTable table;
		private Action action;
		
		private Object editorValue;
		private JButton btnRenderer;
		private JButton btnEditor;
		
		public ButtonColumn(JTable table, Action action, int column) {
			this.table = table;
			this.action = action;
			
			btnRenderer = new JButton();
			btnRenderer.setOpaque(true);
			btnEditor = new JButton();
			btnEditor.addActionListener(this);
			
			TableColumnModel columnModel = table.getColumnModel();
			columnModel.getColumn(column).setCellRenderer(this);
			columnModel.getColumn(column).setCellEditor(this);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			editorValue = value;
			btnRenderer.setText(value.toString());
			return btnRenderer;
		}
		
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			btnEditor.setText(value.toString());
			return btnEditor;
		}

		public void actionPerformed(ActionEvent e) {
			int selectedRow = table.convertRowIndexToModel(table.getEditingRow());
			fireEditingStopped(); // Stop Editing
			
			// Invoke Action
			ActionEvent event = new ActionEvent(table, ActionEvent.ACTION_PERFORMED, Integer.toString(selectedRow));
			action.actionPerformed(event);
			
		}

		@Override
		public Object getCellEditorValue() {
			return editorValue;
		}
		
	}
	
	//JButton Click Event Handler
	class MenuBtnActionHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
		}
		
	}
}
