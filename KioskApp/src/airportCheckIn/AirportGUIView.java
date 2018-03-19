//This View doesn't know about the Controller, 
//except that it provides methods for registering a Controller's listeners. 
//Other organizations are possible
package airportCheckIn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 * This Class is used to set up the GUI.
 * 
 * @author Suraj Sivaprasad
 * @author Sreepratha Ramasubramanian
 * @author Hari Nikesh Suresh
 */
public class AirportGUIView extends JFrame implements ActionListener, Observer
{
	private static final long serialVersionUID = 1L;
	private AirportModel airport;
	private CheckInDeskList cidList;				//List of checkIns Desks
	private int numDesks;

	//GUI Components
	JButton proceed, close;
	JScrollPane scrollList, scrollNorth, scrollQueue, scrollFlight, scrollSouth;
	JTextArea [] checkInDesks;
	JTextArea displayQueue, displayFlight;
	Font myFont;


	/**
	 * The Constructor which creates the full GUI Layout of the frame and it's panels.
	 * @param list The List of CheckIns to be searched
	 */
	public AirportGUIView(AirportModel airport)
	{
		//super((Window)null);	//Calling constructor of JDialog initialised with No owner (NULL)
		//setModal(true);		 	//Blocks input to others so that there will be no concurrency issues

		this.airport=airport;
		airport.addObserver(this);
		cidList=airport.getListOfCheckInDesks();
		numDesks=cidList.getSize();

		myFont=new Font (Font.SANS_SERIF,Font.PLAIN,16);	//myFont will be the main font of the GUI
		//this.setLayout(new BorderLayout(5,5));
		setTitle(" Sree+Suraj+Hari Airport CheckIn GUI");	//Title of Main GUI Window
		setLocation (20,10);								//(20,10) should be near the Top-Left Corner 
		setDefaultCloseOperation(AirportGUIView.DO_NOTHING_ON_CLOSE);	//disable default close action
		setupNorthPanel();
		setupCenterPanel();
		setupSouthPanel();
		pack();				//pack contents to fit
		setVisible(true);	//Set it as Visible

	} 

	/**
	 * Method to set up the Center panel of the GUI, which contains live information about CheckIn Desks
	 */
	private void setupCenterPanel()
	{

		JPanel cidPanel = new JPanel(new GridLayout (1,2));
		checkInDesks = new JTextArea [numDesks];
		for (int i = 0; i < numDesks; i++) 
		{
			checkInDesks[i]= new JTextArea(20,40);
			checkInDesks[i].setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
			checkInDesks[i].setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.LIGHT_GRAY));
			checkInDesks[i].setEditable(false);
			cidPanel.add(checkInDesks[i]);
		}
		scrollList = new JScrollPane(cidPanel);	//The display area can be scrolled.
		scrollList.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));	//To create a small border 
		this.add(scrollList,BorderLayout.CENTER);
	}

	/**
	 * Method to set up the North Panel of the GUI, which contains the queue information
	 * The firstPanel has the  a "Close" button.
	 * The 2nd titlePanel contains a title.
	 */
	private void setupNorthPanel() 
	{

		//The firstPanel has one buttons: a "Close" button.
		JPanel firstPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		close = new JButton("Close & Write Log File");
		close.setFont(new Font (Font.SANS_SERIF,Font.BOLD,16));
		close.setBackground(Color.RED);
		close.setForeground(Color.WHITE);
		close.addActionListener(this);
		firstPanel.add(close);

		//The titlePanel is used to display the title and a button to proceed simulation
		JPanel titlePanel = new JPanel();		
		JLabel titleLabel = new JLabel("Sree&Suraj&Hari - Airport CheckIn");
		titleLabel.setFont(new Font (Font.SANS_SERIF,Font.BOLD,20));
		titlePanel.add(titleLabel);
		proceed = new JButton("Proceed with Simulation");
		proceed.setFont(myFont);
		proceed.setBackground(Color.GREEN);
		proceed.setForeground(Color.BLACK);
		titlePanel.add(proceed);

		//The queuePanel is used to display the queue information
		JPanel queuePanel = new JPanel();	
		displayQueue = new JTextArea(5,80);		//10 row cells and 80 column cells
		displayQueue.setFont(new Font (Font.MONOSPACED,Font.PLAIN,14));		//Monospaced font for good formatting
		displayQueue.setEditable(false);
		scrollQueue = new JScrollPane(displayQueue);	//The display area can be scrolled.
		scrollQueue.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));	//To create a small border 
		queuePanel.add(scrollQueue);

		//Set up the northPanel which contains the previous two panels
		JPanel topPanel = new JPanel(new GridLayout(3,1));
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(2,1));
		topPanel.add(firstPanel);
		topPanel.add(titlePanel);
		JLabel queueLabel = new JLabel("   Queue Of Passengers waiting");
		queueLabel.setFont(new Font (Font.SANS_SERIF,Font.BOLD,16));
		topPanel.add(queueLabel);
		northPanel.add(topPanel);
		northPanel.add(queuePanel);
		scrollNorth = new JScrollPane(northPanel);
		scrollNorth.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));	//To create a small border 
		this.add(scrollNorth,BorderLayout.NORTH);	//add northPanel to the North position of this Frame

	}

	/**
	 * Method to set up the SouthPanel consisting of Flight Information
	 */
	private void setupSouthPanel() 
	{ 

		//The flightPanel is used to display the queue information
		JPanel flightPanel  = new JPanel();	
		displayFlight = new JTextArea(10,80);		//10 row cells and 80 column cells
		displayFlight.setFont(new Font (Font.MONOSPACED,Font.PLAIN,14));		//Monospaced font for good formatting
		displayFlight.setEditable(false);
		scrollFlight = new JScrollPane(displayFlight);	//The display area can be scrolled.
		scrollFlight.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));	//To create a small border 
		flightPanel.add(scrollFlight);

		scrollSouth = new JScrollPane(flightPanel);
		scrollSouth.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));	//To create a small border 
		this.add(scrollSouth,BorderLayout.SOUTH);	//add to the South position of this Frame
	
	}


	//MVC pattern - allows listeners to be added
	public void addProceedCIDListener(ActionListener al) 
	{
		proceed.addActionListener(al);
	}

	public void disableProceedButton() 
	{
		proceed.setEnabled(false);
	}

	
	
	/**
	 * Implementation of the abstract actionPerformed Method of the ActionListener Interface
	 * Event Handling is performed here when a button is clicked.
	 * For each type of button, the appropriate action must be done accordingly
	 */ 
	public void actionPerformed(ActionEvent e) 
	{ 

		if (e.getSource() == close) 
		{	
			JLabel closeLabel;
			closeLabel= new JLabel("The Booking Report File can be found after this GUI is closed.");
			closeLabel.setFont(myFont);
			closeLabel.setForeground(Color.RED);
			JOptionPane.showMessageDialog(this,closeLabel,"GoodBye From Suraj-Sree-Hari Airport!",JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
			this.dispose();
			System.exit(0);
		}
	}  

	 
	//OBSERVER pattern - must provide update methods
    //synchronized blocks access to sync methods of the same object until finished
    //possibly investigate SwingWorker
    //for each customer, store bidlist into correct panel
    public synchronized void update(Observable o, Object args) 
    {System.out.println("Calling update");
    displayQueue.setText(airport.getQ());
    displayFlight.setText(airport.printFlightDetails());
    	for (int i = 0; i < numDesks; i++) 
    	{
    		String report = cidList.get(i).getReport() ;
			this.checkInDesks[i].setText(report);	
			if (report.contains("Closing"))
				{checkInDesks[i].setForeground(Color.RED);
			}
				
			
			else
				checkInDesks[i].setForeground(Color.BLACK);
    	}
    }
	
	public CheckInDeskList getUpdatedCheckInList()
	{
		return cidList;
	}

	/**
	 * Method to clear and reset all the GUI Components back to their default values.
	 */
	private void resetCheckIn()
	{

	}
}