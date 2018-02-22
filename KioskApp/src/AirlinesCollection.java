
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.LinkedHashSet;
import java.util.*;  

/**
 * The AirlinesCollection contains an LinkedHashSet of objects of checkIn class and TreeSet of objects of Flight class 
 * @author Sreepratha
 *
 */
public class AirlinesCollection {
		
	private LinkedHashSet<checkIn> chks;
	private TreeSet<Flight> flts;  
	
	
    /**Constructor with no arguments
     * 
     */
	public AirlinesCollection() {
		chks = new LinkedHashSet<checkIn>();
		flts=new TreeSet<Flight>();
		
		
	}
	
	/**
	 * Adds details of Passengers in a flight to the LinkedHasshSet
	 * By reading from an input file
	 * Calls a method to read from file,which in turn calls a method to create objects
	 * @return Nothing
	 * @param Nothing
	 */
	
	public void populateCheckIns() {

		readFile1("C:\\Users\\C Ram\\eclipse-workspace\\Project1\\PaxBookingFile1.csv");
		System.out.println("Done 1");
		
		}
	/**
	 * Adds details of Flights to a TreeSet
	 * By reading from an input file
	 * Calls a method to read from file,which in turn calls a method to create objects
	 * @return Nothing
	 * @param Nothing
	 */
	public void populateFlights() {

		readFile2("C:\\Users\\C Ram\\eclipse-workspace\\Project1\\FlightFile1.csv");
		System.out.println("Done 1");
		
		}


	
	/**returns a report line by line
	 * 
	 * @return String Returns a report of all the check-ins
	 */
	
	
	public String getTableOfCheckIns()
	{		
		String report = "NAME                                                BOOKING      FLIGHT  EXCESS FEE  CHECK-IN STATUS  \n";
		for (checkIn s  : chks){
			report += String.format("%-50s", s.getPassenger().getPaxName().getLastName());
			report += String.format("%-15s", s.getPassenger().getBookingRef() );
			report += String.format("%-10s", s.getFlight().getFlightCode());
			report += String.format("%-20f", s.getExcessFee());
			report += String.format("%-6s", s.getCheckedIn());
			report += "\n";
		}
		writeToFile("C:\\Users\\C Ram\\eclipse-workspace\\Project1\\report.txt",report);
		return report;
	}
	
	//adding an object to LinkedHashSet
	
	public void addcheckIn(checkIn c) {
		chks.add(c);
		
	}
//adding an object to TreeSet
	public void addFlight(Flight c) {
		flts.add(c);
		
	}
	
	
	
  

	
/**
 * writes report to a file
 * @param filename 
 * @param contents of file
 * @return void
 * @exception IOException
 * @exception FileNotFoundException
 * 
 */
public  void writeToFile(String filename, String report) {

	 FileWriter fw;
	 try {
		
	    fw = new FileWriter(filename);
	    System.out.println(fw);
	    fw.write("The report"+ "\n");
	    fw.write("\n"+report);
	    fw.close();

	 }
	 //message and stop if file not found
	 catch (FileNotFoundException fnf){
		 System.out.println(filename + " not found ");
		 System.exit(0);
	 }
	 //stack trace here because we don't expect to come here
	 catch (IOException ioe){
	    ioe.printStackTrace();
	    System.exit(1);
	 }
}

/** reads from a csv file and makes objects of checkIn class
 * populates them with data from the file
 * @param filename 
 * @return void
 * @exception FileNotFoundException
 */
public void readFile1(String filename) {
	try {
		File f = new File(filename);
		Scanner scanner = new Scanner(f);
				while (scanner.hasNextLine()) {
			//read first line and process it
			String inputLine = scanner.nextLine();
			if (inputLine.length() != 0) {//ignored if blank line
				processLine(inputLine);
			}}}
			catch (FileNotFoundException fnf){
				 System.out.println( filename + " is not found ");
				 System.exit(0);
			 }}

/** reads from a csv file and makes objects of Flight class
 * populates them with data from the file
 * @param filename 
 * @return void
 * @exception FileNotFoundException
 */
public void readFile2(String filename) {
					try {
						File f = new File(filename);
						Scanner scanner = new Scanner(f);
				while (scanner.hasNextLine()) {
					//read first line and process it
					String inputLine = scanner.nextLine();
					if (inputLine.length() != 0) {//ignored if blank line
						processLine2(inputLine);
					}}
		

		
	}
	//if the file is not found, stop with system exit
	catch (FileNotFoundException fnf){
		 System.out.println( filename + " is not found ");
		 System.exit(0);
	 }
}

/**
 * Gets data from line,reads it, creates checkIn object
 * @param line the line read in previous readFile()
 * @exception Exception
 */
private void processLine(String line) {
	try 
		//create Room objects and add to the list
		{String parts1 [] = line.split(",");
		String PassNum = parts1[0];
		System.out.println(PassNum);
		Name name = new Name(parts1[1]);
		System.out.println(name.getLastName());
		String Booking=parts1[2];
		System.out.println(Booking);
		String flightcode=parts1[3];
		System.out.println(flightcode);
		Passenger p=new Passenger(PassNum,name,Booking);
		Baggage b= new Baggage();
		String f[]=new String[6];
		f=getFlights(flightcode);
		/*for(int i=0;i<6;i++)
		{
			System.out.println(f[i]);
		}*/
		Flight h=new Flight(flightcode,f[0],Integer.parseInt(f[2]),Double.parseDouble(f[3]),Double.parseDouble(f[4]),f[1]);
		h.flightDetails();
		checkIn d = new checkIn(p,b,h);
		this.addcheckIn(d);
		System.out.println("Check-In added");
	}

	//catches all exceptions 
	catch (Exception e) {System.out.println("Error here also");}}

/**
 * Gets data from line,reads it, creates Flight object
 * @param line the line read in previous readFile()
 * @exception Exception
 */
private void processLine2(String line) {
	try 
		//create Room objects and add to the list
		{String parts1 [] = line.split(",");
		String Code = parts1[0];
		System.out.println(Code);
			String des=parts1[1];
			System.out.println(des);
		String fname=parts1[2];
		System.out.println(fname);
		int maxp=Integer.parseInt(parts1[3]);
		System.out.println(maxp);
		double maxv=Double.parseDouble(parts1[5]);
		System.out.println(maxv);
		double maxw=Double.parseDouble(parts1[4]);
		
		System.out.println(maxw);
		Flight f=new Flight(Code,fname,maxp,maxv,maxw,des);
		this.addFlight(f);
		System.out.println("Flight Added");
		
		
	}

	//catches all exceptions 
	catch (Exception e) {
		System.out.println(" error ");
	}
}


//To get flight details using Flight Code to create objects of checkIn class
public String[] getFlights(String c)
{System.out.println("GetFlights");
	String a[] = new String[6]; 
for (Flight s : flts)
{
if(s.getFlightCode().equals(c))
{System.out.println("Hello");
	a[0]= s.getAirlineName();
	System.out.println("Name"+a[0]);
	a[1]= s.getDestination();
	a[2]=Integer.toString(s.getMaxNumOfPax());
	a[3]=Double.toString(s.getMaxBagWeight());
	a[4]=Double.toString(s.getMaxBagVolume());
	return a;
	}//else {return a;}
}
return a;
}

//Use this for method for Check-In button in GUI 

public void CheckInNow(String c,int l,int h,int b,double w)
{for (checkIn s : chks)
{
	if(c.equals(s.getPassenger().getBookingRef()))
	{
		s.setCheckedIn('Y');
		s.getBaggage().setBagBreadth(b);
		s.getBaggage().setBagLength(l);
		s.getBaggage().setBagHeight(h);
		s.getBaggage().setBagWeight(w);
	}
}
	
}
}