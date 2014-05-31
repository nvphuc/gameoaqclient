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
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Cell extends JPanel {
	private int ID;
	private Vector<Point> stones;
	private Image imageSelect, imageCell, imageStone, imageBigStone;
	public boolean isSelected, isOver, isBigStone;

	public Cell(int ID) {
		setOpaque(false);
		this.ID = ID;
		switch (ID) {
		case 5:
			imageCell = (new ImageIcon("images/bigCellR.png")).getImage();
			break;
		case 11:
			imageCell = (new ImageIcon("images/bigCellL.png")).getImage();
			break;
		default:
			imageCell = (new ImageIcon("images/smallCell" + this.ID + ".png")).getImage();
			break;
		}
		imageSelect = (new ImageIcon("images/select.png")).getImage();
		imageBigStone = (new ImageIcon("images/bigStone0.png")).getImage();
		imageStone = (new ImageIcon("images/smallStone3.png")).getImage();
		isSelected = false;
		isOver = false;
		init();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D gr = (Graphics2D) g;
		if (ID != 5 && ID != 11) {
			gr.drawImage(imageCell, 0, 0, null);
			if (isSelected || isOver) {				
				gr.drawImage(imageSelect, 0, 0, null);
			}
			
			int x, y;
			for (Point stone : stones) {
				x = stone.x;
				y = stone.y;
				gr.drawImage(imageStone, x, y, null);
			}
			gr.setColor(Color.BLUE);
			gr.setFont(new Font("default", Font.BOLD | Font.ITALIC, 20));
			gr.drawString(stones.size() + "", 5, 90);
		} else {
			gr.drawImage(imageCell, 0, 0, null);
			if (isBigStone) {
				if (ID == 11)
					gr.drawImage(imageBigStone, 12, 75, null);
				else
					gr.drawImage(imageBigStone, 60, 75, null);
			}
			int x, y;
			for (Point stone : stones) {
				x = stone.x;
				y = stone.y;
				gr.drawImage(imageStone, x, y, null);
			}
			gr.setColor(Color.BLUE);
			gr.setFont(new Font("default", Font.BOLD | Font.ITALIC, 20));
			if (ID == 11)
				gr.drawString(stones.size() + "", 5, 190);
			else
				gr.drawString(stones.size() + "", 65, 190);
		}
	}

	public void initStones() {
		if (ID != 5 && ID != 11) {
			addStones(5);
		} else {
			isBigStone = true;
			refresh();
		}
	}
	
	public void init() {
		stones = new Vector<Point>();
		refresh();
	}

	public void addStones(int amount) {
		int x, y;
		if (ID != 5 && ID != 11) {
			for (int i = 0; i < amount; i++) {
				x = (int) (Math.random() * 50 + 15);
				y = (int) (Math.random() * 50 + 15);
				stones.add(new Point(x, y));
			}
		} else {
			if (ID == 11) {
				for (int i = 0; i < amount; i++) {
					x = (int) (Math.random() * 15 + 60);
					y = (int) (Math.random() * 130 + 35);
					stones.add(new Point(x, y));
				}
			} else {
				for (int i = 0; i < amount; i++) {
					x = (int) (Math.random() * 15 + 5);
					y = (int) (Math.random() * 130 + 35);
					stones.add(new Point(x, y));
				}
			}
		}
		refresh();
	}

	public int clearStones() {
		int amount = stones.size();
		isSelected = false;
		isBigStone = false;
		stones.clear();
		refresh();
		return amount;
	}

	public void refresh() {
		repaint();
	}
	
	public int getID() {
		return ID;
	}

	public int getStonesNumber() {
		return stones.size();
	}
	
	public void select(boolean check) {
		isSelected = check;
		repaint();
	}
	
	public void mouseOver(boolean check) {
		isOver = check;
		repaint();
	}

	public Dimension getSize() {
		return new Dimension(imageCell.getWidth(null), imageCell.getHeight(null));
	}
}