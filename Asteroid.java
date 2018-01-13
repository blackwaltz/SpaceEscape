import javax.swing.*;          

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D.Double;

public class Asteroid extends JLabel {
	
	private ImageIcon asteroidIcon1 = new ImageIcon(Asteroid.class.getResource("images/asteroid1_128x125.gif").getPath().substring(1));  //.gif ЅЋ≈ј“№
	private ImageIcon asteroidIcon2 = new ImageIcon(Asteroid.class.getResource("images/asteroid2_170x170.gif").getPath().substring(1));
	private ImageIcon asteroidIcon3 = new ImageIcon(Asteroid.class.getResource("images/asteroid3_181x240.gif").getPath().substring(1));
	
	private double [][] figure1 = { {3,72}, {20,26}, {39,11}, {70,4}, {84,15}, {99,18}, {125,63}, {115,97}, {81,121}, {42,120}, {3,72} };
	private double [][] figure2 = { {3,72}, {39,16}, {84,4}, {136,21}, {161,57}, {168,97}, {154,135}, {119,161}, {94,167}, {54,165}, {35,154}, {9,119}, {3,72} };
	private double [][] figure3 = { {6,105}, {20,56}, {30,21}, {45,4}, {71,10}, {89,11}, {115,30}, {138,78}, {168,103}, {176,166}, {162,112}, {127,232}, {77,235}, {20,185}, {6,105} };
	
	private ImageIcon asteroidIcon;
	private double x, y;
	private boolean boom;
	private double [][] figureA;
	
	
	public Asteroid(int type, int x, int y) {
		super();
		
		//в зависимости от рандомно сгенерированного type выбираем одну из 3х моделек
		switch (type) {
        	case 1: asteroidIcon = asteroidIcon1; figureA = figure1; break;
        	case 2: asteroidIcon = asteroidIcon2; figureA = figure2; break;
        	case 3: asteroidIcon = asteroidIcon3; figureA = figure3; break;
		}
		
		this.setIcon(asteroidIcon);
		this.setBounds(10, 255, asteroidIcon.getIconWidth(), asteroidIcon.getIconHeight());
		this.setLocation(x, y);
		
	}
	
	//проверка коллизий
	public boolean checkIntersection(Spaceship ship){
		this.x = this.getLocation().getX();
		this.y = this.getLocation().getY();
		double shipX = ship.getLocation().getX();
		double shipY = ship.getLocation().getY();
		Double line1, line2;
		boom = false;
		
		if ((this.x > (shipX + ship.getIcon().getIconWidth())) || ((this.x + asteroidIcon.getIconWidth()) < shipX) || (this.y > (shipY + ship.getIcon().getIconHeight())) || ((this.y + asteroidIcon.getIconHeight()) < shipY)){
			boom = false; //оптимизаци€ блеать
		} else {
			for (int i = 0; i < ship.figure.length - 1; i++){
				line1 = new Double(ship.figure[i][0] + shipX, ship.figure[i][1] + shipY, ship.figure[i+1][0] + shipX, ship.figure[i+1][1] + shipY);
				for (int j = 0; j < this.figureA.length - 1; j++){
					line2 = new Double(this.figureA[j][0] + this.x, this.figureA[j][1] + this.y, this.figureA[j+1][0] + this.x, this.figureA[j+1][1] + this.y);
					if (line1.intersectsLine(line2)) boom = true;
				}
			}
			
		}
		
		return boom;
	}
	
}
