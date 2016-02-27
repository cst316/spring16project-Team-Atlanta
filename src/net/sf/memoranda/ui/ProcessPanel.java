/**
 * ProcessPanel.java
 * Created on 23 Feb. 2016
 * Package: net.sf.memoranda
 * 
 * Process Panel class.
 * @author Meyung
 *
 */
package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.memoranda.Contact;
import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.util.Context;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.ProcessListImpl;

public class ProcessPanel extends JPanel {
	BorderLayout borderLayout1 = new BorderLayout();
	JToolBar processToolBar = new JToolBar();
	JButton newProcessB = new JButton();
	JButton removeProcessB = new JButton();
	JScrollPane scrollPane = new JScrollPane();
	ProcessTable processTable = new ProcessTable();
	JPopupMenu processPPMenu = new JPopupMenu();
	JMenuItem ppRun = new JMenuItem();
	JMenuItem ppRemovePro = new JMenuItem();
	JMenuItem ppNewPro = new JMenuItem();
	JCheckBoxMenuItem ppShowActiveOnlyChB = new JCheckBoxMenuItem();


	public ProcessPanel() {
		try {
			jbInit();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
	}

	/**
	 * jbInit() This method sets up the user interface for the Process UI.
	 * 
	 * @throws Exception
	 */
	void jbInit() throws Exception {
		processToolBar.setFloatable(false);
		newProcessB.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_new.png")));
		newProcessB.setEnabled(true);
		newProcessB.setMaximumSize(new Dimension(24, 24));
		newProcessB.setMinimumSize(new Dimension(24, 24));
		newProcessB.setToolTipText(Local.getString("Create new Process"));
		newProcessB.setRequestFocusEnabled(false);
		newProcessB.setPreferredSize(new Dimension(24, 24));
		newProcessB.setFocusable(false);
		newProcessB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newProcessB_actionPerformed(e);
			}
		});
		newProcessB.setBorderPainted(false);
		processTable.setMaximumSize(new Dimension(32767, 32767));
		processTable.setRowHeight(24);
		removeProcessB.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_remove.png")));
		removeProcessB.setEnabled(true);
		removeProcessB.setMaximumSize(new Dimension(24, 24));
		removeProcessB.setMinimumSize(new Dimension(24, 24));
		removeProcessB.setToolTipText(Local.getString("Delete Process"));
		removeProcessB.setRequestFocusEnabled(false);
		removeProcessB.setPreferredSize(new Dimension(24, 24));
		removeProcessB.setFocusable(false);
		removeProcessB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeProcessB_actionPerformed(e);
			}
		});
		ppShowActiveOnlyChB.setFont(new java.awt.Font("Dialog", 1, 11));
		ppShowActiveOnlyChB.setText(Local.getString("Show Active only"));
		boolean isShao = (Context.get("SHOW_ACTIVE_TASKS_ONLY") != null)
				&& (Context.get("SHOW_ACTIVE_TASKS_ONLY").equals("true"));
		ppShowActiveOnlyChB.setSelected(isShao);
		this.setLayout(borderLayout1);
		scrollPane.getViewport().setBackground(Color.white);
		this.add(scrollPane, BorderLayout.CENTER);
		processToolBar.addSeparator(new Dimension(8, 24));
		processToolBar.add(newProcessB, null);
		processToolBar.addSeparator(new Dimension(8, 24));
		processToolBar.add(removeProcessB, null);
		processToolBar.addSeparator(new Dimension(8, 24));
		this.add(processToolBar, BorderLayout.NORTH);

		processTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				boolean enbl = (processTable.getRowCount() > 0) && (processTable.getSelectedRow() > -1);

				removeProcessB.setEnabled(enbl); ppRemovePro.setEnabled(enbl);
				ppRun.setEnabled(enbl);
			}
		});

		processPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
		ppRemovePro.setFont(new java.awt.Font("Dialog", 1, 11));
		ppRemovePro.setText(Local.getString("Remove process"));
		ppRemovePro.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ppRemovePro_actionPerformed(e);
			}
		});
		ppRemovePro.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_remove.png")));
		ppRemovePro.setEnabled(false);
		ppNewPro.setFont(new java.awt.Font("Dialog", 1, 11));
		ppNewPro.setText(Local.getString("New contact")+"...");
		ppNewPro.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ppNewPro_actionPerformed(e);
			}
		});
		ppNewPro.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_new.png")));

		processToolBar.add(ppNewPro, null);
		processToolBar.add(ppRemovePro, null);
		this.add(scrollPane, BorderLayout.CENTER);
		scrollPane.getViewport().add(processTable, null);
		this.add(processToolBar, BorderLayout.NORTH);
		processPPMenu.add(ppRun);
		processPPMenu.addSeparator();
		processPPMenu.add(ppNewPro);
		processPPMenu.add(ppRemovePro);
		processPPMenu.addSeparator();

		// remove resources using the DEL key
		processTable.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e){
				if(processTable.getSelectedRows().length>0 
						&& e.getKeyCode()==KeyEvent.VK_DELETE)
					ppRemovePro_actionPerformed(null);
			}
			public void	keyReleased(KeyEvent e){}
			public void keyTyped(KeyEvent e){} 
		});
	}

	void newProcessB_actionPerformed(ActionEvent e) {
		ProcessDialog dlg = new ProcessDialog(App.getFrame(), Local.getString("New Process"));
		Dimension frmSize = App.getFrame().getSize();
		Point loc = App.getFrame().getLocation();
		dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
		dlg.setVisible(true);
		if (dlg.CANCELLED)
			return;
		CurrentProject.getProcessList().addProcess(dlg.txtProcessName.getText(), dlg.txtScrumMasterName.getText(), dlg.txtProductOwnerName.getText(),
				dlg.txtTeamMemberNames.getText(), dlg.txtSprintPlanningMeeting.getText(), dlg.txtDailyStandUp.getText(),
				dlg.txtSprintReview.getText(), dlg.txtSprintRetrospective.getText());
		processTable.tableChanged();
	}

	void removeProcessB_actionPerformed(ActionEvent e) {
		int[] toRemove = processTable.getSelectedRows();
		String msg = "";
		if (toRemove.length == 1)
			msg =
			Local.getString("Remove the process")
			+ "\n'"
			+ processTable.getModel().getValueAt(toRemove[0], 0)
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
						Local.getString("Remove process"),
						JOptionPane.YES_NO_OPTION);
		if (n != JOptionPane.YES_OPTION)
			return;
		for (int i = 0; i < toRemove.length; i++) {        	
			CurrentProject.getProcessList().removeProcess(
					((net.sf.memoranda.Process) processTable.getModel().getValueAt(toRemove[i], ProcessTable._PROCESS)).getProcessName());
		}
		processTable.tableChanged();
	}

	void ppRemovePro_actionPerformed(ActionEvent e) {
		removeProcessB_actionPerformed(e);
	}
	void ppNewPro_actionPerformed(ActionEvent e) {
		newProcessB_actionPerformed(e);
	}
}