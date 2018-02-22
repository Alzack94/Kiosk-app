
public class alreadyCheckedInException extends Exception{
	private static final long serialVersionUID = 1L;

	public  alreadyCheckedInException (String done){
	super("Duplicate Booking Reference"+done);
	}
}
