/**
 * ContactsPanel.java
 * Created on 14 Feb. 2016
 * Package: net.sf.memoranda.ui
 * 
 * Contacts Panel implementation.
 * @author Moretti
 */
package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Contact;
import net.sf.memoranda.util.AppList;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.MimeType;
import net.sf.memoranda.util.MimeTypesList;
import net.sf.memoranda.util.Util;

import java.io.*;

public class ContactsPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    JToolBar toolBar = new JToolBar();
    JButton newConB = new JButton();
    ContactsTable contactsTable = new ContactsTable();
    JButton removeConB = new JButton();
    JScrollPane scrollPane = new JScrollPane();
    
  JPopupMenu conPPMenu = new JPopupMenu();
  JMenuItem ppRun = new JMenuItem();
  JMenuItem ppRemoveCon = new JMenuItem();
  JMenuItem ppNewCon = new JMenuItem();
  
    public ContactsPanel() {
        try {
            jbInit();
        }
        catch (Exception ex) {
           new ExceptionDialog(ex);
        }
    }
    
	/**
	 * Setup user interface and init dialog.
	 */
    void jbInit() throws Exception {
        toolBar.setFloatable(false);
        this.setLayout(borderLayout1);
        newConB.setIcon(
            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/addcontact.png")));
        newConB.setEnabled(true);
        newConB.setMaximumSize(new Dimension(24, 24));
        newConB.setMinimumSize(new Dimension(24, 24));
        newConB.setToolTipText(Local.getString("New Contact"));
        newConB.setRequestFocusEnabled(false);
        newConB.setPreferredSize(new Dimension(24, 24));
        newConB.setFocusable(false);
        newConB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newConB_actionPerformed(e);
            }
        });
        newConB.setBorderPainted(false);
        contactsTable.setMaximumSize(new Dimension(32767, 32767));
        contactsTable.setRowHeight(24);
        removeConB.setBorderPainted(false);
        removeConB.setFocusable(false);
        removeConB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeConB_actionPerformed(e);
            }
        });
        removeConB.setPreferredSize(new Dimension(24, 24));
        removeConB.setRequestFocusEnabled(false);
        removeConB.setToolTipText(Local.getString("Remove Contact"));
        removeConB.setMinimumSize(new Dimension(24, 24));
        removeConB.setMaximumSize(new Dimension(24, 24));
        removeConB.setIcon(
            new ImageIcon(
                net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/removecontact.png")));
        removeConB.setEnabled(false);
        scrollPane.getViewport().setBackground(Color.white);
        toolBar.addSeparator(new Dimension(8, 24));
        toolBar.addSeparator(new Dimension(8, 24));


        contactsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                boolean enbl = (contactsTable.getRowCount() > 0) && (contactsTable.getSelectedRow() > -1);

                removeConB.setEnabled(enbl); ppRemoveCon.setEnabled(enbl);
                ppRun.setEnabled(enbl);
            }
        });
        
        conPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
    ppRemoveCon.setFont(new java.awt.Font("Dialog", 1, 11));
    ppRemoveCon.setText(Local.getString("Remove contact"));
    ppRemoveCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppRemoveCon_actionPerformed(e);
            }
        });
    ppRemoveCon.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/removecontact.png")));
    ppRemoveCon.setEnabled(false);
    ppNewCon.setFont(new java.awt.Font("Dialog", 1, 11));
    ppNewCon.setText(Local.getString("New contact")+"...");
    ppNewCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppNewCon_actionPerformed(e);
            }
        });
    ppNewCon.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/addcontact.png")));

    toolBar.add(newConB, null);
        toolBar.add(removeConB, null);
        this.add(scrollPane, BorderLayout.CENTER);
        scrollPane.getViewport().add(contactsTable, null);
        this.add(toolBar, BorderLayout.NORTH);
    conPPMenu.add(ppRun);
    conPPMenu.addSeparator();
    conPPMenu.add(ppNewCon);
    conPPMenu.add(ppRemoveCon);
    conPPMenu.addSeparator();
	
		// remove resources using the DEL key
		contactsTable.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e){
				if(contactsTable.getSelectedRows().length>0 
					&& e.getKeyCode()==KeyEvent.VK_DELETE)
					ppRemoveCon_actionPerformed(null);
			}
			public void	keyReleased(KeyEvent e){}
			public void keyTyped(KeyEvent e){} 
		});
    }
    
	/**
	 * Prompts for a new contact.
	 * @see net.sf.memoranda.ui.AddContactDialog#AddContactDialog()
	 * @param e Action trigger to notify when 'New Contact' is pressed
	 */
    void newConB_actionPerformed(ActionEvent e) {
        AddContactDialog dlg = new AddContactDialog(App.getFrame(), Local.getString("New contact"));
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
       	CurrentProject.getContactsList().addContact(dlg.nameField.getText(), dlg.emailField.getText(),
       			dlg.phoneField.getText(), dlg.notesField.getText());            	     	
        contactsTable.tableChanged();
    }

	/**
	 * Removes a contact that is selected from the table and Element.
	 * @see net.sf.memoranda.ui.ContactsTable#tableChanged()
	 * @param e Action trigger to notify when 'Remove Contact' is pressed
	 */
    void removeConB_actionPerformed(ActionEvent e) {
        int[] toRemove = contactsTable.getSelectedRows();
        String msg = "";
        if (toRemove.length == 1)
            msg =
                Local.getString("Remove the contact")
                    + "\n'"
                    + contactsTable.getModel().getValueAt(toRemove[0], 0)
                    + "'";

        else
            msg = Local.getString("Remove") + " " + toRemove.length + " " + Local.getString("contacts");
        msg +=
            "\n"
            + Local.getString("Are you sure?");
        int n =
            JOptionPane.showConfirmDialog(
                App.getFrame(),
                msg,
                Local.getString("Remove contact"),
                JOptionPane.YES_NO_OPTION);
        if (n != JOptionPane.YES_OPTION)
            return;
        for (int i = 0; i < toRemove.length; i++) {        	
        		CurrentProject.getContactsList().removeContact(
                        ((Contact) contactsTable.getModel().getValueAt(toRemove[i], ContactsTable._CONTACT)).getName());
        }
        contactsTable.tableChanged();
    }

  void ppRemoveCon_actionPerformed(ActionEvent e) {
    removeConB_actionPerformed(e);
  }
  void ppNewCon_actionPerformed(ActionEvent e) {
    newConB_actionPerformed(e);
  }
}