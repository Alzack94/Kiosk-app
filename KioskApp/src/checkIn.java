
public class checkIn {
private char checked ;
private Passenger passenger ;
private Baggage baggage ;
private Flight flight; 
private double excessWeight;
private double excessVolume;
private double excessFee;

public checkIn(Passenger b,Baggage c,Flight f)
{
	checked='N';
	passenger=b;
	baggage=c;
	flight=f;
	excessWeight=0;
	excessVolume=0;
	excessFee=0;
	
	
	
}

public Flight getFlight()
{
	return flight;
}

public void setCheckedIn(char a)
{
	checked=a;
}

public void setPassenger( Passenger p)
{
	passenger=p;
}

public void setBaggage(Baggage b)
{
	baggage=b;
}

public char getCheckedIn()
{
	return checked;
}

public Passenger getPassenger()
{
	return passenger;
}

public Baggage getBaggage()
{
	return baggage;
}

public String checkInDetails()
{
	return "Passenger Name is : "+passenger.getPaxName().getLastName() +"\nBooking Reference is "+passenger.getBookingRef()+"\nExcess Weight is "+Double.toString(excessWeight)+"\nExcess Volume is "+Double.toString(excessVolume)+ "\nExcess Fee is is"+ Double.toString(getExcessFee());
	
}

public double  getExcessFee() {
	if(excessWeight!=0)
		excessFee=excessWeight*flight.getWeightPenalty();
	else excessFee=0;
	if(excessVolume!=0)
		excessFee+=excessVolume*flight.getVolumePenalty();
	else excessFee+=0;
		
		
	return excessFee;
}


public void calculateExcess()
{
	double b=baggage.getBagVolume();
	if(b>(flight.getMaxBagVolume()/flight.getMaxNumOfPax()))
	excessVolume=b-(flight.getMaxBagVolume()/flight.getMaxNumOfPax());
	else
		excessVolume=0;
	
	double c=baggage.getBagWeight() ;
	if(c>(flight.getMaxBagWeight()/flight.getMaxNumOfPax()))
	excessWeight=c-(flight.getMaxBagWeight()/flight.getMaxNumOfPax());
	else
		excessWeight=0;
}

public Double getexcessWeight()
{
	if (excessWeight==0)
		return 0.0 ;
	else return excessWeight ;
	
}

public Double getexcessVolume()
{
	if (excessVolume==0)
		return 0.0 ;
	else return excessVolume ;
	
}}


/*public boolean validBookingReference()
{
	String br=passenger.getBookingRef();
	String f=flight.getFlightCode();
	String pass=passenger.getPassportNum();
	int j=1;
	int size=br.length();
	
				for(int i=0;i<8;i++)
						{
								if(br.charAt(i)==pass.charAt(i))
									{System.out.println("True");
											j=1;}
								else
										return false;
						}	
				for(int k=8;k<size;k++)
						{
								if(br.charAt(k)==f.charAt(k))
								{System.out.println("True");
								j=1;}
								else
										return false;
						}
		return true;
}}*/









