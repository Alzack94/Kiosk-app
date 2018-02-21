package airportCheckIn;
/**
 * CheckIn class is used to process the passenger check-in. Each passenger is assigned to one class. 
 * According to Stage 1 requirements, each passenger is assumed to have a single piece of baggage.
 * @author Suraj Sivaprasad
 * @author Sreepratha Ramasubramanian
 */

public class CheckIn
{
	//instance variables
	private Passenger pax;
	private Baggage bag;
	private Flight flight;
	private boolean checkedIn;		//This boolean value is true, if the passenger has successfully checked-in.
	private boolean overWeightBag;	//This boolean value is true, if the passenger has Over Weight Baggage.
	private double excessBagWeight;	//Amount of excess Baggage Weight in KG.
	private boolean overSizedBag;	//This boolean value is true, if the passenger has Over Sized Baggage.
	private double excessBagVolume;	//Amount of excess Baggage Volume in cm^3.

	
	/**Constructor for creating a CheckIn Object with the parameter values.
	 * 
	 * @param p		Passenger object
	 * @param b		Baggage	object
	 * @param f		Flight object
	 */
	public CheckIn(Passenger p, Baggage b, Flight f)
	{
		pax=p;
		bag=b;
		flight=f; 
		checkedIn=false;	//Initially, set to false.
		
		//To calculate Baggage Limits of the Flight
		double bagWeightLimit=(double) flight.getMaxBagWeight()/flight.getMaxNumOfPax();
		double bagVolumeLimit=(double) flight.getMaxBagVolume()/flight.getMaxNumOfPax();		
		
		if(bag.getBagWeight()<=bagWeightLimit)
		{
			overWeightBag=false;
			excessBagWeight=0.0;
		}
		else
		{	
			overWeightBag=true;
			excessBagWeight=bag.getBagWeight()-bagWeightLimit;
		}
		
		if(bag.getBagVolume()<=bagVolumeLimit)
		{
			overSizedBag=false;
			excessBagVolume=0.0;
		}
		else
		{	
			overSizedBag=true;
			excessBagVolume=bag.getBagVolume()-bagVolumeLimit;
		}	
	}
	
	//The get methods for CheckIn Class
	public Passenger getPax()
	{	return pax;	}
	public Baggage getBag()
	{	return bag;	}
	public Flight getFlight()
	{	return flight;	}
	public boolean getCheckedIn()
	{	return checkedIn;	}
	public boolean getOverWeightBag()
	{	return overWeightBag;	}
	public double getExcessBagWeight()
	{	return excessBagWeight;	}
	public boolean getOverSizedBag()
	{	return overSizedBag;	}
	public double getExcessBagVolume()
	{	return excessBagVolume;	}
	
	//The set methods for CheckIn Class
	public void setPax(Passenger p)
	{	pax=p;	}
	public void setBag(Baggage b)
	{	bag=b;	}
	public void setFlight(Flight f)
	{	flight=f;	}
	public void setCheckedIn(boolean ci)
	{	checkedIn=ci;	}
	

	/**
	 * @return String listing important details of the CheckIn
	 */
	public String checkInDetails()
	{
		String report="";
		report += String.format("%-24s",pax.getPaxName().getFullName());
		report += String.format("%-16s",pax.getBookingRef());
		report += String.format("%-16.2f",bag.getBagVolume());
		report += String.format("%-15.2f",bag.getBagWeight());
		report += String.format("%-12d",flight.getFlightCode());
		report += String.format("%-10s",getCheckedIn()?"Yes":"No");
		report += "\n";
		return report;
	}
	
	/**
	 * @return	Formatted Column Header for CheckIn details
	 */
	public String getCheckInDetailsHeader()
	{
		String h="PASSENGERNAME           BOOKINGREFERENCE  BAGVOLUME(cm3)  BAGWEIGHT(kg)  FLIGHTCODE  CHECKED-IN\n";
		h+=getCheckInDetailsDashes();
		return h;
	}
	
	/**
	 * @return	Formatted Dashes for CheckIn Details
	 */
	public String getCheckInDetailsDashes()
	{
		String h="-----------------------------------------------------------------------------------------------\n";
		return h;
	}
}
