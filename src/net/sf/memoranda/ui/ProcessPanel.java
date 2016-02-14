package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Vector;

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

import net.sf.memoranda.ContactsList;
import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.History;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.Task;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.date.DateListener;
import net.sf.memoranda.util.Context;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;

/**
 * 
 * @author Meyung 
 * this code was based off of the TaskPanel.java class
 */
public class ProcessPanel extends JPanel {
	BorderLayout borderLayout1 = new BorderLayout();
	JButton historyBackB = new JButton();
	JToolBar processToolBar = new JToolBar();
	JButton historyForwardB = new JButton();
	JButton newProcessB = new JButton();

	JCheckBoxMenuItem ppShowActiveOnlyChB = new JCheckBoxMenuItem();

	JScrollPane scrollPane = new JScrollPane();
	DailyItemsPanel parentPanel = null;

	public ProcessPanel(DailyItemsPanel _parentPanel) {
		try {
			parentPanel = _parentPanel;
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void jbInit() throws Exception {
		processToolBar.setFloatable(false);

		historyBackB.setAction(History.historyBackAction);
		historyBackB.setFocusable(false);
		historyBackB.setBorderPainted(false);
		historyBackB.setToolTipText(Local.getString("History back"));
		historyBackB.setRequestFocusEnabled(false);
		historyBackB.setPreferredSize(new Dimension(24, 24));
		historyBackB.setMinimumSize(new Dimension(24, 24));
		historyBackB.setMaximumSize(new Dimension(24, 24));
		historyBackB.setText("");

		historyForwardB.setAction(History.historyForwardAction);
		historyForwardB.setBorderPainted(false);
		historyForwardB.setFocusable(false);
		historyForwardB.setPreferredSize(new Dimension(24, 24));
		historyForwardB.setRequestFocusEnabled(false);
		historyForwardB.setToolTipText(Local.getString("History forward"));
		historyForwardB.setMinimumSize(new Dimension(24, 24));
		historyForwardB.setMaximumSize(new Dimension(24, 24));
		historyForwardB.setText("");

		newProcessB
				.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_new.png")));
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

		ppShowActiveOnlyChB.setFont(new java.awt.Font("Dialog", 1, 11));
		ppShowActiveOnlyChB.setText(Local.getString("Show Active only"));
		
		boolean isShao = (Context.get("SHOW_ACTIVE_TASKS_ONLY") != null)
				&& (Context.get("SHOW_ACTIVE_TASKS_ONLY").equals("true"));
		ppShowActiveOnlyChB.setSelected(isShao);
		
		this.setLayout(borderLayout1);
		scrollPane.getViewport().setBackground(Color.white);
		
		this.add(scrollPane, BorderLayout.CENTER);
		processToolBar.add(historyBackB, null);
		processToolBar.add(historyForwardB, null);
		processToolBar.addSeparator(new Dimension(8, 24));

		processToolBar.add(newProcessB, null);
		processToolBar.addSeparator(new Dimension(8, 24));

		this.add(processToolBar, BorderLayout.NORTH);
		
		CurrentDate.addDateListener(new DateListener() {
			public void dateChange(CalendarDate d) {
				newProcessB
						.setEnabled(d.inPeriod(CurrentProject.get().getStartDate(), CurrentProject.get().getEndDate()));
			}
		});
		CurrentProject.addProjectListener(new ProjectListener() {
			public void projectChange(Project p, NoteList nl, TaskList tl, ResourcesList rl, ContactsList cl) {
				newProcessB.setEnabled(CurrentDate.get().inPeriod(p.getStartDate(), p.getEndDate()));
			}

			public void projectWasChanged() {
				// taskTable.setCurrentRootTask(null); //XXX
			}
		});
	}

	void newProcessB_actionPerformed(ActionEvent e) {
		ProcessDialog dlg = new ProcessDialog(App.getFrame(), Local.getString("New Process"));

		Dimension frmSize = App.getFrame().getSize();
		Point loc = App.getFrame().getLocation();
		dlg.startDate.getModel().setValue(CurrentDate.get().getDate());
		dlg.endDate.getModel().setValue(CurrentDate.get().getDate());
		dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x,
				(frmSize.height - dlg.getSize().height) / 2 + loc.y);
		dlg.setVisible(true);
		if (dlg.CANCELLED)
			return;
		CalendarDate sd = new CalendarDate((Date) dlg.startDate.getModel().getValue());
		CalendarDate ed;
		if (dlg.chkEndDate.isSelected())
			ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
		else
			ed = null;
		long effort = Util.getMillisFromHours(dlg.effortField.getText());
		//Process newProcess = CurrentProject.getTaskList().createTask(sd, ed, dlg.todoField.getText(),
		//				dlg.priorityCB.getSelectedIndex(), effort, dlg.descriptionField.getText(), null);
		//newTask.setProgress(((Integer) dlg.progress.getValue()).intValue());
		CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
		parentPanel.updateIndicators();
	}
	
	void ppNewTask_actionPerformed(ActionEvent e) {
		newProcessB_actionPerformed(e);
	}

	

}