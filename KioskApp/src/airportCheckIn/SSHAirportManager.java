package airportCheckIn;
/**
 * SSHAirportManager Class is used for managing the Collections used in SSH Airport.
 * It is also used for reading the Input File and managing the check-ins at the airport.
 * @author Sreepratha Ramasubramanian
 * @author Suraj Sivaprasad
 */
public class SSHAirportManager 
{
	private SSHAirportCollection ac;	//List of Collections for SSH Airport

	public SSHAirportManager() 
	{
		ac = new SSHAirportCollection();
	}

	/**
	 * Method to read the Collections from the input file
	 */
	public void run() 
	{
		ac.readFile("FlightFile.csv","Flight");
		System.out.println("\nFlights file done.\n");
		
		ac.readFile("PaxBookingFile.csv","PaxBooking");
		System.out.println("\nCheckIns file done.\n");
	}
	
	/**
	 * Method to print a report of all the check-ins (only after all check-ins have been completed)
	 */
	public void generateReport()
	{
		//if(ac.AllCheckInsFinished()) 
		//{
		
			String report = "\n********************** REPORT OF SREE-SURAJ-HARI AIRPORT **********************\n";
			report += ac.getTableOfCheckIns();
			System.out.println(report);
			ac.writeToFile("Report.txt",report);
			
		//}
		//else
		//System.out.println("\nSorry! Cannot produce report until all check-ins are completed.\n");
	}

	public SSHAirportCollection getCollection()
	{
		return ac;
	}
}
