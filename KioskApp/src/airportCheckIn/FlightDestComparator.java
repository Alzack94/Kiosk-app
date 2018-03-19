package airportCheckIn;
import java.util.Comparator;

/**
 * This is the Comparator Class used to sort Flight objects based on their Flight Name.
 * @author Suraj Sivaprasad
 */
public class FlightDestComparator implements Comparator<Flight>
{
	public int compare(Flight f1, Flight f2) 
	{
		return f1.getDestination().compareTo(f2.getDestination());
	}
}
