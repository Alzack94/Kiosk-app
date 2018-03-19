package airportCheckIn;

import java.awt.event.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;



public class AirportController 
{
	private AirportModel airport;
	private AirportGUIView view;
	
	public AirportController(AirportModel a, AirportGUIView v) 
	{
		airport = a;
		view = v;
		view.addProceedCIDListener(new ProceedCIDController());
	}
		
    
    public class ProceedCIDController implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        { 
        	System.out.println("\nProceed Button in GUI has been clicked -> Starting Simulation");
        	view.disableProceedButton();
    		Thread thread = new Thread (airport);
    		thread.start();
    		    		
    		//view.updateCurrentItem(i.getItemReport());
    		//airport.processNextBid();
    		//view.setAfterProcessing();
    		//view.display(airport.getItemReport());
    		//if (airport.isFinished() )
   			//view.setAfterProcessing();
        }
    }

}
