import javax.swing.JOptionPane;

public class AirlineManager {

		private AirlinesCollection allacc;
		public AirlineManager() {
			allacc = new AirlinesCollection();
		}
		
		
		public void run() {
			System.out.println("Flights start");
	        allacc.populateFlights();
	        System.out.println("Flights done");
	        System.out.println("CheckIns start");
	        allacc.populateCheckIns();
	        System.out.println("CheckIns done");
	        
	        //print a report of all the check-ins
			System.out.println(allacc.getTableOfCheckIns());
			
		
		}
		

public AirlinesCollection getlist()
{
	return allacc;
}
}
