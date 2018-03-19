package airportCheckIn;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * CheckInDesk class is used to simulates CheckIns happening at a desk.
 * It implements Runnable to enable multi-threading
 * @author Suraj Sivaprasad
 */
public class CheckInDesk  implements Runnable
{
	private int waitTime;   		//Waiting Time 
	private int deskID;    			//CheckIn Desk ID
	private ArrayList<CheckIn> checkInsAtThisDesk;  //list of checkIns processed at this desk
	private AirportModel airport;
	private String report="";//The Airport Model, where the CheckInDesks are located

	public CheckInDesk(int id, AirportModel a)
	{
		deskID=id;
		checkInsAtThisDesk = new ArrayList<CheckIn>();
		airport=a;

		//If speed is totally variable, we don't seem to get any processing clashes or failures
		//Setting 2 different waiting times will give some clashes as well as some variation
		int random0to100 =(int)(Math.random() * 101);
		waitTime = 3000 + 2000*(random0to100%2);
	}

	//add a CheckIn in the list of this desk to be processed
	public void addCheckIn(CheckIn c) 
	{
		checkInsAtThisDesk.add(c);
	}

	//Returns the deskID of the current desk
	public int getDeskID() 
	{
		return deskID;
	}

	//Returns the report of all CheckIns at this desk
	public String getCidList()
	{
		report = "";
		if (checkInsAtThisDesk.size() == 0) 
		{
			report += "\n No CheckIns have happened at Desk "+deskID+"\n";
		}
		else 
		{
			report += "\n********************CheckIns at Desk "+deskID+"********************\n";
			report = "FULLNAME                    BOOKINGREF    FLIGHTCODE  EXCESSFEES($)  CHECKED-IN\n";
			report+= "-------------------------------------------------------------------------------\n";
			for (CheckIn s  : checkInsAtThisDesk)
			{
				report += String.format("%-28s", s.getPassenger().getPaxName().getFullName());
				report += String.format("%-14s", s.getPassenger().getBookingRef() );
				report += String.format("%-12s", s.getFlight().getFlightCode());
				report += String.format("%-15.2f", s.getExcessFee());
				report += String.format("%-10s", s.getCheckedIn()?"Yes":"No");
				report += "\n";
			}
			report+= "-------------------------------------------------------------------------------\n";
		}
		return report;		
	}

	/**
	 * Implementing run method() under Runnable Interface for Threading
	 * Until the end of the Airport Time, the CheckIns at this desk are processed
	 */
	public void run() 
	{
		//Keep processing until airport time is not finished
		System.out.println("Starting Check-In thread"+deskID);
		int count=0;
		while (count<5) 
		{
		try {count++;
				if (deskID/2 == 0) 
				{  
					//Introduce variation in sleeping pattern, so CheckIns have different times
					Thread.sleep(waitTime);
				}

				CheckIn c=airport.getFrontOfQueue();
				if(c==null)
				{
					System.out.println("CheckIn Desk is inactive because there are no Passengers in Queue");
				}
				else
				{
					String bRef=c.getPassenger().getBookingRef();
					airport.CheckInNowStage2(bRef);
					System.out.println(printDetails(c,bRef));
					
				}

				if (deskID/2 != 0) 
				{
					//set a pause before the bid
					Thread.sleep(waitTime);
				}
			}
			catch (InterruptedException e) 
			{
				System.out.println("\n Check-In Desk "+deskID + "  Interrupted");
			}
			catch (Exception e) 
			{
				System.out.println("\nException in Check-In Desk "+ deskID + e.getStackTrace());
			}
		}System.out.println("Closing"+Integer.toString(deskID));
		FinishReport();
		airport.Finish();
		
	}	
	
	public String printDetails(CheckIn c1,String bRef1)
	{
		
		report="\n: Check-In Desk "+deskID+" \n" +airport.CheckInStatusInUse(c1)+airport.findCheckInDetailsByBookRef(bRef1);
		return report;
	}
	
	public String getReport()
	{
		return report;
	}
	
	public void FinishReport()
	{
		report="Closing"+deskID;
	}
	
}