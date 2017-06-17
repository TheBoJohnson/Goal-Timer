/* Goal Timer
 * Written by Bo Johnson 6/17/17
 * 
 * Goal Timer is a simple timer written in Java that allows you to choose 
 * between 5, 10, 25, 50 or a custom amount of time to set the timer.
 * 
 * When the alloted time has elapsed, an alarm will sound along with a random image from 
 * a preselected directory. 
 * 
 * The image can be of anything but I intended for it to be of a goal you want to achieve or 
 * something you want to accomplish. After a hard 50 minutes of intense focus seeing the image 
 * of what you want will keep you motivated and focused on your objectives. 
 * 
 * Feel free to fork the project. I have plans on putting the project in a JAR file and making it more 
 * polished, but for now I have written it for my personal needs. 
 * 
 * Thanks for checking it out and enjoy!
 * 
 * */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Gui extends JFrame{ 
	private Container theCont;
	private JPanel center; 
	private JPanel upper; 
	private JPanel lower; 
	private JPanel bottom; 
	private JLabel timeLabel; 
	private JButton custom; 
	private JButton cancel; 
	private JButton[] timeButtons; 
	private Thread btnTimeThread; 
	
	public static void main(String[] args){
		new Gui();
	}
	
	public Gui(){
		super("Goal Timer");
		setSize(1000, 750); 
		setResizable(false);
		initalize(); 
		
		theCont.setLayout(new BorderLayout());
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		
		// setting up the time label 
		upper.add(timeLabel);
		
		// setting up the buttons
		for(int i = 0; i < timeButtons.length; i++){
			lower.add(timeButtons[i]);
		}
		
		
		// adding all the panels for the final layout of the app
		center.add(upper);
		center.add(lower);
		
		bottom.add(custom);
		bottom.add(cancel);
		
		theCont.add(center, BorderLayout.CENTER); 
		theCont.add(bottom, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true); 
	}
	
	// method initalizes all of the components for the app
	// was written to have the constructor focus only on the view of the app
	private void initalize(){
		theCont = getContentPane();
		
		center = new JPanel(); 
		
		bottom = new JPanel(); 
		bottom.setBorder(new EmptyBorder(0, 0, 200, 0));
		
		upper = new JPanel();
		upper.setBorder(new EmptyBorder(225, 0, 0, 0));
		
		lower = new JPanel();
		lower.setBorder(new EmptyBorder(0, 0, 250, 0));
		
		timeButtons = new JButton[4]; 
		timeButtons[0] = new JButton("5 Minutes"); 
		timeButtons[1] = new JButton("10 Minutes"); 
		timeButtons[2] = new JButton("25 Minutes"); 
		timeButtons[3] = new JButton("50 Minutes"); 
		
		TimeButtonListener list1 = new TimeButtonListener();
		
		for(int i = 0; i < timeButtons.length; i++){
			timeButtons[i].addActionListener(list1);
		}
		
		timeLabel = new JLabel("00:00"); 
		timeLabel.setFont(new Font("Serif", Font.PLAIN, 150));
		
		CustomButtonListener list2 = new CustomButtonListener(); 
		custom = new JButton("Custom Time"); 
		custom.addActionListener(list2);
		
		CancelButtonListener list3 = new CancelButtonListener(); 
		cancel = new JButton("Cancel"); 
		cancel.addActionListener(list3);
		
		
	}
	
	// method sets the btnTimeThread reference to a new TheTimer Thread and starts it 
	public void startTimer(int timeMili){
		btnTimeThread = new Thread(new TheTimer(timeMili, timeLabel), "Time Thread"); 
		btnTimeThread.start(); 
	}
	
	
	// this action listener class is for the preprogrammed time buttons
	private class TimeButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(btnTimeThread != null){
				return; 
			}
			JButton src = (JButton) e.getSource();
			// here I parse the int from the labels on the buttons to determine how much time
			// the timer thread should depending on which button is pushed by the user. 
			int time = Integer.parseInt(src.getText().substring(0, 2).trim()); 
			startTimer(time); 
			
		}
	}
	// similar to the TimeButtonListenr except instead of parsing the integer from the labels of the preprogrammed buttons
	// I open a JOptionPane that asks the user how many minutes they want and parse that. 
	private class CustomButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(btnTimeThread != null){
				return; 
			}
			JButton src = (JButton) e.getSource(); 
			int time = Integer.parseInt(JOptionPane.showInputDialog(src.getParent().getParent(), "How many minutes do you want?").trim());
			startTimer(time); 
		}
	}
	
	// this action listener enables the user to reset the timer once it has been started.
	private class CancelButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(btnTimeThread.isAlive()){
				btnTimeThread.interrupt();
				timeLabel.setText("00:00");
				btnTimeThread = null;
			}
		}
	}
	
}
