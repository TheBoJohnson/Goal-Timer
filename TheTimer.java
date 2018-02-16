/*This class is the second thread of the app that updates the JLabel that shows how much time is 
 * remaining on the app. 
 * 
 * It also opens the picture once the timing has been finished while starting the audio of the timer. 
 * 
 * Again I just uploaded these files from my personal computer without changing any of the file paths 
 * so make sure you change them in the code to work for your machine
 * */
import java.io.File;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JLabel; 

public class TheTimer implements Runnable{
	private int numMinutes; 
	private JLabel targetLabel;
	private Picture picFrame; 
	
	public TheTimer(int nM, JLabel theLabel){
		numMinutes = nM; 
		targetLabel = theLabel; 
	}
	
	public void run(){
		try{
			// this block calculates the number of miliseconds for the users input then uses a for loop to count down
			// I use the TimeUnit class to display the time correctly.
			int totalMilliSecs = numMinutes * 60000; 
			String newLabelText = ""; 
			
			for(int i = totalMilliSecs; i >= 0; i-=1000){
				newLabelText = String.format("%02d:%02d",
					    TimeUnit.MILLISECONDS.toMinutes(i),
					    TimeUnit.MILLISECONDS.toSeconds(i) - 
					    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(i)));
				
				targetLabel.setText(newLabelText);
				Thread.sleep(1000);
			}
		} catch(InterruptedException e){
			// this catch block catches the interrupt exception which will be thrown when the user pushes
			// the Cancel button. I simply return to stop the picture or the alarm from playing. 
			return; 
		}
		
		picFrame = new Picture(); 
		picFrame.initalize();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//picFrame.randomPic();
		playAlarm(); 
		
		
	}
	
	// method uses the AudioSystem suite of classes to play the alarm file.
	public void playAlarm(){
		   try {
		        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Sounds/Twin-bell-alarm-clock-ringing-short.wav").getAbsoluteFile());
		        Clip clip = AudioSystem.getClip();
		        clip.open(audioInputStream);
		        clip.start();
		        Thread.sleep(12000);
		    } catch(Exception ex) {
		        System.out.println("Error with playing sound.");
		        ex.printStackTrace();
		    }
	}
	
}
