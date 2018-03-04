package airportCheckIn;

import java.awt.*;
import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import javax.swing.event.*;

/**
 * This Class is used to set up the JFrame for the CheckIn and connect to the Collections used.
 * 
 * @author Sreepratha Ramasubramanian
 * @author Suraj Sivaprasad
 * @author Hari Nikesh Suresh
 */

public class SSHAirportGUI extends JFrame 
{
	private static final long serialVersionUID = 1L;
	SSHAirportCollection ac;

	public  SSHAirportGUI(SSHAirportCollection c)
	{
		ac=c;
		//System.out.println(ac);
		createFrame();
	}

	public void createFrame() 
	{
		// Create the frame, position it and handle closing it
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Check-In Kiosk");
		//create Grid Bag Layout to the frame- set constraints
		GridBagLayout gridBagLayout = new GridBagLayout();
		this.getContentPane().setLayout(gridBagLayout);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		this.getContentPane().add(tabbedPane, gbc_tabbedPane);

		// individual tab objects created for individual accommodation types- Parameters passed
		JPanel Panel1 = (JPanel) new SSHAirportGUITabs(ac);
		Panel1.setVisible(true);
		tabbedPane.addTab("Check-In", null, Panel1, null);

		//tab group added to frame
		this.add(tabbedPane);

		// Adjusts the size of the frame to best work for the components

		this.pack();

		this.setVisible(true);
		// Resizing windows disabled
		this.setResizable(false);
	}
}