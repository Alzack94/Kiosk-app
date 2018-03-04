package airportCheckIn;

/**
 * This class contains the main method.
 * It creates and initialises an object of type SSHAirportManager and performs it's run() method
 * @author Hari Nikesh Suresh
 * @author Sreepratha Ramasubramanian
 * @author Suraj Sivaprasad
 */
public class MainClass {

	public static void main(String[] args)
	{
		SSHAirportManager ssh=new SSHAirportManager();
		ssh.run();
		SSHAirportGUI g=new SSHAirportGUI(ssh.getCollection());
		ssh.generateReport();
	}
}