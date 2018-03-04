package airportCheckIn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.LinkedHashSet;
import java.util.*;  

/**
 * The SSHAirportCollection is used to manage the collections used in SSH Airport
 * It contains an LinkedHashSet of CheckIn objects and a TreeSet of Flight objects 
 * @author Sreepratha Ramasubramanian
 * @author Suraj Sivaprasad
 */
public class SSHAirportCollection 
{

	private LinkedHashSet<CheckIn> chks;	//LinkedHashSet of CheckIn objects
	private TreeSet<Flight> flts;  			//TreeSet of CheckIn objects

	/**
	 * Constructor for initialising the Collections used.
	 * No parameters are passed, hence the Collections are empty (doesn't have any content)
	 */
	public SSHAirportCollection() 
	{
		chks = new LinkedHashSet<CheckIn>();
		flts=new TreeSet<Flight>();
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
			Baggage b = new Baggage(0.0,0.0,0.0,0.0);	//Baggage details will be later entered through GUI
			Flight f = findByFlightCode(flightCode);
		
			CheckIn d = new CheckIn(p, b, f);
			this.addcheckIn(d);
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
	 * This method is used to complete a new check-in through GUI, after all details are entered at the Check-In App.
	 * @param bRef 	Booking Reference entered in the GUI
	 * @param l		Bag Length
	 * @param h		Bag Breadth
	 * @param b		Bag Height
	 * @param w		Bag Weight
	 */
	public void CheckInNow(String bRef, int l, int h, int b, double w)
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
}