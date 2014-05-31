package oaq.gui.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Table extends JButton {

	private String name;
	private Image image;
	private Image imageMouseOver;
	
	public Table(String name) {
		super();
		this.name = name;
		image = (new ImageIcon("images/Table.png")).getImage();
		imageMouseOver = (new ImageIcon("images/Table_MouseOver.png")).getImage();
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
	
	public Dimension getPreferredSize() {
		return new Dimension(image.getWidth(null), image.getHeight(null));		
	}

	public String getName(){
		return name;
	}
}