package oaq.gui.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Avatar extends JPanel {

	Image imgBoder, imgAvatar;
	String userName;
	String ready;
	int width = 100, height = 120;

	public Avatar(String userName) {
		this.setOpaque(false);
		this.userName = userName;
		this.ready = "";
		this.imgBoder = (new ImageIcon("images/boderAvatar3.png")).getImage();
		this.imgAvatar = (new ImageIcon("images/noAvatar.png")).getImage();
		if (userName.length() * 9.5 > 100) {
			width = (int) (userName.length() * 9.5);
		}
	}

	public Avatar(String userName, ImageIcon imgAvatar) {
		this.setOpaque(false);
		this.userName = userName;
		this.imgBoder = (new ImageIcon("images/boderAvatar1.png")).getImage();
		this.imgAvatar = imgAvatar.getImage();
		if (userName.length() * 9.5 > 100) {
			width = (int) (userName.length() * 9.5);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		Font font = new Font("Serif", Font.BOLD, 18);
		g2d.setFont(font);
		g2d.setColor(Color.yellow);
		FontMetrics fm = g2d.getFontMetrics();
		int stringWidth = fm.stringWidth(userName);

		if (stringWidth < 100) {
			g2d.drawImage(imgBoder, 0, 0, 100, 100, null);
			g2d.drawImage(imgAvatar, 5, 5, 90, 90, null);
			g2d.drawString(userName, (100 - stringWidth) / 2, 115);
			
			g2d.setFont(new Font("Tahoma", Font.PLAIN, 20));
			g2d.setColor(Color.red);
			g2d.drawString(ready, 10, 30);
		} else {
			g2d.drawImage(imgBoder, (stringWidth - 100) / 2, 0, 100, 100, null);
			g2d.drawImage(imgAvatar, (stringWidth - 100) / 2 + 5, 5, 90, 90, null);
			g2d.drawString(userName, 0, 115);
			
			g2d.setFont(new Font("Tahoma", Font.PLAIN, 20));
			g2d.setColor(Color.red);
			g2d.drawString(ready, (stringWidth - 100) / 2 + 10, 30);
		}					
		setBounds(getLocation().x, getLocation().y, width, height);
	}

	public Dimension getSize() {
		return new Dimension(width, height);
	}

	public void setImgAvatar(ImageIcon imgAvatar) {
		this.imgAvatar = imgAvatar.getImage();
		repaint();
	}

	public void setUserName(String userName) {
		this.userName = userName;
		if (userName.length() * 9.5 > 100) {
			width = (int) (userName.length() * 9.5);
		}
		repaint();
	}
	
	public void setReady(boolean check) {
		if(check) {
			ready = "Sẵn Sàng";
		}
		else {
			ready = "";
		}
		repaint();
	}
	
	public String getUserName() {
		return userName;
	}
}
