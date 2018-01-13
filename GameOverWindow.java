import javax.swing.*;          
import java.io.*;
import java.util.*;
import java.util.Timer;
import java.awt.*;
import java.awt.event.*;

public class GameOverWindow extends JLayeredPane {
	
	
	private JLabel gameOverText;
	public LabelWT yourScore;
	private JLabel repeatButton;
	private JLabel highScoresButton;
	private JLabel exitButton;
	public ScoresGlassPane scoreScreen;
	private ImageIcon repeatButtonIcon = new ImageIcon(GameOverWindow.class.getResource("images/Retry.png").getPath().substring(1));
	private ImageIcon repeatButtonIconA = new ImageIcon(GameOverWindow.class.getResource("images/RetryA.png").getPath().substring(1));
	private ImageIcon gameOverTextIcon = new ImageIcon(GameOverWindow.class.getResource("images/GameOver3.gif").getPath().substring(1));
	private ImageIcon highScoresButtonIcon = new ImageIcon(GameOverWindow.class.getResource("images/HighScores.png").getPath().substring(1));
	private ImageIcon highScoresButtonIconA = new ImageIcon(GameOverWindow.class.getResource("images/HighScoresA.png").getPath().substring(1));
	private ImageIcon exitButtonIcon = new ImageIcon(GameOverWindow.class.getResource("images/Exit.png").getPath().substring(1));
	private ImageIcon exitButtonIconA = new ImageIcon(GameOverWindow.class.getResource("images/ExitA.png").getPath().substring(1));
	private final Space f;
	
	public GameOverWindow(final Space f) {
		super();
		this.setBounds(1, 1, 800, 510);
		this.f = f;
		
		
		//фон + картинка Game Over
		gameOverText = new JLabel();
		gameOverText.setIcon(gameOverTextIcon);
		gameOverText.setBounds(1, 1, 800, 510);
		add(gameOverText, new Integer(0));
		
		//текущий счет
		yourScore = new LabelWT("");
		yourScore.setBounds(200, 180, 400, 55);
		add(yourScore, new Integer(1));
		
		
				
		//перезапуск
		repeatButton = new JLabel();
		repeatButton.setIcon(repeatButtonIcon);
		repeatButton.setBounds(225, 220, 350, 80);  
		repeatButton.addMouseListener(new MouseAdapter() {
			public void	mouseEntered(MouseEvent e) {
				repeatButton.setIcon(repeatButtonIconA);
			}
			
			public void	mouseExited(MouseEvent e) {
				repeatButton.setIcon(repeatButtonIcon);
			}
			
			public void	mouseClicked(MouseEvent e) {
				f.gameOver.setVisible(false);
				f.gameOver.setEnabled(false);
				f.mainpanel.setVisible(true);
				f.mainpanel.setEnabled(true);
				f.timer = new Timer();				//—тарый таймер был убит. —оздаем новый. »де€ с timer.wait() и notifyAll() не сработала, так и не пон€л почему.
				f.timerBodyInst = new TimerBodyT(f);
				f.timer.schedule(f.timerBodyInst, 500, 20);
				f.mp3Player.play();
				f.mp3Player.setRepeat(true);
				
				scoreScreen.setVisible(false);
				scoreScreen.setEnabled(false);
			}
			
		});
		add(repeatButton, new Integer(2));
		
		//переход на экран high scores
		scoreScreen = new ScoresGlassPane(); 	//вот тут идет считывание из файла при создании панели. ƒалее эта панель не пересоздаетс€, но обновление очков происходит,
												//хот€ прорисовка компонента происходит только при его создании. ≈сть догадка (см. проверку коллизий в таймере).
		scoreScreen.setVisible(false);
		scoreScreen.setEnabled(false);
		scoreScreen.setBounds(1, 1, 800, 510);
		add(scoreScreen, new Integer(5));
		
		highScoresButton = new JLabel();
		highScoresButton.setIcon(highScoresButtonIcon);
		highScoresButton.setBounds(175, 300, 450, 80);
		highScoresButton.addMouseListener(new MouseAdapter() {
			public void	mouseEntered(MouseEvent e) {
				highScoresButton.setIcon(highScoresButtonIconA);
			}
			
			public void	mouseExited(MouseEvent e) {
				highScoresButton.setIcon(highScoresButtonIcon);
			}
			
			public void	mouseClicked(MouseEvent e) {
				scoreScreen.setVisible(true);
				scoreScreen.setEnabled(true);
				/*
				 * ќставлю дл€ истории. ѕоказывает, что setEnabled не работает таким образом.
				 * 
				highScoresButton.setEnabled(false);
				repeatButton.setEnabled(false);
				exitButton.setEnabled(false);
				*/
			}
			
		});
		add(highScoresButton, new Integer(2));
		
		//выход из приложени€
		exitButton = new JLabel();
		exitButton.setIcon(exitButtonIcon);
		exitButton.setBounds(225, 380, 350, 80);
		exitButton.addMouseListener(new MouseAdapter() {
			public void	mouseEntered(MouseEvent e) {
				exitButton.setIcon(exitButtonIconA);
			}
			
			public void	mouseExited(MouseEvent e) {
				exitButton.setIcon(exitButtonIcon);
			}
			
			public void	mouseClicked(MouseEvent e) {
				System.exit(0);
			}
			
		});
		add(exitButton, new Integer(2));
		
		
		
		
	}

}
