import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.ActionEvent;


import javax.swing.*;

/**
 * 
 * @author Sreepratha
 *
 */





public class tabs extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	AirlinesCollection a;
	JLabel nameLabel, IDLabel, BaggageL, BaggageH, BaggageW,BaggageB,optionsLabel, aboutLabel;
	JTextField nameText, bookingText,BaggageLT, BaggageHT, BaggageWT,BaggageBT,optionsLabelT, aboutLabelT;
	
	
	JTextArea aboutYou,aboutYou1;
	JButton bookbut,searchbut,reset,summary;
	
	
	public tabs(AirlinesCollection b) {
		a=b;
		//gridbag layout assigned 
		this.setLayout(new GridBagLayout());
		//components added in grid layout
		nameLabel = new JLabel("Passenger  Name:");//added to grid (0,0)
		addComp(this, nameLabel, 0, 0, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);
		nameText = new JTextField(30);//added to grid (1,0)
		addComp(this, nameText, 1, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
		IDLabel = new JLabel(" Booking Reference:");//added to grid (0,1)
		addComp(this, IDLabel, 0, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);
		bookingText = new JTextField(5);//added to grid (1,1)
		addComp(this, bookingText, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
		BaggageL = new JLabel(" Baggage Length:");//added to grid (0,1)
		addComp(this, BaggageL, 0,2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
		BaggageLT = new JTextField(5);//added to grid (1,1)
		addComp(this, BaggageLT, 1, 2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
		BaggageH = new JLabel("Baggage Height:");//added to grid (0,1)
		addComp(this,BaggageH, 0, 3, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);
		BaggageHT = new JTextField(5);//added to grid (1,1)
		addComp(this,BaggageHT , 1, 3,1 , 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
		BaggageB = new JLabel(" BaggageWidth:");//added to grid (0,1)
		addComp(this,BaggageB, 0, 4, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);
		BaggageBT = new JTextField(5);//added to grid (1,1)
		addComp(this,BaggageBT, 1, 4, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
		BaggageW = new JLabel(" BaggageWeight:");//added to grid (0,1)
		addComp(this,BaggageW, 0, 5, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);
		BaggageWT = new JTextField(5);//added to grid (1,1)
		addComp(this,BaggageWT, 1, 5, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
		bookbut= new JButton(" Check-In  ");//added to grid (3,0)
		bookbut.addActionListener(this);
		addComp(this, bookbut, 3, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);

		
		
		
		
		aboutYou = new JTextArea(2,10);
		aboutYou1 = new JTextArea(10, 40);
		
		// Set the default text for the text area
		//changed
		aboutYou.setText("Hello");
		aboutYou1.setText("Hello");
		
		// If text doesn't fit on a line, jump to the next
		
		aboutYou.setLineWrap(true);
		aboutYou1.setLineWrap(true);
		
		
		// Makes sure that words stay intact if a line wrap occurs
		
		aboutYou.setWrapStyleWord(true);
		aboutYou1.setWrapStyleWord(true);
		
		// Adds scroll bars to the text area ----------
		
		JScrollPane scrollbar1 = new JScrollPane(aboutYou, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		addComp(this, scrollbar1, 5, 2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
		
JScrollPane scrollbar2 = new JScrollPane(aboutYou1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		addComp(this, scrollbar2, 5,6, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
		
		
		setVisible(true);
		
		
	}
	/**
	 * 
	 * separate method to add components to gridbag layout
	 */
	private void addComp(JPanel thePanel, JComponent comp, int xPos, int yPos, int compWidth, int compHeight, int place, int stretch){
		GridBagConstraints gridConstraints = new GridBagConstraints();
		gridConstraints.gridx = xPos;
		gridConstraints.gridy = yPos;
		gridConstraints.gridwidth = compWidth;
		gridConstraints.gridheight = compHeight;
		gridConstraints.weightx = 100;
		gridConstraints.weighty = 100;
		gridConstraints.insets = new Insets(5,5,5,5);
		gridConstraints.anchor = place;
		gridConstraints.fill = stretch;
		
		thePanel.add(comp, gridConstraints);
	}
	/*
	 * action listeners
	 */
	public void actionPerformed(ActionEvent e) 
    { 
//		System.out.println(e.getSource());
		if (e.getSource()==bookbut) {
			System.out.println("Check-In");
			checkIn();
		};
		
		
				
		
    }
	public void checkIn() {
		String c=bookingText.getText();
		int l=Integer.parseInt(BaggageLT.getText());
		int b=Integer.parseInt(BaggageBT.getText());
		int h=Integer.parseInt(BaggageHT.getText());
		double w=Double.parseDouble(BaggageWT.getText());
		
		a.CheckInNow(c,l,h,b,w);
		aboutYou.setText(a.CheckBookingReference(c));
		
			aboutYou1.setText(a.Details(bookingText.getText()));
	
	}
	
   
		
    	
   
   
//   
}
