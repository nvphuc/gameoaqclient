package oaq.gui.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class Store extends JButton {

	private Vector<Point> stones;
	private Vector<Point> bigstones;
	private Image imageFlag, imageStore, imageStoreRaidan, imageStone,
			imageBigStone;
	public boolean isTurn, isRaidan;

	public Store() {
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		imageStore = (new ImageIcon("images/store.png")).getImage();
		imageStoreRaidan = (new ImageIcon("images/store_raidan.png"))
				.getImage();
		imageBigStone = (new ImageIcon("images/bigStoneNgang.png")).getImage();
		imageStone = (new ImageIcon("images/smallStone3.png")).getImage();
		imageFlag = (new ImageIcon("images/flag.png")).getImage();
		isTurn = false;
		isRaidan = false;		
		initStones();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D gr = (Graphics2D) g;
		if (isRaidan) {
			gr.drawImage(imageStoreRaidan, 0, 0, null);
		} else {
			gr.drawImage(imageStore, 0, 0, null);
		}
		if (isTurn)
			gr.drawImage(imageFlag, 0, 0, null);
		for (Point stone : stones) {
			gr.drawImage(imageStone, stone.x, stone.y, null);
		}
		for (Point bigstone : bigstones) {
			gr.drawImage(imageBigStone, bigstone.x, bigstone.y, null);
		}
		gr.setColor(Color.RED);
		gr.setFont(new Font("default", Font.BOLD | Font.ITALIC, 20));
		gr.drawString(stones.size() + "", 117, 70);
	}

	public void initStones() {
		stones = new Vector<Point>();
		bigstones = new Vector<Point>();
		refresh();
	}

	public void setRaidan(boolean check) {
		isRaidan = check;
		repaint();
	}
	
	public int getStonesNumber() {
		return stones.size();
	}
	
	public void removeStone(){
		stones.remove(0);
		repaint();
	}
	
	public void addStones(int amount) {
		int x, y;
		for (int i = 0; i < amount; i++) {
			x = (int) (Math.random() * 60 + 40);
			y = (int) (Math.random() * -10 + 30);
			stones.add(new Point(x, y));
		}
		refresh();
	}

	public void addBigStones(int amount) {
		int x, y;
		for (int i = 0; i < amount; i++) {
			x = (int) (Math.random() * -20 + 60);
			y = (int) (Math.random() * 30 + 25);
			bigstones.add(new Point(x, y));
		}
		refresh();
	}

	public Dimension getSize() {
		return new Dimension(imageStore.getWidth(null),
				imageStore.getHeight(null));
	}

	public void refresh() {
		repaint();
	}
}
