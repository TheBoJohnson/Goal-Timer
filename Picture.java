/*Class displays the pictures from your set pictures folder randomly every time the timer sounds
 * Again change the file paths for your system. 
 * 
 * I also added some other methods for ease of use if you want to use this class for another project :)
 * */
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.*;
import javax.swing.JFrame;
import javax.swing.*; 

public class Picture {
	private JFrame frame;
	private PicPanel mainPanel;  
	private BufferedImage img; 
	private int width; 
	private int height; 
	private Random rand; 
	private File dir; 
	private File[] listOfPics; 
	
	public Picture(){
		frame = new JFrame();
		mainPanel = new PicPanel(); 
		frame.setContentPane(mainPanel);
		dir = new File("Pictures/"); 
		rand = new Random();
		
	}
	
	public void initalize(){		
		try{
			listOfPics = dir.listFiles(); 
			img = ImageIO.read(listOfPics[rand.nextInt(listOfPics.length)]);
		}
		catch(IOException exc){
			if(dir == null){
				System.out.println("dir was not initaialized properly");
			}
			else{
				System.out.println(dir.getPath());
			}

			if(img == null)
				System.out.println("img is null");
			
		}
		
		width = img.getWidth(); 
		height = img.getHeight(); 
		
		frame.setTitle("Motivations");
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void randomPic(){
		try{
			listOfPics = dir.listFiles(); 
			img = ImageIO.read(listOfPics[rand.nextInt(listOfPics.length)]); 
			height = img.getHeight(); 
			width = img.getWidth(); 
			resizeFrame(); 
			mainPanel.repaint(); 
		}
		catch(IOException e){
			
		}
	}
	
	public void changePic(String src){
			listOfPics = dir.listFiles();
			File picFile = new File(src); 
			
			try {
				img = ImageIO.read(picFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			height = img.getHeight();
			width = img.getWidth(); 
			resizeFrame();
			mainPanel.repaint();
	}
	
	public void resizeFrame(){
		frame.setSize(width, height);
	}
	
	private class PicPanel extends JPanel{
		public void paintComponent(Graphics g){
			g.drawImage(img, 0, 0, null);
		}
	}
}


