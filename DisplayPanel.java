package assignment03;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class DisplayPanel extends JComponent {
	private static final long serialVersionUID = 6094419801472051751L;
	private String[] currentButtonString = ATMInternals.INIT_STRING;
	private JFrame frame;
	
	public DisplayPanel(JFrame frameIn) {
		frame = frameIn;
		this.setPreferredSize(new Dimension(500,300));
	}

	public void setCurrentButtonString(String[] array) {
		currentButtonString = array;
		System.out.println(Arrays.toString(currentButtonString));
		ATMgui.TEST_LOG.println(Arrays.toString(currentButtonString));
		frame.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) { 
		super.paintComponent(g);
		// Recover Graphics2D
		Graphics2D g2 = (Graphics2D) g;
		g2.setBackground(Color.BLACK);
		g2.fillRect(0, 0, 500, 300);
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Sansserif", Font.BOLD, 20));
		g2.setStroke(new BasicStroke(2));
		g2.drawString(currentButtonString[0], 10, 25);
		g2.drawString(currentButtonString[1], 10, 60);
		g2.drawString(currentButtonString[2], 10, 95);
		g2.drawString(currentButtonString[3], 10, 130);
		g2.drawString(currentButtonString[4], 10, 165);
	}
}
