package oaq.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import oaq.game.Game;
import oaq.gui.component.Avatar;
import oaq.gui.component.Cell;
import oaq.gui.component.Effect;
import oaq.gui.component.MyButton;
import oaq.gui.component.MyJScrollPane;
import oaq.gui.component.MyPanel;
import oaq.gui.component.Store;
import oaq.processor.ProcessorGuiPlay;

public class GuiPlay extends Gui {

	// Thuoc tinh giao dien
	public JPanel pnChat;
	public JScrollPane scrollPaneChat;
	public JTextArea txtContent;
	public JTextField txtChat;
	public JButton btSendChat;

	public Store stores[];
	public Cell cells[];
	public JButton btLeft;
	public JButton btRight;
	public JPanel pnBoard;

	public JButton btLeaveTable, btReady, btInvite;

	public JLabel lbMoney;

	public JPanel[] pnPlayer;
	public Avatar[] avatarPlayer;
	
	public GuiPlay(Game game, Point location, int orderNumber) {
		super(game, location, "BackGroundPlay");
		setTitle("Play Game");
		processor = new ProcessorGuiPlay(this, orderNumber);
		setGui();
		Thread thread = new Thread((ProcessorGuiPlay) processor);
		thread.start();
	}

	@Override
	public void setGui() {
		
		// Tao board panel
		pnBoard = new JPanel();
		pnBoard.setLayout(null);
		pnBoard.setOpaque(false);
		pnBoard.setBounds(174, 155, 652, 204);
		pnMain.add(pnBoard);

		// Tao cac cells
		cells = new Cell[12];
		int x = 0, y = 0;

		// Bigcell trai
		cells[11] = new Cell(11);
		cells[11].setBounds(x, y, cells[11].getSize().width,
				cells[11].getSize().height);
		pnBoard.add(cells[11]);

		x += cells[11].getSize().width;

		// Cells tren
		for (int i = 10; i >= 6; i--) {
			cells[i] = new Cell(i);
			cells[i].setBounds(x, y, cells[i].getSize().width,
					cells[i].getSize().height);
			pnBoard.add(cells[i]);
			x += cells[i].getSize().width;
		}

		// Bigcell phai
		cells[5] = new Cell(5);
		cells[5].setBounds(x, y, cells[5].getSize().width,
				cells[5].getSize().height);
		pnBoard.add(cells[5]);

		// Cells duoi
		x = 94;
		y += cells[10].getSize().height;
		for (int i = 0; i < 5; i++) {
			cells[i] = new Cell(i);
			cells[i].setBounds(x, y, cells[i].getSize().width,
					cells[i].getSize().height);
			pnBoard.add(cells[i]);
			x += cells[i].getSize().width;
		}

		createListenerForCell();

		// Button mui ten
		x = 267;
		y = 360;
		btLeft = new MyButton("leftBt", "leftBt_Over", "leftBt");
		btLeft.addActionListener(this);
		btLeft.setBounds(x, y, 93, 40);
		pnMain.add(btLeft);

		x = 639;
		btRight = new MyButton("rightBt", "rightBt_Over", "rightBt");
		btRight.addActionListener(this);
		btRight.setBounds(x, y, 93, 40);
		pnMain.add(btRight);

		// pnPlayer[1]
		pnPlayer = new JPanel[2];

		pnPlayer[1] = new JPanel();
		pnPlayer[1].setLayout(null);
		pnPlayer[1].setOpaque(false);

		stores = new Store[2];
		stores[1] = new Store();
		stores[1].setBounds(0, (120 - stores[1].getSize().height) / 2,
				stores[1].getSize().width, stores[1].getSize().height);
		pnPlayer[1].add(stores[1]);

		// avatar
		avatarPlayer = new Avatar[2];

		avatarPlayer[1] = new Avatar("");
		avatarPlayer[1].setBounds(stores[1].getSize().width + 40, 0,
				avatarPlayer[1].getSize().width,
				avatarPlayer[1].getSize().height);
		pnPlayer[1].add(avatarPlayer[1]);

		int pnWidth = stores[1].getSize().width
				+ avatarPlayer[1].getSize().width + 40;
		pnPlayer[1].setBounds((1000 - pnWidth) / 2, 26,
				stores[1].getSize().width + avatarPlayer[1].getSize().width
						+ 40, 130);
		pnMain.add(pnPlayer[1]);

		// pnPlayer[0]
		pnPlayer[0] = new JPanel();
		pnPlayer[0].setLayout(null);
		pnPlayer[0].setOpaque(false);

		avatarPlayer[0] = new Avatar("");
		avatarPlayer[0].setBounds((avatarPlayer[1].getSize().width - 100) / 2,
				0, avatarPlayer[1].getSize().width,
				avatarPlayer[1].getSize().height);
		pnPlayer[0].add(avatarPlayer[0]);

		stores[0] = new Store();
		stores[0].addActionListener(this);
		stores[0].setBounds(160, (120 - stores[1].getSize().height) / 2,
				stores[0].getSize().width, stores[0].getSize().height);
		pnPlayer[0].add(stores[0]);

		pnWidth = stores[0].getSize().width + avatarPlayer[1].getSize().width
				+ 60;
		pnPlayer[0].setBounds((1000 - pnWidth) / 2, 400,
				stores[0].getSize().width + avatarPlayer[1].getSize().width
						+ 60, 130);
		pnMain.add(pnPlayer[0]);

		// Tao khung chat
		pnChat = new MyPanel("BackGround2");
		pnChat.setLayout(null);
		pnChat.setOpaque(false);
		pnChat.setBounds(10, 430, 280, 195);
		pnMain.add(pnChat);

		scrollPaneChat = new JScrollPane();
		scrollPaneChat.setBounds(6, 14, 268, 136);
		pnChat.add(scrollPaneChat);

		txtContent = new JTextArea();
		txtContent.setEditable(false);
		scrollPaneChat.setViewportView(txtContent);

		txtChat = new JTextField();
		txtChat.addActionListener(this);
		txtChat.setColumns(10);
		txtChat.setBounds(6, 155, 218, 25);
		pnChat.add(txtChat);

		btSendChat = new JButton("Gửi");
		btSendChat.addActionListener(this);
		btSendChat.setMargin(new Insets(0, 0, 0, 0));
		btSendChat.setAlignmentY(0.0f);
		btSendChat.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btSendChat.setBounds(234, 155, 40, 25);
		pnChat.add(btSendChat);

		btLeaveTable = new MyButton("btRoiBan", "btRoiBan_Over",
				"btRoiBan_Disable");
		btLeaveTable.addActionListener(this);
		btLeaveTable.setBounds(883, 575, btLeaveTable.getPreferredSize().width,
				btLeaveTable.getPreferredSize().height);
		pnMain.add(btLeaveTable);

		btReady = new MyButton("btReady", "btReady_Over", "btReady_Disable");
		btReady.addActionListener(this);
		btReady.setBounds(778, 572, btReady.getPreferredSize().width,
				btReady.getPreferredSize().height);
		pnMain.add(btReady);

		btInvite = new MyButton("btMoi", "btMoi_Over", "btMoi_Disable");
		btInvite.addActionListener(this);
		btInvite.setBounds(669, 572, btInvite.getPreferredSize().width,
				btInvite.getPreferredSize().height);
		pnMain.add(btInvite);

		lbMoney = new JLabel("Tiền : " + getGame().getPlayer().getCredit());
		lbMoney.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lbMoney.setForeground(Color.yellow);
		lbMoney.setBounds(321, 583, lbMoney.getPreferredSize().width,
				lbMoney.getPreferredSize().height);
		pnMain.add(lbMoney);

		setVisible(true);
	}

	public boolean allowToClick() {
		ProcessorGuiPlay processor = (ProcessorGuiPlay) this.processor;
		return (stores[0].isTurn == true && processor.isMovingStones == false);
	}

	public void createListenerForCell() {
		final ProcessorGuiPlay processor = (ProcessorGuiPlay) this.processor;
		for (int i = 0; i < 5; i++) {
			cells[i].addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (allowToClick()) {
						Cell cell = (Cell) e.getSource();
						cell.select(true);
						int preCell = processor.selectedCell;
						processor.selectedCell = cell.getID();
						if (preCell > -1) {
							cells[preCell].select(false);
						}
						if (cell.isSelected == false)
							processor.selectedCell = -1;
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					Cell cell = (Cell) e.getSource();
					cell.mouseOver(true);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					Cell cell = (Cell) e.getSource();
					cell.mouseOver(false);
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}
			});
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ProcessorGuiPlay processor = (ProcessorGuiPlay) this.processor;

		if (e.getSource() == btReady) {
			processor.sendReady();
			return;
		}
		if (e.getSource() == btLeft) {
			processor.sendMove(-1);
			return;
		}
		if (e.getSource() == btRight) {
			processor.sendMove(1);
			return;
		}
		if (e.getSource() == btSendChat || e.getSource() == txtChat) {
			processor.sendChat();
			return;
		}
		if (e.getSource() == btLeaveTable) {
			processor.leaveTable();
			return;
		}
		if (e.getSource() == btInvite) {
			processor.invite();
			return;
		}
		if (e.getSource() == stores[0] && stores[0].isRaidan == true) {
			processor.raiDan();
		}
	}

	public void showStartGame() {
		String notice = "BAÉT ÑAÀU GAME";
		Effect effect = new Effect(notice);
		effect.setBounds((pnEffect.getWidth() - notice.length() * 60) / 2,
				(pnEffect.getHeight() - 130) / 2, notice.length() * 60, 130);
		pnEffect.add(effect);
		pnEffect.repaint();
	}
	
	public void showWinner(boolean check) {
		String notice = "";
		if(check){
			notice = "BAÏN THAÉNG";
		}
		else{
			notice = "BAÏN THUA";
		}
		Effect effect = new Effect(notice);
		effect.setBounds((pnEffect.getWidth() - notice.length() * 60) / 2,
				(pnEffect.getHeight() - 130) / 2, notice.length() * 60, 130);
		pnEffect.add(effect);
		pnEffect.repaint();
	}

	public void showReport(String report, int orderNumber) {
		String[] players = report.split("#");
		String[] thisPlayer = players[orderNumber].split(":");
		String[] anotherPlayer = players[(orderNumber+1)%2].split(":");
		
		final String[] args = {"OK", ""};

		final String content = "<html>" + avatarPlayer[0].getUserName() + ":<br>" + "Stones = " + thisPlayer[0] + ", BigStones = " + thisPlayer[1] + ", Vay " + thisPlayer[2] + " Stones<br>" +
										  avatarPlayer[1].getUserName() + ":<br>" + "Stones = " + anotherPlayer[0] + ", BigStones = " + anotherPlayer[1] + ", Vay " + anotherPlayer[2] + " Stones<br>";
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				//MessageBox box = new MessageBox(GuiPlay.this, args, content);
				//pnEffect.add(box);
				pnEffect.repaint();
				while(args[1].equals("")) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				((ProcessorGuiPlay) processor).resetGame();
			}
		}).start();
	}
}
