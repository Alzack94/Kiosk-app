package airportCheckIn;

import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class AirportController 
{
	private AirportModel airport;
	private AirportGUIView view;
	
	public AirportController(AirportModel a, AirportGUIView v) 
	{
		airport = a;
		view = v;
		view.addProceedCIDListener(new ProcessCIDController());
	}
		
    
    public class ProcessCIDController implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        { System.out.println("Start");
        	view.disableProceedButton();
    		Thread thread = new Thread (airport);
    		System.out.println("Starting");
    		thread.start();
    		System.out.println("Starting");
    		//view.updateCurrentItem(i.getItemReport());


    			//auction.processNextBid();
    			//view.setAfterProcessing();
    		//view.display(auction.getItemReport());

    		//if (auction.isFinished() )
    			//view.setAfterProcessing();
        }
    }

}
