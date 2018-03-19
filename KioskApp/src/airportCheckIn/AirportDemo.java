package airportCheckIn;

public class AirportDemo 
{
	public static void main (String arg[])  
	{
		AirportModel model = new AirportModel();   //the model
		AirportGUIView view  = new AirportGUIView(model);
		AirportController controller = new AirportController(model, view);   
		view.setVisible(true);
	}
}
