import javax.swing.*;

import java.io.*;

import javax.swing.event.MouseInputAdapter;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

public class ScoresGlassPane extends JLabel {
		
	public ScoresGlassPane(){
		setIcon(new ImageIcon(ScoresGlassPane.class.getResource("images/ScoreBack.png").getPath().substring(1)));
		setBounds(1, 1, 800, 510);
		addMouseListener(new MouseAdapter() { //��� MouseListener ������ ��������� ��� ������� � f.gameOver.setEnabled(false); �� ��������. ���. ����. �� ���� ��������� �� �������.
			public void	mouseClicked(MouseEvent e) {
				setVisible(false);
				setEnabled(false); //setEnabled ������ ������ �� ��������, �� � ������� �� ������ ������. ������� ��� �������� ������ ��� �����-�� ������������ �������, � �� ��� ����.
			}
		});
	}
	
	public void paintComponent(Graphics g) { //��� ������� ��� �������� ���� paintComponent � ������ �� ��������� ���� ��� ���������� ����� � ������. ��� �������� � ��� �������������� ������!
		
		Graphics2D g1 = (Graphics2D) g; //paintComponent ��������� ������ Graphics. TextLayout - ������ Graphics2D. ������ ������ ��������� ����� Graphics2D? ����.
		//� ������ ���� ��������� � ���������� � ����� ��������� Graphics, � �� ������� ��� ��� ���������� field'�� Component � JComponent ���� ������. ������ �����.
		super.paintComponent(g1);
		
		g1.setColor(Color.lightGray);
		Font font = new Font("Helvetica", 1, 50);
		FontRenderContext frc = g1.getFontRenderContext();
		
		DataInputStream readingFile;
		TextLayout layout;
        String [] highScoresData;
        String [] score;
        int k=0;
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
						
					k++;
					readingFile.readByte();
					readingFile.readByte();
				}
			} catch (EOFException e) {
			}
			readingFile.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (k = 0; k < score.length; k++){
			layout = new TextLayout(score[k], font, frc);
			layout.draw(g1, 650, k*60+38); //���� �� ������� ������������ �� ������� ����, �� ���-�� ����.
			layout = new TextLayout(highScoresData[k], font, frc);
			layout.draw(g1, 10, k*60+38);
		}
				
	}

}
