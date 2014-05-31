package oaq.gui.component;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class Effect extends JButton {

	private String content;
	Timer timer1, timer2;
	int size = 1;
	private float alpha = 1f;
	
	public Effect(String content) {
		
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		this.content = content;
		
		timer2= new Timer(20, new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				alpha += -0.013f;
				if (alpha <= 0) {
					alpha = 0;
					timer2.stop();
					Container container = getParent();
					container.remove(Effect.this);
					container.repaint();
				}
				repaint();				
			}
		});
		
		timer1 = new Timer(8, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				size += 1;
				repaint();
				if (size == 50) {
					timer1.stop();
					timer2.start();
				}
			}
		});
		timer1.start();
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		
		Font font = new Font("VNI-Algerian", Font.BOLD, size);
		g2d.setFont(font);

		FontMetrics fm = g2d.getFontMetrics();

		int w = (int) getSize().getWidth();
		int h = (int) getSize().getHeight();

		int stringWidth = fm.stringWidth(content);
		g2d.setColor(Color.red);
		g2d.drawString(content, (w - stringWidth) / 2, h / 2);
	}	
}
