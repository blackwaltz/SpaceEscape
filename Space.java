import javax.swing.*;          

import jaco.mp3.player.MP3Player;

import java.util.*;
import java.util.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.io.File;

public class Space extends JFrame {
	
	public MP3Player mp3Player;
	public MP3Player failSoundPlayer;
	Timer timer;
	Spaceship spaceship;
	private int delay = 0;
	private Asteroid ast;
	private ArrayList<Asteroid> asteroidList;
	public JLayeredPane mainpanel;	
	LabelWT label;
	public GameOverWindow gameOver;
	public boolean boom1;
	public TimerBodyT timerBodyInst;
		
	public Space(){											//конструктор создает JFrame со всеми внутренними компонентами, в main лишь создаетс€ один экземпл€р такого JFrame
		super("Space Escape");
	 	asteroidList = new ArrayList<Asteroid>();
	 	
	 	setPreferredSize(new Dimension(800, 510));
	 	setLayout(null);
	 	
		mainpanel = new JLayeredPane();
		mainpanel.setPreferredSize(new Dimension(800, 510));
		mainpanel.setBounds(1, 1, 800, 510);
		
		JLabel background = new JLabel(new ImageIcon(Space.class.getResource("images/stars800x510.jpg").getPath().substring(1)));
		background.setBounds(1, 1, 800, 510);
		mainpanel.add(background, new Integer(1));		//неплохо было бы заставить фон циклично двигатьс€
				
		String text1 = "Score: 0";
		label = new LabelWT(text1);
		label.setBounds(1, 1, 400, 55);
		mainpanel.add(label, new Integer(2));
		
		mp3Player = new MP3Player(new File(Space.class.getResource("sound/TheManWithTheMachineGun.mp3").getPath().substring(1)));
		//failSoundPlayer = new MP3Player(new File(Space.class.getResource("sound/FAIL.mp3").getPath().substring(1)));
		
		spaceship = new Spaceship();						//корабль следует за курсором, потом можно реализовать инерцию движени€ дл€ усложнени€ игры
		mainpanel.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				//spaceship.setLocation(e.getX()-65, e.getY()-40); 
				spaceship.xMoveDirection = e.getX()-65;
				spaceship.yMoveDirection = e.getY()-40;
			}
			public void mouseDragged(MouseEvent e) {
				//spaceship.setLocation(e.getX()-65, e.getY()-40); 
				spaceship.xMoveDirection = e.getX()-65;
				spaceship.yMoveDirection = e.getY()-40;
			}
		});
		mainpanel.add(spaceship, new Integer(2));
				
		timer = new Timer();
		timerBodyInst = new TimerBodyT(this);
		timer.schedule(timerBodyInst, 500, 20);
		mp3Player.play();
		mp3Player.setRepeat(true);
				
		gameOver = new GameOverWindow(this);
		gameOver.setVisible(false);
		gameOver.setEnabled(false);
		
		//add(mainpanel); - можно просто вот так. »спользовать getContentPane() нужно не всегда, но дл€ простоты можно всегда использовать.
		Container contentPane = getContentPane();
		contentPane.add(mainpanel);
		contentPane.add(gameOver);
		
	}

	
	public static void main(String[] args) {
		JFrame mainframe = new Space();
		mainframe.setResizable(false);		
		mainframe.setLocation(200, 100);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.pack();
		mainframe.setVisible(true);

	}

}
