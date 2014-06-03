package oaq.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import oaq.game.Game;
import oaq.gui.component.MyButton;
import oaq.gui.component.MyJScrollPane;
import oaq.gui.component.MyPanel;
import oaq.gui.component.Table;
import oaq.processor.ProcessorGuiWaitingRoom;

public class GuiWaitingRoom extends Gui {

	private JPanel pnPlayer;
	public JPanel pnTables;
	public MyJScrollPane scrollPaneTable;

	public Table[] tables = new Table[100];
	private JButton btCreateTable, btPlayRight, btUpdate, btLogout, btEdit, btSetting;

	public JLabel lbAvatar, lbUsername, lbImgMoney, lbMoney;

	public GuiWaitingRoom(Game game, Point location) {
		super(game, location, "background07");
		setTitle("WaitRoom");		
		processor = new ProcessorGuiWaitingRoom(this);
		setGui();
		Thread thread = new Thread((ProcessorGuiWaitingRoom) processor);
		thread.start();	
	}

	@Override
	public void setGui() {

		// Tao pane Player
		pnPlayer = new JPanel();
		pnPlayer.setLayout(null);
		pnPlayer.setOpaque(false);
		pnPlayer.setBorder(new BevelBorder(BevelBorder.RAISED));
		pnPlayer.setBounds(44, 15, 300, 100);

		lbAvatar = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(game.getPlayer().getAvatar().getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		lbAvatar.setBounds(10, 10, 80, 80);
		pnPlayer.add(lbAvatar);

		lbUsername = new JLabel(game.getPlayer().getUsername());
		lbUsername.setFont(new Font("Serif", Font.ITALIC | Font.BOLD, 15));
		lbUsername.setForeground(Color.yellow);
		lbUsername.setBounds(100, 20, lbUsername.getPreferredSize().width,
				lbUsername.getPreferredSize().height);
		pnPlayer.add(lbUsername);

		lbImgMoney = new JLabel(new ImageIcon("images/lbMoney.png"));
		lbImgMoney.setBounds(100, 60, 26, 16);
		pnPlayer.add(lbImgMoney);

		lbMoney = new JLabel(game.getPlayer().getCredit() + "");
		lbMoney.setFont(new Font("Serif", Font.ITALIC | Font.BOLD, 15));
		lbMoney.setForeground(Color.yellow);
		lbMoney.setBounds(135, 57, lbMoney.getPreferredSize().width,
				lbMoney.getPreferredSize().height);
		pnPlayer.add(lbMoney);

		// Tao cac button
		btCreateTable = new MyButton("btCreateTable", "btCreateTable_MouseOver", "btCreateTable");
		btCreateTable.addActionListener(this);
		btCreateTable.setBounds(750, 200,
				btCreateTable.getPreferredSize().width,
				btCreateTable.getPreferredSize().height);

		btPlayRight = new MyButton("btPlayRight", "btPlayRight_MouseOver", "btPlayRight");
		btPlayRight.addActionListener(this);
		btPlayRight.setBounds(750, 350, btPlayRight.getPreferredSize().width,
				btPlayRight.getPreferredSize().height);

		btUpdate = new MyButton("btUpdate", "btUpdate_MouseOver", "btUpdate");
		btUpdate.addActionListener(this);
		btUpdate.setBounds(55, 510, btUpdate.getPreferredSize().width,
				btUpdate.getPreferredSize().height);

		btEdit = new MyButton("btEdit", "btEdit_Over", "btEdit");
		btEdit.addActionListener(this);
		btEdit.setBounds(740, 15, btEdit.getPreferredSize().width,
				btEdit.getPreferredSize().height);
		
		btSetting = new MyButton("btSet", "btSet_Over", "btSet");
		btSetting.addActionListener(this);
		btSetting.setBounds(795, 15, btSetting.getPreferredSize().width,
				btSetting.getPreferredSize().height);
		
		btLogout = new MyButton("btLogout", "btLogout_Over", "btThoat");
		btLogout.addActionListener(this);
		btLogout.setBounds(850, 15, btLogout.getPreferredSize().width,
				btLogout.getPreferredSize().height);

		// Tao pane Tables
		pnTables = new MyPanel("BackGroundBox");
		pnTables.setLayout(null);
		pnTables.setOpaque(false);
		pnTables.setBounds(45, 160, 606, 350);

		scrollPaneTable = new MyJScrollPane();
		scrollPaneTable.setBounds(10, 22, 586, 319);
		pnTables.add(scrollPaneTable);

		// Gan cac thanh phan vao pnMain
		pnMain.add(pnPlayer);
		pnMain.add(pnTables);
		pnMain.add(btCreateTable);
		pnMain.add(btPlayRight);
		pnMain.add(btUpdate);
		pnMain.add(btEdit);
		pnMain.add(btSetting);
		pnMain.add(btLogout);

		((ProcessorGuiWaitingRoom) processor).updateTables();

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
ProcessorGuiWaitingRoom processor = (ProcessorGuiWaitingRoom) this.processor;
		
		if (e.getSource() == btCreateTable) {
			processor.createTable();
			return;
		}
		
		if (e.getSource() == btEdit) {
			processor.editAccount();
			return;
		}

		if (e.getSource() == btLogout) {
			processor.logout();
			return;
		}
		
		if (e.getSource() == btPlayRight) {
			processor.playRight();
			return;
		}
		
		if (e.getSource() == btUpdate) {
			processor.updateTables();
			return;
		}
	}

}