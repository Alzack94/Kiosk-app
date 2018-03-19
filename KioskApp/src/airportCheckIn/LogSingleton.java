package airportCheckIn;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class LogSingleton 
{
private static LogSingleton instance;
private String log;
private int a=0;

private LogSingleton() 
{  
this.log=""; 
}


public static LogSingleton getInstance() {
if (instance == null) // only if no instance
{
synchronized(LogSingleton.class) //lock block
{ 
if (instance == null) //Double Check
instance = new LogSingleton();
}
}
return instance;
}
public void EnterQueue(Passenger p) 
{a++;
	log+= "\nlog"+a+": "+p.getPaxName().getLastName()+" has entered the queue ";
}
public void LeaveQueue(Passenger p,int c) 
{a++;
	log+= "\nlog"+a+": "+p.getPaxName().getLastName()+" has left the queue,is at Check-In desk "+c;
}
public void CheckedIn(Passenger p,int b) 
{a++;
	log+= "\nlog"+a+": "+p.getPaxName().getLastName()+" has checked in at Desk"+b;
}
public void Remaining(Passenger p) 
{a++;
	log+= "\nlog"+a+": "+p.getPaxName().getLastName()+" has checked in ";
}
public String getLog()
{
	return log;
}

/**
 * A method to write the supplied text to the file in the supplied filename.
 * It can handle File-Not-Found and IO Exceptions.
 * @param filename The name of the file to be written to
 * @param report The text to be written to the file
 * @exception IOException
 * @exception FileNotFoundException
 */
public void writeToFile(String filename, String report) 
{
	FileWriter fw;
	try
	{
		fw = new FileWriter(filename);
		fw.write(report);
		fw.close();
	}
	catch (FileNotFoundException fnf)	//Print message and normal exit, if file is not found
	{
		System.out.println("The file at "+ filename + " was not found!");
		System.exit(0);
	}
	catch (IOException ioe)		//to print IO Exception Stack Trace
	{
		ioe.printStackTrace();
		System.exit(1);
	}
}

}