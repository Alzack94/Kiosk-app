import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class KioskGui extends JApplet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField lastName = new JTextField(18);
	JTextField bookingRef = new JTextField(10);
	JTextField bagWeight = new JTextField(3);
	JTextField bagLength = new JTextField(3);
	JTextField bagWidth = new JTextField(3);
	JTextField bagHeight = new JTextField(3);
	JTextField exceedBag = new JTextField(3);
	JTextField amount = new JTextField(10);
	JButton proceed = new JButton("Check in");
	JPanel[] p = new JPanel[8];
	JPanel[] a = new JPanel[8];
	
	public KioskGui() {
		// TODO Auto-generated method stub
		getContentPane().setLayout(new GridLayout(8, 1));
		proceed.addActionListener(new checkinListener());
		for (int i=0; i<8; i++) 
		{
			 p[i] = new JPanel();
			 a[i] = new JPanel();
			 if ( i != 0 ) p[i].setLayout(new FlowLayout(FlowLayout.LEFT));
			 if ( i == 0 ) p[i].setLayout(new FlowLayout(FlowLayout.RIGHT));
			 if ( i != 0 ) a[i].setLayout(new FlowLayout(FlowLayout.CENTER));
			 getContentPane().add(p[i]);
			 getContentPane().add(a[i]);
		}
		
		p[0].add(new JLabel("Kiosk App"));
		p[1].add(new JLabel("Last name "));
		a[1].add(lastName);
		p[2].add(new JLabel("Booking Reference "));
		a[2].add(bookingRef);
		p[3].add(new JLabel("Luggage weight "));
		a[3].add(bagWeight); 
		p[4].add(new JLabel("Baggage volume "));
		a[4].add(bagLength);
		a[4].add(new JLabel("X"));
		a[4].add(bagWidth);
		a[4].add(new JLabel("X"));
		a[4].add(bagHeight);
		p[5].add(new JLabel("Exceed weight "));
		a[5].add(exceedBag);
		p[6].add(new JLabel("Amount "));
		a[6].add(amount);
		a[7].add(proceed);

	}
	
	public class checkinListener implements ActionListener{
 		
		public void actionPerformed(ActionEvent e)		 
		 {
			 if( e.getSource() == proceed ) 
			 {
				 runCC();
				
			 }
		 }
			
		 
	 	}
	public void runCC()
	{
		AirlineManager amo = new AirlineManager();
	    amo.run();
	}

}
