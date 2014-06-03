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

import javax.swing.JButton;
import javax.swing.Timer;

public class EffectCredit extends JButton {

	private String content;
	Timer timer1, timer2;
	int size = 10;
	private float alpha = 1f;
	
	public EffectCredit(String content) {
		
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		this.content = content;
		
		timer2= new Timer(20, new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				alpha += -0.005f;
				if (alpha <= 0) {
					alpha = 0;
					timer2.stop();
					Container container = getParent();
					container.remove(EffectCredit.this);
					container.repaint();
				}
				repaint();				
			}
		});
		timer2.start();
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		
		Font font = new Font("VNI-Algriane", Font.PLAIN, size);
		g2d.setFont(font);

		FontMetrics fm = g2d.getFontMetrics();

		int w = (int) getSize().getWidth();
		int h = (int) getSize().getHeight();

		int stringWidth = fm.stringWidth(content);
		g2d.setColor(Color.yellow);
		g2d.drawString(content, (w - stringWidth) / 2, h / 2);
	}	
}
