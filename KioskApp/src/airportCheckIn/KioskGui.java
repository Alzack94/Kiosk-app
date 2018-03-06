package airportCheckIn;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;


public class KioskGui extends JApplet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SSHAirportCollection acol;
	SSHAirportManager cin = new SSHAirportManager();
	JTextField lastName = new JTextField(18);
	JTextField bookingRef = new JTextField(10);
	JTextField bagWeight = new JTextField(3);
	JTextField bagLength = new JTextField(3);
	JTextField bagWidth = new JTextField(3);
	JTextField bagHeight = new JTextField(3);
	JTextField exceedBag = new JTextField(3);
	JTextField amount = new JTextField(10);
	JLabel bk = new JLabel();
	JLabel bc = new JLabel();
	JTextArea aboutYou = new JTextArea(1,12);
	JTextArea aboutYou1 = new JTextArea(4,20);
	
	
	JButton proceed = new JButton("Check in");
	JButton reset = new JButton("Reset");
	JPanel[] p = new JPanel[10];
	JPanel[] a = new JPanel[10];
	Font font1 = new Font("SansSerif", Font.BOLD, 20);
	
	public KioskGui() {
		// TODO Auto-generated method stub
		 
		setBackground(Color.YELLOW); 
		cin.run();
		acol = cin.getCollection();
		
		
		lastName.setCaretColor(Color.WHITE); 
		lastName.setFont(font1);
		lastName.setBackground(Color.DARK_GRAY);
		lastName.setForeground(Color.white);
		lastName.setBorder(BorderFactory.createLineBorder(Color.decode("#d4ffcf")));
		bookingRef.setCaretColor(Color.WHITE); 
		bookingRef.setFont(font1);
		bookingRef.setBackground(Color.DARK_GRAY);
		bookingRef.setForeground(Color.white);
		bookingRef.setBorder(BorderFactory.createLineBorder(Color.decode("#d4ffcf")));
		bagWeight.setCaretColor(Color.WHITE); 
		bagWeight.setFont(font1);
		bagWeight.setBackground(Color.DARK_GRAY);
		bagWeight.setForeground(Color.white);
		bagWeight.setBorder(BorderFactory.createLineBorder(Color.decode("#d4ffcf")));
		bagLength.setCaretColor(Color.WHITE); 
		bagLength.setFont(font1);
		bagLength.setBackground(Color.DARK_GRAY);
		bagLength.setForeground(Color.white);
		bagLength.setBorder(BorderFactory.createLineBorder(Color.decode("#d4ffcf")));
		bagWidth.setCaretColor(Color.WHITE); 
		bagWidth.setFont(font1);
		bagWidth.setBackground(Color.DARK_GRAY);
		bagWidth.setForeground(Color.white);
		bagWidth.setBorder(BorderFactory.createLineBorder(Color.decode("#d4ffcf")));
		bagHeight.setCaretColor(Color.WHITE); 
		bagHeight.setFont(font1);
		bagHeight.setBackground(Color.DARK_GRAY);
		bagHeight.setForeground(Color.white);
		bagHeight.setBorder(BorderFactory.createLineBorder(Color.decode("#d4ffcf")));
		exceedBag.setCaretColor(Color.WHITE); 
		exceedBag.setFont(font1);
		exceedBag.setBackground(Color.DARK_GRAY);
		exceedBag.setForeground(Color.white);
		exceedBag.setBorder(BorderFactory.createLineBorder(Color.decode("#d4ffcf")));
		amount.setCaretColor(Color.WHITE); 
		amount.setFont(font1);
		amount.setBackground(Color.DARK_GRAY);
		amount.setForeground(Color.white);
		amount.setBorder(BorderFactory.createLineBorder(Color.decode("#d4ffcf")));
		
		aboutYou.setCaretColor(Color.WHITE); 
		aboutYou.setFont(new Font("SansSerif", Font.BOLD, 15));
		aboutYou.setBackground(Color.DARK_GRAY);
		aboutYou.setForeground(Color.white);
		aboutYou.setBorder(BorderFactory.createLineBorder(Color.decode("#d4ffcf")));
		aboutYou.setEditable(false);
		
		aboutYou1.setCaretColor(Color.WHITE); 
		aboutYou1.setFont(new Font("SansSerif", Font.BOLD, 12));
		aboutYou1.setBackground(Color.DARK_GRAY);
		aboutYou1.setForeground(Color.white);
		aboutYou1.setBorder(BorderFactory.createLineBorder(Color.decode("#d4ffcf")));
		//aboutYou1.setLineWrap(true);
		aboutYou1.setEditable(false);
		
		getContentPane().setLayout(new GridLayout(10, 1));
		//setBackground(Color.black);
		//getContentPane().setBackground(Color.YELLOW); //Color JPanel
		getContentPane().setForeground(Color.green);
		proceed.addActionListener(new checkinListener());
		reset.addActionListener(new resetListener());
		for (int i=0; i<10; i++) 
		{
			 p[i] = new JPanel();
			 a[i] = new JPanel();
			 if ( i != 0 ) p[i].setLayout(new FlowLayout(FlowLayout.LEFT));
			 if ( i == 0 ) p[i].setLayout(new FlowLayout(FlowLayout.RIGHT));
			 if ( i != 0 ) a[i].setLayout(new FlowLayout(FlowLayout.CENTER));
			 getContentPane().add(p[i]).setBackground(Color.DARK_GRAY);
			 getContentPane().add(a[i]).setBackground(Color.DARK_GRAY);
			 
		}
		
		p[0].add(new JLabel("Kiosk App")).setForeground(Color.white);
		p[1].add(new JLabel("Last name ")).setForeground(Color.white);
		a[1].add(lastName);
		a[1].add(bk);
		p[2].add(new JLabel("Booking Reference ")).setForeground(Color.white);
		a[2].add(bookingRef);
		a[2].add(bc);
		p[3].add(new JLabel("Luggage weight ")).setForeground(Color.white);
		a[3].add(bagWeight); 
		p[4].add(new JLabel("Baggage volume ")).setForeground(Color.white);
		a[4].add(bagLength);
		a[4].add(new JLabel("X")).setForeground(Color.white);
		a[4].add(bagWidth);
		a[4].add(new JLabel("X")).setForeground(Color.white);
		a[4].add(bagHeight);
		p[5].add(new JLabel("Exceed weight ")).setForeground(Color.white);
		a[5].add(exceedBag);
		p[6].add(new JLabel("Amount ")).setForeground(Color.white);
		a[6].add(amount);
		p[7].add(aboutYou);
		p[8].add(aboutYou1);
		a[7].add(proceed);
		a[8].add(reset);
		FocusListener highlighter = new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                e.getComponent().setBackground(Color.black);
            }

            @Override
            public void focusLost(FocusEvent e) {
                e.getComponent().setBackground(Color.DARK_GRAY);   
            }
            
        };
        lastName.addFocusListener(highlighter);
        bookingRef.addFocusListener(highlighter);
        bagWeight.addFocusListener(highlighter);
        bagLength.addFocusListener(highlighter);
        bagWidth.addFocusListener(highlighter);
        bagWidth.addFocusListener(highlighter);
        bagHeight.addFocusListener(highlighter);
        exceedBag.addFocusListener(highlighter);
        amount.addFocusListener(highlighter);
	}
	
	public class checkinListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e)		 
		 {
			
			 if( e.getSource() == proceed ) 
			 {
				
				 String c = bookingRef.getText();
				 int l = Integer.parseInt(bagLength.getText());
				 int h = Integer.parseInt(bagHeight.getText());
				 int b = Integer.parseInt(bagWidth.getText());
				 double w =Double.parseDouble(bagWeight.getText());
				 acol.CheckInNow(c,l,h,b,w);
				 aboutYou.setText(acol.validBookingRefFromFile(c));
				 aboutYou1.setText(acol.findCheckInDetailsByBookRef(bookingRef.getText()));
				 //exceedBag.setText(acol.Exceedweight(bookingRef.getText()));
				 if(acol.validBookingRefFromFile(c)=="Booking Reference is Invalid")
				 {
				 //bc.setText(acol.CheckBookingReference(c));
				 //bc.setForeground(Color.decode("#fdad9e"));
				 bookingRef.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#fdad9e")));
				 }
				 else
				 {
					 bookingRef.setBorder(BorderFactory.createLineBorder(Color.decode("#d4ffcf")));
				 }
				 if(acol.findCheckInDetailsByBookRef(bookingRef.getText())=="Passenger Not Found")
				 {
				 //bk.setText(acol.Details(bookingRef.getText()));
				 //bk.setForeground(Color.decode("#fdad9e"));
				 lastName.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#fdad9e")));
				 }
				 else
				 {
					 lastName.setBorder(BorderFactory.createLineBorder(Color.decode("#d4ffcf")));
				 }
				 cin.generateReport();
				 //getCC(c,l,h,b,w);
				
			 }
		 }
			
		 
	 	}
public class resetListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e)		 
		 {
			
			 if( e.getSource() == reset ) 
			 {
				
				 lastName.setText(null);
				 bookingRef.setText(null);
				 bagLength.setText(null);
				 bagHeight.setText(null);
				 bagWidth.setText(null);
				 bagWeight.setText(null);
				 exceedBag.setText(null);
				 amount.setText(null);
				 aboutYou.setText(null);
				 aboutYou1.setText(null);
				 
				
			 }
		 }
			
		 
	 	}
//	public void getCC(String c,int l,int h,int b,double w)
//	{
//		 AirlinesCollection cin = new AirlinesCollection();
//		cin.CheckInNow(c,l,h,b,w);
//		System.out.println(c);
//		System.out.println(w);
//	}

}
