package vendor;

// Reference:
// https://tips4java.wordpress.com/2009/07/12/table-button-column/

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

	//JButton Renderer/Editor
	public class ButtonColumn extends AbstractCellEditor
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
			btnEditor = new JButton();
			btnEditor.addActionListener(this);
			
			TableColumnModel columnModel = table.getColumnModel();
			columnModel.getColumn(column).setCellRenderer(this);
			columnModel.getColumn(column).setCellEditor(this);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			btnRenderer.setText(value.toString());
			return btnRenderer;
		}
		
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			editorValue = value;
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