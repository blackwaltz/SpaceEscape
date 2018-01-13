import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

import javax.swing.*;          

import java.util.*;
import java.util.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class TimerBodyT extends TimerTask {					//TimerBody - класс компонента, используемого в timer. ћетод run() - собственно тело таймера
	
	public boolean boom;
	private Space frame;
	private Integer delay;
	private Asteroid ast;
	private ArrayList<Asteroid> asteroidList = new ArrayList<Asteroid>();
	public String text;
	int acceleration = 0;

	public TimerBodyT(Space frame){			//передаем слоистую панель в качестве параметра, на которую надо будет добавл€ть экземпл€ры астероидов
		this.delay = 0;
		this.frame = frame;
	}
	
	public void run() {
		delay++;   									//1 delay = 20 ms
		text = Integer.toString(delay);
		frame.spaceship.moveDamnShip();
					
		if (delay % 3 == 0) {
			frame.mainpanel.remove(frame.label);
			frame.label = new LabelWT("Score: " + text);
			frame.label.setBounds(1, 1, 400, 55);
			frame.mainpanel.add(frame.label, 2, 1);
		}
		
		if (delay % (50 - acceleration/7) == 0) {						//каждую секунду создаем новый астероид
			int type = new Random().nextInt(3)+1;
			int x = 800;
			int y = new Random().nextInt(510-170);
			acceleration++;
			ast = new Asteroid(type, x, y);
			asteroidList.add(ast);
			frame.mainpanel.add(ast, new Integer(2));
			
			
			
		}
		
		//цикл дл€ перебора всех астероидов: прочекать каждый на коллизию с кораблем и удалить ненужные
		for (int i=0; i < asteroidList.size(); i++){
			ast = asteroidList.get(i);
			int xpos = (int) ast.getLocation().getX();
		    int ypos = (int) ast.getLocation().getY();
		    ast.setLocation(xpos - 10 - acceleration/3, ypos);
		    if (xpos + 180 < 0){
		    	frame.mainpanel.remove(ast);
		    	asteroidList.remove(i);				//”дал€ем астероид из массива и с панели. Ќадеюсь этого достаточно, чтобы сборщик мусора убрал ненужный астероид
		    }
			boom = ast.checkIntersection(frame.spaceship);		//проверка коллизий
			/*
			 * ƒалее до конца идет большой блок - все то, что мы делаем при обнаружении коллизии.
			 */
			
			if (boom) {
				//System.out.println("BOOM!" + text);		
				for (int j=0; j < asteroidList.size(); j++){
					ast = asteroidList.get(j);
					frame.mainpanel.remove(ast);
			    	
				}
				frame.mp3Player.stop();
				asteroidList.clear();
				frame.mainpanel.setVisible(false);
				frame.mainpanel.setEnabled(false);
				frame.timer.cancel(); 				//”биваем таймер. timer.wait() и notify() не работают почему-то. «ато получилось вынести TimerBody во внешний класс и создавать таймер заново
				frame.getContentPane().add(frame.gameOver); //”ж не из-за этой ли строчки вызываетс€ прорисовка панели. Ќо получаетс€ € каждый раз добавл€ю новую панель. ¬ общем странно это.
				frame.gameOver.setEnabled(true);
				frame.gameOver.setVisible(true);
				frame.boom1 = true;
				
				//перерисовываем текущий счет на экране game over
				frame.gameOver.remove(frame.gameOver.yourScore);
				frame.gameOver.yourScore = new LabelWT("Score: " + text);
				frame.gameOver.yourScore.setBounds(250, 180, 400, 55);
				frame.gameOver.add(frame.gameOver.yourScore, new Integer(1));
				
				Date d = new Date();
		        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy hh:mm");
		        
		        DataInputStream readingFile;
		        Integer[] highScores;
		        String [] highScoresData;
		        String [] score;
		        int k=0;
		        highScores = new Integer[5];
				highScoresData = new String [5];
				score = new String [5];
				
				try {
					readingFile = new DataInputStream(new BufferedInputStream(new FileInputStream(ScoresGlassPane.class.getResource("data/highscores.txt").getPath().substring(1))));
										
					try {
						while (true) {
							char chr;
							char lineSep = System.getProperty("line.separator").charAt(0);
							highScoresData[k] = "";
							score[k] = "";
							String spaceSymbol = "_";
							while ((chr = (char) readingFile.readByte()) != (spaceSymbol.charAt(0)))
								highScoresData[k] += Character.toString(chr); 
								
							while ((chr = (char) readingFile.readByte()) != (spaceSymbol.charAt(0)))
								score[k] += Character.toString(chr);
								
							highScores[k] = Integer.parseInt(score[k]);
							k++;
							readingFile.readByte();
							readingFile.readByte();
						}
					} catch (EOFException e) {
					}
					readingFile.close();
					
					/*
					 * ¬ывод на консоль дл€ упрощени€ отладки
					 * 
					for (k = 0; k < highScores.length; k++){
						System.out.println(highScores[k]);
					}
					
					for (k = 0; k < highScores.length; k++){
						System.out.println(highScoresData[k]);
					}
					*/
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				for (k = 0; k < highScores.length; k++){
					if (delay > highScores[k]){
						for (int m = highScores.length-1; m > k; m--){
							highScores[m] = highScores[m-1];
							highScoresData[m] = highScoresData[m-1];
						}
						highScores[k] = delay;
						highScoresData[k] = format1.format(d);	
						break;	
					}
				}
				/*
				 * ¬ывод на консоль дл€ упрощени€ отладки
				 *  
				for (k = 0; k < highScores.length; k++){
					System.out.println(highScores[k]);
				}
				
				for (k = 0; k < highScores.length; k++){
					System.out.println(highScoresData[k]);
				}
				*/
				try {
					BufferedWriter writingFile = new BufferedWriter(new FileWriter(ScoresGlassPane.class.getResource("data/highscores.txt").getPath().substring(1)));
					for (k = 0; k < highScores.length; k++){
						writingFile.write(highScoresData[k]+"_");
						if (k == 4){
							writingFile.write(Integer.toString(highScores[k])+"_");
						} else{
							writingFile.write(Integer.toString(highScores[k])+"_\r\n"); //если добавить перенос строки в конце, то будет ошибка при следующем чтении файла, так как процесс попытаетс€ прочитать 6ю строку, когда их всего должно быть 5.
						}
						writingFile.flush(); //на вс€кий случай
					}
					
					writingFile.close();
				} catch (IOException e) {	}
				
				
				this.delay = 0;//осталось от варианта с timer.wait() ѕусть будет.
			}
		}
	}
}
