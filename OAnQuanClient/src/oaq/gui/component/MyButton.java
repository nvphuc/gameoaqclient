package oaq.gui.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MyButton extends JButton {

	private Image image;
	private Image imageMouseOver;
	private boolean isEnable;
	private String imgStr, imgOverStr, imgDisableStr;
	
	public MyButton(String nImage, String nImageMouseOver, String nImageDisable) {
		super();
		imgStr = nImage;
		imgOverStr = nImageMouseOver;
		imgDisableStr = nImageDisable;
		isEnable = true;
		image = (new ImageIcon("images/" + imgStr + ".png")).getImage();
		imageMouseOver = (new ImageIcon("images/" + imgOverStr + ".png")).getImage();
		setContentAreaFilled(false);
		setBorderPainted(false);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (getModel().isArmed()) {
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		} else if (getModel().isRollover()) {
			g.drawImage(imageMouseOver, 0, 0, getWidth(), getHeight(), null);
		}
		else {
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		}
	}
	
	@Override
	public void setEnabled(boolean check) {
		isEnable = check;
		if(isEnable) {
			image = (new ImageIcon("images/" + imgStr + ".png")).getImage();
		}
		else {
			image = (new ImageIcon("images/" + imgDisableStr + ".png")).getImage();
		}
		repaint();
		super.setEnabled(isEnable);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(image.getWidth(null), image.getHeight(null));		
	}
}