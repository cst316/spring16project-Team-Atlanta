/**
 * AddContactDialog.java
 * Created on 14 Feb. 2016
 * Package: net.sf.memoranda.ui
 * 
 * Add Contact Dialog JPanel implementation.
 * @author Moretti
 */
package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;

import net.sf.memoranda.util.Local;

public class AddContactDialog extends JDialog {
    JPanel dialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel header = new JLabel();
    ButtonGroup buttonGroup1 = new ButtonGroup();
    JPanel areaPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc;
    JLabel jLabel1 = new JLabel();
    public JTextField nameField = new JTextField();
    JLabel jLabel2 = new JLabel();
    public JTextField emailField = new JTextField();
    JLabel jLabel3 = new JLabel();
    public JTextField phoneField = new JTextField();
    JLabel jLabel4 = new JLabel();
    public JTextArea notesField = new JTextArea();
    Border border = BorderFactory.createLineBorder(Color.GRAY);
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
    JButton okB = new JButton();
    JButton cancelB = new JButton();
    public boolean CANCELLED = true;

    public AddContactDialog(Frame frame, String title) {
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
        dialogTitlePanel.setBackground(Color.WHITE);
        dialogTitlePanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("New contact"));
        header.setIcon(new ImageIcon(net.sf.memoranda.ui.AddContactDialog.class.getResource(
            "resources/icons/contact48.png")));
        dialogTitlePanel.add(header);
        this.getContentPane().add(dialogTitlePanel, BorderLayout.NORTH);
        
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
        
        jLabel1.setText(Local.getString("Name")+": ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.insets = new Insets(5, 20, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(jLabel1, gbc);
        nameField.setMinimumSize(new Dimension(4, 24));
        nameField.setPreferredSize(new Dimension(335, 24));
        nameField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(CaretEvent e) {
                nameField_caretUpdate(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(nameField, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 2; gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 15, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        
        jLabel2.setText(Local.getString("E-Mail")+":  ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.insets = new Insets(5, 20, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel2, gbc);
        emailField.setMinimumSize(new Dimension(4, 24));
        emailField.setPreferredSize(new Dimension(335, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 0, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(emailField, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 2; gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 15, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        
        jLabel3.setText(Local.getString("Phone Number")+":  ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.insets = new Insets(5, 20, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel3, gbc);
        phoneField.setMinimumSize(new Dimension(4, 24));
        phoneField.setPreferredSize(new Dimension(335, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 0, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(phoneField, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 2; gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 15, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        
        jLabel4.setText(Local.getString("Notes")+":  ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.insets = new Insets(5, 20, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel4, gbc);
        notesField.setMinimumSize(new Dimension(4, 24));
        notesField.setPreferredSize(new Dimension(335, 120));
        notesField.setLineWrap(true);
        notesField.setWrapStyleWord(true);
        notesField.setBorder(border);
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 0, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(notesField, gbc);
        
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
    void nameField_caretUpdate(CaretEvent e) {
        checkOkEnabled();
    }
    
	/**
	 * Do not enable the 'OK' button until the text field is not empty.
	 */
    void checkOkEnabled() {        
         okB.setEnabled(
            (nameField.getText().length() > 0)
         );
    }
}