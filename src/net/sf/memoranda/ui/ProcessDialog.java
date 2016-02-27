/**
 * ProcessDialog.java
 * Created on 23 Feb. 2016
 * Package: net.sf.memoranda.ui
 * 
 * Add Process Dialog JPanel implementation.
 * @author Meyung
 */
package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
//import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JCheckBox;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;

@SuppressWarnings("serial")
public class ProcessDialog extends JDialog {
	JPanel processDialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel header = new JLabel();
	JPanel areaPanel = new JPanel(new GridBagLayout());
	GridBagConstraints gbc;
	JLabel lblProcessName   = new JLabel();
	public JTextField txtProcessName = new JTextField();
	JLabel lblScrumMasterName   = new JLabel();
	public JTextField txtScrumMasterName = new JTextField();
	JLabel lblProductOwnerName   = new JLabel();
	public JTextField txtProductOwnerName = new JTextField();
	JLabel lblTeamMemberNames   = new JLabel();
	public JTextField txtTeamMemberNames = new JTextField();
	JLabel lblSprintPlanningMeeting   = new JLabel();
	public JTextField txtSprintPlanningMeeting = new JTextField();
	JLabel lblDailyStandUp   = new JLabel();
	public JTextField txtDailyStandUp = new JTextField();
	JLabel lblSprintReview   = new JLabel();
	public JTextField txtSprintReview = new JTextField();
	JLabel lblSprintRetrospective   = new JLabel();
	public JTextField txtSprintRetrospective = new JTextField();
	JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
	JButton okB = new JButton();
	JButton cancelB = new JButton();
	public boolean CANCELLED = true;

	public ProcessDialog(Frame frame, String title) {
		super(frame, title, true);
		try {
			jbInit();
			pack();
		}
		catch (Exception ex) {
			new ExceptionDialog(ex);
			ex.printStackTrace();
		}
	}

	/**
	 * Setup user interface and init dialog.
	 */
	void jbInit() throws Exception {
		this.setResizable(false);
		processDialogTitlePanel.setBackground(Color.WHITE);
		processDialogTitlePanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		header.setFont(new java.awt.Font("Dialog", 0, 20));
		header.setForeground(new Color(0, 0, 124));
		header.setText(Local.getString("New Process"));
		header.setIcon(new ImageIcon(net.sf.memoranda.ui.AddContactDialog.class.getResource(
				"resources/icons/process48.png")));
		processDialogTitlePanel.add(header);
		this.getContentPane().add(processDialogTitlePanel, BorderLayout.NORTH);

		gbc = new GridBagConstraints();
		gbc.gridwidth = 2;
		gbc.gridx = 0; gbc.gridy = 0;
		gbc.insets = new Insets(10, 15, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc = new GridBagConstraints();
		gbc.gridwidth = 2;
		gbc.gridx = 2; gbc.gridy = 0;
		gbc.insets = new Insets(10, 15, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		lblProcessName.setText(Local.getString("Process Name")+": ");
		gbc.gridx = 0; gbc.gridy = 1;
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 20, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		areaPanel.add(lblProcessName, gbc);
		txtProcessName.setMinimumSize(new Dimension(4, 24));
		txtProcessName.setPreferredSize(new Dimension(335, 24));
		txtProcessName.addCaretListener(new javax.swing.event.CaretListener() {
			public void caretUpdate(CaretEvent e) {
				txtProcessName_caretUpdate(e);
			}
		});
		gbc = new GridBagConstraints();
		gbc.gridx = 1; gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		areaPanel.add(txtProcessName, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 2; gbc.gridy = 1;
		gbc.insets = new Insets(5, 10, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(5, 15, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;

		lblScrumMasterName.setText(Local.getString("Scrum Master Name")+":  ");
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 3;
		gbc.insets = new Insets(5, 20, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;
		areaPanel.add(lblScrumMasterName, gbc);
		txtScrumMasterName.setMinimumSize(new Dimension(4, 24));
		txtScrumMasterName.setPreferredSize(new Dimension(335, 24));
		gbc = new GridBagConstraints();
		gbc.gridx = 1; gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(5, 5, 0, 15);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		areaPanel.add(txtScrumMasterName, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 2; gbc.gridy = 1;
		gbc.insets = new Insets(5, 10, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(5, 15, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;

		lblProductOwnerName.setText(Local.getString("Product Owner Name")+":  ");
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 5;
		gbc.insets = new Insets(5, 20, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;
		areaPanel.add(lblProductOwnerName, gbc);
		txtProductOwnerName.setMinimumSize(new Dimension(4, 24));
		txtProductOwnerName.setPreferredSize(new Dimension(335, 24));
		gbc = new GridBagConstraints();
		gbc.gridx = 1; gbc.gridy = 5;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(5, 5, 0, 15);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		areaPanel.add(txtProductOwnerName, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 2; gbc.gridy = 1;
		gbc.insets = new Insets(5, 10, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(5, 15, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;

		lblTeamMemberNames.setText(Local.getString("Team Member Names")+":  ");
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 7;
		gbc.insets = new Insets(5, 20, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;
		areaPanel.add(lblTeamMemberNames, gbc);
		txtTeamMemberNames.setMinimumSize(new Dimension(4, 24));
		txtTeamMemberNames.setPreferredSize(new Dimension(335, 24));
		gbc = new GridBagConstraints();
		gbc.gridx = 1; gbc.gridy = 7;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(5, 5, 0, 15);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		areaPanel.add(txtTeamMemberNames, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 2; gbc.gridy = 1;
		gbc.insets = new Insets(5, 10, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(5, 15, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;

		lblSprintPlanningMeeting.setText(Local.getString("Sprint Planning Meeting Notes")+":  ");
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 9;
		gbc.insets = new Insets(5, 20, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;
		areaPanel.add(lblSprintPlanningMeeting, gbc);
		txtSprintPlanningMeeting.setMinimumSize(new Dimension(4, 24));
		txtSprintPlanningMeeting.setPreferredSize(new Dimension(335, 24));
		gbc = new GridBagConstraints();
		gbc.gridx = 1; gbc.gridy = 9;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(5, 5, 0, 15);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		areaPanel.add(txtSprintPlanningMeeting, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 2; gbc.gridy = 1;
		gbc.insets = new Insets(5, 10, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(5, 15, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;

		lblDailyStandUp.setText(Local.getString("Daily Stand-Up Notes:")+":  ");
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 11;
		gbc.insets = new Insets(5, 20, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;
		areaPanel.add(lblDailyStandUp, gbc);
		txtDailyStandUp.setMinimumSize(new Dimension(4, 24));
		txtDailyStandUp.setPreferredSize(new Dimension(335, 24));
		gbc = new GridBagConstraints();
		gbc.gridx = 1; gbc.gridy = 11;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(5, 5, 0, 15);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		areaPanel.add(txtDailyStandUp, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 2; gbc.gridy = 1;
		gbc.insets = new Insets(5, 10, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(5, 15, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;

		lblSprintReview.setText(Local.getString("Sprint Review Notes")+":  ");
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 13;
		gbc.insets = new Insets(5, 20, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;
		areaPanel.add(lblSprintReview, gbc);
		txtSprintReview.setMinimumSize(new Dimension(4, 24));
		txtSprintReview.setPreferredSize(new Dimension(335, 24));
		gbc = new GridBagConstraints();
		gbc.gridx = 1; gbc.gridy = 13;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(5, 5, 0, 15);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		areaPanel.add(txtSprintReview, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 2; gbc.gridy = 1;
		gbc.insets = new Insets(5, 10, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(5, 15, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;

		lblSprintRetrospective.setText(Local.getString("Phone Number")+":  ");
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 15;
		gbc.insets = new Insets(5, 20, 5, 15);
		gbc.anchor = GridBagConstraints.WEST;
		areaPanel.add(lblSprintRetrospective, gbc);
		txtSprintRetrospective.setMinimumSize(new Dimension(4, 24));
		txtSprintRetrospective.setPreferredSize(new Dimension(335, 24));
		gbc = new GridBagConstraints();
		gbc.gridx = 1; gbc.gridy = 15;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(5, 5, 0, 15);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		areaPanel.add(txtSprintRetrospective, gbc);    

		this.getContentPane().add(areaPanel, BorderLayout.CENTER);

		okB.setEnabled(false);
		okB.setMaximumSize(new Dimension(100, 26));
		okB.setMinimumSize(new Dimension(100, 26));
		okB.setPreferredSize(new Dimension(100, 26));
		okB.setText(Local.getString("Ok"));
		okB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				okB_actionPerformed(e);
			}
		});   
		this.getRootPane().setDefaultButton(okB);
		cancelB.setMaximumSize(new Dimension(100, 26));
		cancelB.setMinimumSize(new Dimension(100, 26));
		cancelB.setPreferredSize(new Dimension(100, 26));
		cancelB.setText(Local.getString("Cancel"));
		cancelB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelB_actionPerformed(e);
			}
		});
		buttonsPanel.add(okB);
		buttonsPanel.add(cancelB);
		this.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
	}

	/**
	 * Set CANCELLED variable to false so we can know the user 
	 * pressed the 'OK' button and close this dialog.
	 * @param e Action trigger to notify when 'OK' is pressed
	 */
	void okB_actionPerformed(ActionEvent e) {
		CANCELLED = false;
		this.dispose();
	}

	/**
	 * Closes the dialog window.
	 * @param e Action trigger to notify when 'Cancel' is pressed
	 */
	void cancelB_actionPerformed(ActionEvent e) {
		this.dispose();
	}

	/**
	 * Disable the 'OK' button if nameField is empty.
	 * @param e Event trigger that listens for entries in the text field
	 */
	void txtProcessName_caretUpdate(CaretEvent e) {
		checkOkEnabled();
	}

	/**
	 * Do not enable the 'OK' button until the text field is not empty.
	 */
	void checkOkEnabled() {        
		okB.setEnabled(
				(txtProcessName.getText().length() > 0)
				);
	}  
}