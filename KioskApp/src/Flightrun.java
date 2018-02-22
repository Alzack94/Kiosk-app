
public class Flightrun {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AirlineManager a=new AirlineManager();
		a.run();
		gui g=new gui(a.getlist());
		a.generateReport();

	}

}
