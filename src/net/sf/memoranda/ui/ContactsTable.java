/**
 * ContactsTable.java
 * Created on 14 Feb. 2016
 * Package: net.sf.memoranda.ui
 * 
 * Table for contacts display implementation.
 * @author Moretti
 */
package net.sf.memoranda.ui;

import java.awt.Component;
import java.awt.Font;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import net.sf.memoranda.Contact;
import net.sf.memoranda.ContactsList;
import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.ProcessList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.ui.table.TableSorter;
import net.sf.memoranda.util.Local;

public class ContactsTable extends JTable {

    Vector contacts = null;
    TableSorter sorter = null;
    
    ImageIcon conIcon = new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/mimetypes/contact.png"));
    
	/**
	 * Setup contacts table and init table.
	 */
    public ContactsTable() {
        super();
        initTable();
        sorter = new TableSorter(new ContactTableModel());
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
	 * Initialize column widths for the contacts table.
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
	 * Initializes table with contacts list.
	 * @see net.sf.memoranda.ContactsList#getAllContacts()
	 */
    public void initTable() {
        Vector v = CurrentProject.getContactsList().getAllContacts();
        contacts = new Vector();
        for (int i = 0; i < v.size(); i++) {
            Contact c = (Contact)v.get(i);
            contacts.add(c);
        }

    }
    
     public static final int _CONTACT = 100;

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
                  Contact c = (Contact)getModel().getValueAt(row, _CONTACT);
                  comp.setIcon(conIcon);
                }
                return comp;
            }
        };

    }

	/**
	 * Class to configure columns and establish getters for table values.
	 */
    class ContactTableModel extends AbstractTableModel {

        String[] columnNames = {
                Local.getString("Name"),
                Local.getString("E-Mail"),
                Local.getString("Phone Number"),
                Local.getString("Notes")};

        public String getColumnName(int i) {
            return columnNames[i];
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return contacts.size();
        }
        

    	/**
    	 * Method to get value within the table based on row and column.
    	 * @param row The row to obtain the value
    	 * @param col The column to obtain the value
    	 * @returns The value at the specific location on the table
    	 */
        public Object getValueAt(int row, int col) {
        	Contact c = (Contact)contacts.get(row);
            if (col == _CONTACT) {
                return c;
            }
            if (col == 0) {
                return c.getName();
            } else if (col == 1) {
            	return c.getEmail();
            } else if (col == 2) {
            	return c.getPhone();
            } else if (col == 3) {
            	return c.getNotes();
            }
            return null;
        }

 
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
            }
            }
            catch (Exception ex) {new ExceptionDialog(ex);}
            return null;
        }
    }

}