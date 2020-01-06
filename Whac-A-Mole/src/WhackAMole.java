
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
	The game is a 1 player java game using GUI, multi-thread and event listener in JAVA.
	Featured animation of moles lighting up and disappearing when the user whack them.
	Included points counter and sounds when the user smacks a mole.
	
	@author Yilu LIANG
	@version 1.0
 */
public class WhackAMole extends JFrame implements MouseMotionListener, Runnable{//extends JFrame instead of creating JFrame object
	/**A private JPanel object to contain components*/
	private JPanel jp;
	/**A private JLabel object to display score*/
	private JLabel Jtext; 
	/**A private array of JLabel to store the mole images*/
	private JLabel[] moleArr = new JLabel[14];
	/**A private integer to show the number of smacked mole*/
    private int clickedNumber = 0;
	
    /**
     * A constructor in order to create game background, mole images and run threads*/
	public WhackAMole() {
		this.setVisible(true);
		this.setBounds(400,200,920,518);
		this.setResizable(false);
		this.setTitle("Whack a Mole!");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jp = new JPanel();
		this.setContentPane(jp); //add panel to frame, cannot use add method, otherwise it will throw exception
		jp.setLayout(null);
			
		//set cursor
		Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().getImage("hammer.png"), new Point(0,0), "hammer");
		this.getContentPane().setCursor(c);
		
		this.addMouseMotionListener(this);//to find the coordinates of mole holes
		
		//create an array of moles
		for (int i = 0; i < moleArr.length; i++) {
			JLabel mole = new JLabel(new ImageIcon("mole.png"));//create the image of mole
//			JLabel hitMole = new JLabel(new ImageIcon("mole2.png"));
			moleArr[i] = mole;
			jp.add(mole);//add mole image to panel
//			moleArr[i].setVisible(false);
			
			// handle the click event
			moleArr[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Object source = e.getSource();// get the mole source
                    JLabel mouse = (JLabel) source;
                    if (mouse.getIcon() != null) {
                        mouse.setIcon(null);
                        clickedNumber++;// increase the number of smacked moles
                        Jtext.setText("Score: "+clickedNumber); //refresh the score
                        playmusic();
                    }
                }
            });
		}
		
		//display score
		Jtext=new JLabel();
		Jtext.setBounds(33, -23, 150, 150);
		Jtext.setFont(new Font("", 10, 20));
		Jtext.setForeground(Color.white.brighter());
		Jtext.setText("Score: "+clickedNumber);
		this.getContentPane().add(Jtext);
		
		//add moles to specific coordinates in bg
		moleArr[0].setBounds(76,68,223,223);
		moleArr[1].setBounds(276,68,223,223);
		moleArr[2].setBounds(476,68,223,223);
		moleArr[3].setBounds(676,68,223,223);
		moleArr[4].setBounds(590,138,223,223);
		moleArr[5].setBounds(355,135,223,223);
		moleArr[6].setBounds(120,135,223,223);
		moleArr[7].setBounds(730,212,223,223);
		moleArr[8].setBounds(496,212,223,223);
		moleArr[9].setBounds(232,212,223,223);
		moleArr[10].setBounds(0,210,223,223);
		moleArr[11].setBounds(676,300,223,223);
		moleArr[12].setBounds(355,300,223,223);
		moleArr[13].setBounds(30,300,223,223);
		
		//create the bg
		JLabel bg = new JLabel(new ImageIcon("bg.jpg"));
		jp.add(bg);
		bg.setBounds(0,0,920,518);
		
		//create a thread to handle the random appearance of moles
		new Thread(this).run();
		
	}
	
	/**
	 * This method is used to show the coordinate of the cursor
	 * @param e A MouseEvent object
	 * */
	@Override
	public void mouseDragged(MouseEvent e) {
		System.err.println(e.getX() +"," +e.getY());
	}

	/**
	 * This method is used to implement the abstract method mouseMoved and it is useless*/
	@Override
	public void mouseMoved(MouseEvent e) {
	}

	/**
	 * This method is used to add the music when a mole is smacked*/
	private void playmusic() {
		try {
			File musicFile = new File("music.wav");
			
			if(musicFile.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
			}
			else
				System.out.println("Cannot find the audio file.");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * This method is used to create a thread to display random number of moles
	 * */
	@Override
	public void run() {
		while(true) {
			for (int i = 0; i < moleArr.length; i++) {
				moleArr[i].setVisible(false); //clear the appearance of last moles
			}
			Random r = new Random();
			for (int i = 0; i < r.nextInt(10); i++) { //display 0 to 3 moles at a time
				moleArr[r.nextInt(13)].setVisible(true);
			}
			
			try {
				Thread.sleep(1500); //reappear at a certain intervals
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	/**main method*/
	public static void main(String[] args) {
		new WhackAMole();
	}

}
