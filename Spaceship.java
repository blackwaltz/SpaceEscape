import javax.swing.*;          

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Spaceship extends JLabel{
	private ImageIcon spaceshipIcon = new ImageIcon(Spaceship.class.getResource("images/spaceship130x80.gif").getPath().substring(1));
	public double [][] figure;
	public int xMoveDirection, yMoveDirection;
		
	public Spaceship(){
		super();
		this.setIcon(spaceshipIcon);
		this.setBounds(100, 255, spaceshipIcon.getIconWidth(), spaceshipIcon.getIconHeight());
		figure = new double[][] { {1,26}, {27,25}, {27,9}, {56,1}, {95,1}, {130,34}, {130,62}, {84,80}, {55,80}, {27,71}, {27,54}, {1,54}, {1,26} };
					
	}
	
	//продвинутая анимация
	public void moveDamnShip(){
		int xDest, yDest;
		xDest = (this.getLocation().x - xMoveDirection)/6;
		yDest = (this.getLocation().y - yMoveDirection)/6;
		setLocation(this.getLocation().x - xDest, this.getLocation().y - yDest);
	}
	

}
	

