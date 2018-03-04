package airportCheckIn;

/**
 * Custom exception class that throws an error for a passenger who has already checked-In.
 * @author Suraj Sivaprasad
 * @author Sreepratha Ramasubramanian
 */
public class AlreadyCheckedInException extends Exception
{
	private static final long serialVersionUID = 1L;

	public  AlreadyCheckedInException (String b)
	{
		super("Duplicate Booking Reference "+b);
	}
}
