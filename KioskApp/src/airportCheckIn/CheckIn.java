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
	private Passenger passenger;
	private Baggage baggage;
	private Flight flight; 
	private boolean checkedIn;		//This boolean value is true, if the passenger has successfully checkedIn.
	private double excessWeight;	//Excess Baggage Weight
	private double excessVolume;	//Excess Baggage Volume 
	private double excessFee;		//Excess penalty fees

	/**
	 * Constructor for creating a CheckIn Object with the parameter values.
	 * 
	 * @param p		Passenger object
	 * @param b		Baggage object
	 * @param f		Flight object
	 */
	public CheckIn(Passenger p, Baggage b, Flight f)
	{
		passenger=p;
		baggage=b;
		flight=f;
		checkedIn=false;
		excessWeight=0.0;
		excessVolume=0.0;
		excessFee=0.0;
	}

	//The get methods for CheckIn Class
	public Passenger getPassenger()
	{	return passenger;	}
	public Baggage getBaggage()
	{	return baggage;	}
	public Flight getFlight()
	{	return flight;	}
	public boolean getCheckedIn()
	{	return checkedIn;	}
	public double getExcessWeight()
	{	return excessWeight;	}
	public double getExcessVolume()
	{	return excessVolume;	}
	public double getExcessFee()
	{	return excessFee;	}

	//The set methods for CheckIn Class
	public void setPassenger(Passenger p)
	{	passenger=p;	}
	public void setBaggage(Baggage b)
	{	baggage=b;	}
	public void setFlight(Flight f)
	{	flight=f;	}
	public void setCheckedIn(boolean ci)
	{	checkedIn=ci;	}

	//override equals() method of Object class - used for HashSets
	public boolean equals(Object other) 
	{
		if (other instanceof CheckIn) 
		{
			CheckIn otherCheckIn = (CheckIn) other;
			String oBookRef = otherCheckIn.getPassenger().getBookingRef();
			String thisBookRef = passenger.getBookingRef();
			if (oBookRef.equals(thisBookRef)) 
				return true;
		}
		return false;
	}
	
	//override hashCode() method of Object class - used for HashSets
	public int hashCode() 
	{
		return passenger.getBookingRef().hashCode();
	}
	
	/**
	 * @return details of the CheckIn
	 */
	public String checkInDetails()
	{
		String d="";
		d+="Passenger Name : "+passenger.getPaxName().getLastName();
		d+="\nBooking Reference : "+passenger.getBookingRef();
		d+="\nExcess Weight : "+excessWeight;
		d+="\nExcess Volume : "+excessVolume;
		d+="\nExcess Fee : "+ excessFee;
		return d;
	}

	/**
	 * Recalculate Excess Baggage Weight, Volume and fees with updated Bag details.
	 */
	public void recalculateExcessBaggage()
	{	
		//Initial values set to zero
		excessWeight=0.0;
		excessVolume=0.0;
		excessFee=0.0;

		//To calculate Baggage Limits of the Flight
		double bagWeightLimit=(double) flight.getMaxBagWeight()/flight.getMaxNumOfPax();
		double bagVolumeLimit=(double) flight.getMaxBagVolume()/flight.getMaxNumOfPax();		

		if(baggage.getBagWeight()>bagWeightLimit)
		{	
			excessWeight=baggage.getBagWeight()-bagWeightLimit;
			excessFee+=excessWeight*flight.getWeightPenalty();
		}
		if(baggage.getBagVolume()>bagVolumeLimit)
		{	
			excessVolume=baggage.getBagVolume()-bagVolumeLimit;
			excessFee+=excessVolume*flight.getVolumePenalty();
		}
	}

	/**
	 * @return	boolean true, if Booking reference number is valid according to our rules
	 * 			boolean false, if Booking reference number is invalid
	 */
	public boolean validBookingReference()
	{
		String br=passenger.getBookingRef();
		String fc=flight.getFlightCode();
		String pn=passenger.getPassportNum();
		int size=br.length();

		for(int i=0;i<=7;i++)
			if(br.charAt(i)!=pn.charAt(i))
			return false;

		for(int k=8;k<size;k++)
			if(br.charAt(k)!=fc.charAt(k-8))
				return false;

		return true;
	}
}