import javax.swing.*;

import java.awt.font.*;
import java.awt.*;
import java.util.*;
public class LabelWT extends JLabel{
	
	public String text;
						
	LabelWT(String text) {
		setPreferredSize(new Dimension(400,55));
		this.text = text;
		
	}
	
	public void paintComponent(Graphics g){
		
		Graphics2D g1 = (Graphics2D) g;
		
		super.paintComponent(g1);
		
		g1.setColor(Color.lightGray);
		Font font = new Font("Helvetica", 1, 50);
		FontRenderContext frc = g1.getFontRenderContext();
		TextLayout layout = new TextLayout(text, font, frc);
		layout.draw(g1, 10, 38);
		
		
	}
			
}
