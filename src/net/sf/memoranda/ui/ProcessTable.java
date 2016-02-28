/**
 * ProcessTable.java
 * Created on 25 Feb. 2016
 * Package: net.sf.memoranda.ui
 * 
 * Table for Processes display implementation.
 * @author Meyung
 */
package net.sf.memoranda.ui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import net.sf.memoranda.*;
import net.sf.memoranda.Process;
import net.sf.memoranda.ui.table.TableSorter;
import net.sf.memoranda.util.Local;
import java.awt.Component;
import java.awt.Font;
import java.util.Vector;

public class ProcessTable extends JTable {

	Vector processes = null;
	TableSorter sorter = null;

	ImageIcon conIcon = new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/mimetypes/processes.png"));

	/**
	 * Setup Processes table and init table.
	 */
	public ProcessTable() {
		super();
		initTable();
		sorter = new TableSorter(new ProcessesTableModel());
		sorter.addMouseListenerToHeaderInTable(this);
		setModel(sorter);
		this.setShowGrid(false);
		this.setFont(new Font("Dialog",0,11));
		initColumsWidth();
		CurrentProject.addProjectListener(new ProjectListener() {
			public void projectChange(Project p, NoteList nl, TaskList tl, ResourcesList rl, ContactsList cl, ProcessList pl) {                

			}
			public void projectWasChanged() {
				tableChanged();
			}
		});
	}

	/**
	 * Initialize column widths for the processes table.
	 */
	void initColumsWidth() {
		for (int i = 0; i < 4; i++) {
			TableColumn column = getColumnModel().getColumn(i);
			if (i == 0) {
				column.setPreferredWidth(200);
			} else if (i == 1) {
				column.setMinWidth(50);
				column.setPreferredWidth(50);
			} else if (i == 2) {
				column.setMinWidth(15);
				column.setPreferredWidth(15);
			} else {
				column.setMinWidth(335);
				column.setPreferredWidth(335);
			}
		}
	}

	/**
	 * Event method to update UI and table entries.
	 */
	public void tableChanged() {
		initTable();
		sorter.tableChanged(null);
		initColumsWidth();
		updateUI();
	}

	/**
	 * Initializes table with s list.
	 * @see net.sf.memoranda.ProcessList#getAllPreocesses()
	 */
	public void initTable() {
		Vector v = CurrentProject.getProcessList().getAllProcesses();
		processes = new Vector();
		for (int i = 0; i < v.size(); i++) {
			Process c = (Process)v.get(i);
			processes.add(c);
		}

	}

	public static final int _PROCESS = 100;

	public TableCellRenderer getCellRenderer(int row, int column) {
		return new javax.swing.table.DefaultTableCellRenderer() {

			public Component getTableCellRendererComponent(
					JTable table,
					Object value,
					boolean isSelected,
					boolean hasFocus,
					int row,
					int column) {
				JLabel comp;

				comp = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (column == 0) {
					Process c = (Process)getModel().getValueAt(row, _PROCESS);
					comp.setIcon(conIcon);
				}
				return comp;
			}
		};

	}

	/**
	 * Class to configure columns and establish getters for table values.
	 */
	class ProcessesTableModel extends AbstractTableModel {

		String[] columnNames = {
				Local.getString("Process Name"),
				Local.getString("Scrum Master"),
				Local.getString("Product Owner"),
				Local.getString("Team Member Names"),
				Local.getString("Sprint Planning Meeting"),
				Local.getString("Daily Stand Up"),
				Local.getString("Sprint Review"),
				Local.getString("Sprint Retrospective")};

		public String getColumnName(int i) {
			return columnNames[i];
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return processes.size();
		}


		/**
		 * Method to get value within the table based on row and column.
		 * @param row The row to obtain the value
		 * @param col The column to obtain the value
		 * @returns The value at the specific location on the table
		 */
		public Object getValueAt(int row, int col) {
			Process c = (Process)processes.get(row);
			if (col == _PROCESS) {
				return c;
			}
			if (col == 0) {
				return c.getProcessName();
			} else if (col == 1) {
				return c.getScrumMaster();
			} else if (col == 2) {
				return c.getProductOwner();
			} else if (col == 3) {
				return c.getTeamMembers();
			} else if (col == 4) {
				return c.getSprintPlanningMeeting();
			} else if (col == 5) {
				return c.getDailyStandUp();
			} else if (col == 6) {
				return c.getSprintReview();
			} else if (col == 7) {
				return c.getSprintRetrospective();
			}
			return null;
		}


		@SuppressWarnings("unchecked")
		public Class getColumnClass(int col) {
			try {
				switch (col) {
				case 0 :
					return Class.forName("java.lang.String");
				case 1 :
					return Class.forName("java.lang.String");
				case 2 :
					return Class.forName("java.lang.String");
				case 3 :
					return Class.forName("java.lang.String");
				case 4 :
					return Class.forName("java.lang.String");
				case 5 :
					return Class.forName("java.lang.String");
				case 6 :
					return Class.forName("java.lang.String");
				case 7 :
					return Class.forName("java.lang.String");
				}
			}
			catch (Exception ex) {new ExceptionDialog(ex);}
			return null;
		}
	}

}