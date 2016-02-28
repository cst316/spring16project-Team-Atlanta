/**
 * EditResourcesDescDialog.java
 * Created on 2/28/2016
 * Package: net.sf.memoranda.ui
 * 
 * 
 * @author MMills7
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

public class EditResourcesDescDialog extends JDialog{
	JPanel dialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel header = new JLabel();
    ButtonGroup buttonGroup1 = new ButtonGroup();
    JPanel areaPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc;
    JLabel jLabel1 = new JLabel();
    public JTextField descField = new JTextField();
    Border border = BorderFactory.createLineBorder(Color.GRAY);
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
    JButton okB = new JButton();
    JButton cancelB = new JButton();
    public boolean CANCELLED = true;

    public EditResourcesDescDialog(Frame frame, String title) {
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
        header.setText(Local.getString("New description"));
        
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
        
        jLabel1.setText(Local.getString("Description")+": ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.insets = new Insets(5, 20, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(jLabel1, gbc);
        descField.setMinimumSize(new Dimension(4, 24));
        descField.setPreferredSize(new Dimension(335, 24));
        descField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(CaretEvent e) {
                descField_caretUpdate(e);
            }
        });
        
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(descField, gbc);
        
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
	 * Disable the 'OK' button if descField is empty.
	 * @param e Event trigger that listens for entries in the text field
	 */
    void descField_caretUpdate(CaretEvent e) {
        checkOkEnabled();
    }
    
	/**
	 * Do not enable the 'OK' button until the text field is not empty.
	 */
    void checkOkEnabled() {        
         okB.setEnabled(
            (descField.getText().length() > 0)
         );
    }    
}
