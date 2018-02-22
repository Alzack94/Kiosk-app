package airportCheckIn;
import java.util.Comparator;

/**
 * This is the Comparator Class used to sort Flight objects based on their Flight Code.
 * @author Suraj Sivaprasad
 */
public class FlightCodeComparator implements Comparator<Flight>
{
	public int compare(Flight f1, Flight f2) 
	{
		Integer f1Code = new Integer(f1.getFlightCode());
		Integer f2Code = new Integer(f2.getFlightCode());
		return f1Code.compareTo(f2Code);
	}
}