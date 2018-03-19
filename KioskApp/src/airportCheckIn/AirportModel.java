package airportCheckIn;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Airport Model Class implements Runnable for enabling threading 
 * This is a Model Class under MVC Pattern
 * This is also a Subject/Observable Class under Observer Pattern
 * @author Suraj Sivaprasad
 * @author Sreepratha Ramasubramanian
 */
public class AirportModel extends Observable implements  Runnable
{
	
	private LinkedHashSet<CheckIn> chks;	//LinkedHashSet of all CheckIn objects
	private TreeSet<Flight> flts;  			//TreeSet of Flight objects
	private Queue<CheckIn> q ;				//Rolling Queue of Passengers as they arrive into Airport
	private boolean finished = false; 		//Boolean finished is true when time is up, check-in desks close & flights depart
	private CheckInDeskList cidList;		//The list of CheckIn Desks 
	private Thread [] cidThreads;			//Threads for handling CheckIn Desks
	private AirportGUIView view;			//The GUI class - View under MVC pattern
	private static int num=2;
	/**
	 * Constructor to creates list of customers
	 */
	public AirportModel() 
	{
		chks = new LinkedHashSet<CheckIn>();
		flts = new TreeSet<Flight>();
		q = new LinkedList<CheckIn>();
		cidList = new CheckInDeskList();
		
		readFile("FlightFile.csv","Flight");
		System.out.println("\nFlights file done.\n");
		
		readFile("PaxBookingFile.csv","PaxBooking");
		System.out.println("\nCheckIns file done.\n");
		
		for (int i=1; i <=2; i++)	//To add in CheckIn Desk Objects in List
		{System.out.println("Starting Queue");
			CheckInDesk c = new CheckInDesk (i, this);
			cidList.add(c);
		}
	}
	
	/**
	 * Method to print a report of all the check-ins (only after all check-ins have been completed)
	 */
	public void generateReport()
	{
		//if(ac.AllCheckInsFinished()) 
		//{
		
			String report = "\n********************** REPORT OF SREE-SURAJ-HARI AIRPORT **********************\n";
			report += getTableOfCheckIns();
			System.out.println(report);
			writeToFile("Report.txt",report);
			
		//}
		//else
		//System.out.println("\nSorry! Cannot produce report until all check-ins are completed.\n");
	}

	/**
	 * @return Boolean true if Airport CheckIn time is finished, else Boolean false is returned
	 */
	public boolean isFinished()
	{
		return finished;
	}

	/**
	 * to set Airport CheckIn time as finished
	 */
	public void setFinished() 
	{
		finished = true;
	}
	
	//returns List Of Check-In Desks
	public CheckInDeskList getListOfCheckInDesks() {
		return cidList;
	}
	
	public Queue<CheckIn> getQueue() 
	{
		return q;
	}
	
	/**
	 * @return String having a table of all check-ins
	 */
	public String getTableOfCheckIns()
	{		
		String report = "FULLNAME                    BOOKINGREF    FLIGHTCODE  EXCESSFEES($)  CHECKED-IN\n";
		report+= "-------------------------------------------------------------------------------\n";
		for (CheckIn s  : chks)
		{
			report += String.format("%-28s", s.getPassenger().getPaxName().getFullName());
			report += String.format("%-14s", s.getPassenger().getBookingRef() );
			report += String.format("%-12s", s.getFlight().getFlightCode());
			report += String.format("%-15.2f", s.getExcessFee());
			report += String.format("%-10s", s.getCheckedIn()?"Yes":"No");
			report += "\n";
		}
		report+= "-------------------------------------------------------------------------------\n";
		return report;
	}
	

	/**
	 * Method to add an object to the LinkedHashSet of CheckIns
	 * @param c CheckIn object to be added to the LinkedHashSet
	 */
	public void addcheckIn(CheckIn c) 
	{
		chks.add(c);
	}

	/**
	 * Method to add an object to the TreeSet of Flights
	 * @param f	Flight object to be added to the TreeSet
	 */
	public void addFlight(Flight f) 
	{
		flts.add(f);
	}

	/**
	 * A method to write the supplied text to the file in the supplied filename.
	 * It can handle File-Not-Found and IO Exceptions.
	 * @param filename The name of the file to be written to
	 * @param report The text to be written to the file
	 * @exception IOException
	 * @exception FileNotFoundException
	 */
	public void writeToFile(String filename, String report) 
	{
		FileWriter fw;
		try
		{
			fw = new FileWriter(filename);
			fw.write(report);
			fw.close();
		}
		catch (FileNotFoundException fnf)	//Print message and normal exit, if file is not found
		{
			System.out.println("The file at "+ filename + " was not found!");
			System.exit(0);
		}
		catch (IOException ioe)		//to print IO Exception Stack Trace
		{
			ioe.printStackTrace();
			System.exit(1);
		}
	}


	/**
	 * This method reads the input file at the filename parameter. It scans the file line-by-line and can ignore blank lines.
	 * It then passes the scanned line to the appropriate processing method.
	 * @param filename 	The filename of the input file to add objects to the collection
	 * @param type		The type of file to be read - Flight OR PaxBooking 
	 * @exception 		FileNotFoundException
	 */
	public void readFile(String filename, String type) 
	{
		Scanner scanner;
		String inputLine;
		try 
		{
			File f = new File(filename);
			scanner = new Scanner(f);
			if(scanner.hasNextLine())			//Scanning the first line of the input file
			{
				inputLine = scanner.nextLine();	//Skip processing, since the 1st line only contains explanatory Column headers
			}
			while (scanner.hasNextLine()) 
			{
				inputLine = scanner.nextLine();	//Scanning the next line
				if (inputLine.length() != 0) 	//Blank lines can be ignored
				{
					if(type.equals("Flight"))
					{ processLineFlight(inputLine);}	
					if(type.equals("PaxBooking"))
					{ processLinePaxBooking(inputLine);}		
				}
			}
		}
		catch (FileNotFoundException fnf)	//Print message and normal exit, if file is not found
		{
			System.out.println("The file at "+ filename + " was not found!");
			System.exit(0);
		}
	}

	/**
	 * Gets data from String parameter, processes it and creates a Flight object
	 * @param line read and passed from the file
	 */
	private void processLineFlight(String line) 
	{
		/* For understanding purposes: A look at how the columns are split into a String parts[] array.
		 * The Descriptive Column name for Flight details is provided,
		 * along with a sample line of Flight information in the proper format.
		 * ----------------------------------------------------------------------------------------------------
		 *	0			1						2					3			4				5
		 *	FlightCode	DestinationAirport		Carrier				MaxNumOfPax	MaxBagWeightKG	MaxBagVolumeCM3
		 *	AA956		LondonHeathrow Airport	American Airlines	99			4554			5889015
		 */
		
		try 
		{
			String parts1 [] = line.split(",");
			
			String code = parts1[0];
			String dest=parts1[1];
			String airlineName=parts1[2];
			int maxP=Integer.parseInt(parts1[3].trim());
			double maxW=Double.parseDouble(parts1[4].trim());
			double maxV=Double.parseDouble(parts1[5].trim());
			
			Flight f = new Flight(code, airlineName, maxP, maxV, maxW, dest);
			this.addFlight(f);
		}
		/*If these two formatting errors are caught, the line with error is ignored,
		 *an appropriate error message is displayed,
		 *and the program tries to carry on and process the next line of the input file.
		 *For catching String to an integer format conversion errors.
		 *For example, trying to convert non-numeric text into integers.
		 */
		catch (NumberFormatException nfe)	
		{
			String error = "There is a Number Format conversion error in the following line of Input file :\n'" + line +"'";
			error += "\nError Generated because of Invalid Number Format : " + nfe.getMessage();
			error +="\n-----------------------------------------------------------------------\n\n";
			System.out.println(error);
		}

		/*For catching error, if there are a few missing elements.
		 *Note: This is not a fool-proof method. Other complex errors types are not covered...
		 */
		catch (ArrayIndexOutOfBoundsException air)  
		{
			String error = "Insufficient items in the following line of Input file : \n'" + line +"'";
			error +="\nError was found at column position : " + air.getMessage();
			error +="\n-----------------------------------------------------------------------\n\n";
			System.out.println(error);
		}
	}


	/**
	 * Gets data from String parameter, processes it and creates a Passenger, Baggage & CheckIn object
	 * @param line read and passed from the file
	 */
	private void processLinePaxBooking(String line)
	{
		/* For understanding purposes: A look at how the columns are split into a String parts[] array.
		 * The Descriptive Column name for PaxBooking details is provided,
		 * along with a sample line of Passenger Booking information in the proper format.
		 * ------------------------------------------------------------------
		 *	0			1				2				3			4
		 *	PassportNum	PaxName			BookingRefCode	FlightCode	CheckedIn
		 *	K000378		Adam Kinzinger	K000378HV785	HV785		N
		 */
		
		try 
		{
			String parts1 [] = line.split(",");
			
			String passNum = parts1[0];
			Name name = new Name(parts1[1]);
			String bookRef = parts1[2];
			String flightCode = parts1[3];
		
			Passenger p = new Passenger(passNum, name, bookRef);
			Baggage b = new Baggage(0.0,0.0,0.0,0.0);	//Baggage details will be entered later from the Passenger
			Flight f = findByFlightCode(flightCode);
		
			CheckIn d = new CheckIn(p, b, f);
			
			if(d.validBookingReference())	//If the booking reference number is valid according to our rules
			{
				this.addcheckIn(d);
			}
			else
			{
				System.out.println("The Booking Reference "+bookRef+" is invalid according to our rules. Not added!");
			}
			
		}
		/*If these two formatting errors are caught, the line with error is ignored,
		 *an appropriate error message is displayed,
		 *and the program tries to carry on and process the next line of the input file.
		 *For catching String to an integer format conversion errors.
		 *For example, trying to convert non-numeric text into integers.
		 */
		catch (NumberFormatException nfe)	
		{
			String error = "There is a Number Format conversion error in the following line of Input file :\n'" + line +"'";
			error += "\nError Generated because of Invalid Number Format : " + nfe.getMessage();
			error +="\n-----------------------------------------------------------------------\n\n";
			System.out.println(error);
		}

		/*For catching error, if there are a few missing elements.
		 *Note: This is not a fool-proof method. Other complex errors types are not covered...
		 */
		catch (ArrayIndexOutOfBoundsException air)  
		{
			String error = "Insufficient items in the following line of Input file : \n'" + line +"'";
			error +="\nError was found at column position : " + air.getMessage();
			error +="\n-----------------------------------------------------------------------\n\n";
			System.out.println(error);
		}
	}

	
	/**
	 * Searches the TreeSet of Flights and returns the Flight object with the specified flight code.
	 * If no corresponding Flight is found, a null object is returned.
	 * @param code The Flight Code
	 * @return the Flight object (if found), corresponding to the flight code passed, otherwise null.
	 */
	public Flight findByFlightCode(String code)
	{
		for (Flight f : flts)
		{
			if (code.equals(f.getFlightCode()))
			{	return f;	}
		}
		return null;    //No Flight object was found
	}

	
	/**
	 * Function to search for Passenger Booking Reference in the CheckIn Set, then returns their check-in status.
	 * @param bRef	Booking reference of Passenger
	 * @return		boolean true, if the passenger is already check-in
	 * 				boolean false, if the passenger is not checked-in
	 */
	public boolean findCheckInStatusByBookRef(String bRef)
	{
		for (CheckIn c : chks)
		{
			if(bRef.equals(c.getPassenger().getBookingRef()))
				if(c.getCheckedIn()==true)
					return true;
		}
		return false;
	}
	

	/**
	 * Used to check whether the Booking Reference is valid & matches an entry read from the File
	 * @param bRef	Booking reference of Passenger
	 * @return		Whether the Booking Reference is valid & matches an entry from the File
	 */
	public String validBookingRefFromFile(String bRef)
	{ 
		for (CheckIn c : chks)
		{
			if(bRef.equals(c.getPassenger().getBookingRef()))
			{
				return "Booking Reference is Valid";
			}
		}
		return "Booking Reference is Invalid";
	}


	/**
	 * Function to search for Passenger Booking Reference in the CheckIn Set, then return their check-in details.
	 * This returned string returned is used in the GUI display.
	 * @param bRef	Booking reference of Passenger
	 * @return		String having details of the Check-In 
	 */
	public String findCheckInDetailsByBookRef(String bRef) 
	{
		for (CheckIn c : chks)
		{
			if(bRef.equals(c.getPassenger().getBookingRef()))
			return c.checkInDetails();
		}
		return "Passenger Not Found";
	}

	/**
	 * Method to see if all CheckIns are completed in the Manager class 
	 * @return 	boolean true, if all CheckIns are completed.
	 * 			boolean false, if all CheckIns are not over.
	 */
	public boolean AllCheckInsFinished()
	{
		for (CheckIn c : chks)
		{
			if(c.getCheckedIn()!=true)
			return false;
		}
		return true;
	}
	
	/**
	 * This method is used to complete a check-in for Stage 2.
	 * @param bRef 	Booking Reference
	 */
	public void CheckInNowStage2(String bRef)
	{
		for (CheckIn c : chks)
		{
			try
			{
				if(bRef.equals(c.getPassenger().getBookingRef()))
				{
					if(c.getCheckedIn())	//If the passenger has already completed the check-in
					throw new AlreadyCheckedInException(bRef);
				}

				if(bRef.equals(c.getPassenger().getBookingRef()))
				{
					c.setCheckedIn(true);
					c.setInQueue(false);
					c.recalculateExcessBaggage();
				}
			}
			catch(AlreadyCheckedInException e)
			{
				String message = e.getMessage() + "\nDetails not added";
				System.out.println(message);
			}
		}
	}
	
	/**
	 * This method is used to complete a new check-in through GUI in Stage1, after all details are entered at the Check-In App.
	 * @param bRef 	Booking Reference entered in the GUI
	 * @param l		Bag Length
	 * @param h		Bag Breadth
	 * @param b		Bag Height
	 * @param w		Bag Weight
	 */
	public void CheckInNowStage1(String bRef, int l, int h, int b, double w)
	{
		for (CheckIn c : chks)
		{
			try
			{
				if(bRef.equals(c.getPassenger().getBookingRef()))
				{
					if(c.getCheckedIn())	//If the passenger has already completed the check-in
					throw new AlreadyCheckedInException(bRef);
				}

				if(bRef.equals(c.getPassenger().getBookingRef()))
				{
					c.setCheckedIn(true);
					//Set baggage dimensions that were entered through GUI
					c.getBaggage().setBagBreadth(b);
					c.getBaggage().setBagLength(l);
					c.getBaggage().setBagHeight(h);
					c.getBaggage().setBagWeight(w);
					c.recalculateExcessBaggage();
				}
			}
			catch(AlreadyCheckedInException e)
			{
				String message = e.getMessage() + "\nDetails not added";
				System.out.println(message);
			}
		}
	}
	
	//Use for printing list of Passengers in Queue
	public String getQ()
	{ 
		String report="FULLNAME                   BOOKING      FLIGHT   BAGGAGE             \n";;
	
		for (CheckIn c : q) 
		{
			report += String.format("%-25s", c.getPassenger().getPaxName().getFullName());
			report += String.format("%-15s", c.getPassenger().getBookingRef() );
			report += String.format("%-10s", c.getFlight().getFlightCode());
			report += String.format("%-20s", c.getBaggage().getBagLength()+"X"+c.getBaggage().getBagBreadth()+"X"+c.getBaggage().getBagHeight());
			report += "\n";
		}
		return report;
	}

	//Use for component to display flight status
	public String printFlightDetails()
	{String report="";
		
		
      for(Flight f: flts)
		{int max=f.getMaxNumOfPax();
		double total=f.getMaxBagWeight();
		double current=0.0;
		int count=0;
	
    	for(CheckIn c:chks)
		{if(c.getFlight()==f)
			{if(c.getCheckedIn()==true)
			{
				count++;
				current+=c.getBaggage().getBagWeight();
			}
			else {}
		}}
		double perc=(current/total)*100;
		report += String.format("%-25s", f.getFlightCode());
		report += String.format("%-15s", f.getAirlineName() );
		report += String.format("%-10s", f.getDestination());
		report += String.format("%-10s", max);
		report += String.format("%-10s", perc);
		report+="\n";
		//return Integer.toString(count)+" checked in of "+Integer.toString((int)max)+"\n"+"Hold is "+perc+"% full ";
	
		}return report;}


	//Use for component to display status of check-In class - Desk1,Desk2
	public String CheckInStatusInUse(CheckIn c)
	{
		return c.getPassenger().getPaxName().getLastName()+" is dropping of 1 bag of weight "+Double.toString(c.getBaggage().getBagWeight())+".\n"+"A baggage fee of AED"+Double.toString(c.getExcessFee())+"/- is due";
	}
	
	
	/**
	 * Run method() for the thread
	 * Starts off each cThread for each CheckIn Desk
	 * then sleeps until auction is over & finally sets variable to show auction is over
	 */
	public void run() 
	{
		System.out.println("Starting Queue Thread");
		int count=100;

		cidThreads = new Thread[3];
		for (int i = 0; i < 2; i++)
		{System.out.println("Starting Queue thread"+Integer.toString(i));
			cidThreads[i] = new Thread(cidList.get(i));
			cidThreads[i].start();
		}

		while(count>=0)
		{
		/*	try 
			{*/
				//Thread.sleep(3000);	//Sleep for 3 secs
				CheckIn c = addOneToQueue();
				q.add(c);
				if(q.size()==20)
				{num=3;
				System.out.println("Adding new desk");
				CheckInDesk d = new CheckInDesk (3, this);
				cidList.add(d);
				cidThreads[2] = new Thread(d);
				cidThreads[2].start();
				System.out.println("New Desk Added");}
			//	}
			//}
		/*	catch (Exception e) 
			{
				System.out.println("Auction thread exception" + e.getStackTrace());
			}*/
			count--;
		}System.out.println("Time Up");
				
		System.out.println("Finishing");
		finished = true;
	}
	
	public int randomWithRange(int min, int max)
	{
	   int range = Math.abs(max - min) + 1;     
	   return (int)(Math.random() * range) + (min <= max ? min : max);
	}
	

	public synchronized CheckIn getFrontOfQueue()
	{
		if(q.isEmpty())
		{
			
			setChanged();
			notifyObservers();
			clearChanged();
			return null;
		}
		else
		{
			CheckIn c=q.poll();
			
			setChanged();
			notifyObservers();
			clearChanged();
			return c;
		}
	
	}
	/**
	 * Method to return one chosen object from the Set of CheckIns to the Queue
	 * Used to simulate entry of passengers into the queue.
	 */
	public synchronized CheckIn addOneToQueue()
	{
		//int size = chks.size();
		//int item = new Random().nextInt(size);
		for(CheckIn c : chks)
		{
		    if (c.getCheckedIn()==false && c.getInQueue() == false)
		    {
		    	c.setInQueue(true);	//Now passenger will be added to queue
		    	
		    	//Simulate Random Baggage details
		    	//Bag Dimensions in Centimeters
		    	double bl= (double) randomWithRange(20,100);
		    	double bb= (double) randomWithRange(10,30);
		    	double bh= (double) randomWithRange(15,50);
		    	//Weight in Kilograms
		    	double bw= (double) randomWithRange(15,50);
		    	Baggage b = new Baggage(bl,bb,bh,bw);
		    	c.setBaggage(b);	    	
				
		    	//Report to standard output
				System.out.println("\n"+c.getPassenger().getPaxName().getFullName()+" has been added to the queue");

				//Update GUI View display
				System.out.println("Changing display");
				
				
		        return c;
		    }
		}
		return null;
	}
	
	public void Finish()
	{
		setChanged();
		notifyObservers();
		clearChanged();
	}
	
	public void addDesk()
	{num=3;
		System.out.println("Adding new desk");
		CheckInDesk c = new CheckInDesk (3, this);
		cidList.add(c);
		
		
	}
	
	public int getnum()
	{
		return num;
	}
}
