import java.util.*;
import java.io.*;

public class TaskManager {
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		System.out.println("Enter new task: ");
		String taskName = in.nextLine(); 
		while(!taskName.equals("exit"))
		{
			System.out.println("Enter one more task (or 'exit' for close): ");
			Task t = new Task(taskName);
			t.start();
			taskName = in.nextLine();
			t.stop();
		}
	}
}


interface ITask {
	void start();
	void stop();
	
}

class Task implements ITask{
	private Date timeStarted;
	private Date timeFinished;
	private String name;
	private boolean isStarted = false;
	private static final String fileName = "Tasks DB";
	public Task() {
		this.name ="New task";
		
	}
	public Task(String name) {
		this.name = name;
		
	}
	public void start()
	{	
		// Save data to file
		
				Date time = new Date();
				this.timeStarted = time;
                writeToFile(fileName, "\n"+this.name+"\t"+time.toString()+"\t");
				
	}
	public void stop()
	{	
		Date time = new Date();
		this.timeFinished = time;
        writeToFile(fileName, time.toString() + "\t"+ "Time: "+getDifference(this.timeStarted, this.timeFinished));
		
	}
	
	private void writeToFile(String name, String data){
		BufferedWriter writer = null;
        try
        {
				
                writer = new BufferedWriter( new FileWriter(name+".txt", true));
                writer.write(data);

        }
        catch ( IOException e)
        {
			System.out.println("Can't start new task!");
			System.out.println(e);
        }
        finally
        {
                try
                {
                        if ( writer != null)
                                writer.close( );
                }
                catch ( IOException e)
                {
					System.out.println(e);
                }
		}	
		
		
	}

	
	private String getDifference(Date start, Date end)
	{
	long diffInSeconds = (end.getTime() - start.getTime()) / 1000;

    long diff[] = new long[] { 0, 0, 0, 0 };
    /* sec */diff[3] = (diffInSeconds >= 60 ? diffInSeconds % 60 : diffInSeconds);
    /* min */diff[2] = (diffInSeconds = (diffInSeconds / 60)) >= 60 ? diffInSeconds % 60 : diffInSeconds;
    /* hours */diff[1] = (diffInSeconds = (diffInSeconds / 60)) >= 24 ? diffInSeconds % 24 : diffInSeconds;
    /* days */diff[0] = (diffInSeconds = (diffInSeconds / 24));

    return String.format(
        "%dh, %dm, %ds",
       
        diff[1],
       
        diff[2],
       
        diff[3]
        );
	}
    
}