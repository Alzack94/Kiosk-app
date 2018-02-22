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
		}
	        //print a report of all the check-ins
	        public void generateReport()
	        {
	        if(allacc.CheckInFinish()==true)
			System.out.println(allacc.getTableOfCheckIns());
	        else
	        	System.out.println("CheckIn Incomplete,Cannot produce report");
			}
		

public AirlinesCollection getlist()
{
	return allacc;
}
}
