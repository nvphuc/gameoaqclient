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
import javax.swing.JTextField;

import oaq.game.Game;
import oaq.gui.component.MyButton;
import oaq.processor.ProcessorGuiEdit;

public class GuiEdit extends Gui {

	private JLabel lbUsername, lbPassword;
	private JTextField tfUsername, tfPass;
	private JButton btUpdateUN, btUpdateP, btUpdateA, btAvatar, btBack;
	private JPanel pnAvatar, pnUserName, pnPass;
	public ImageIcon image;
	
	public GuiEdit(Game game, Point location) {
		super(game, location, "BackGroundWait");
		processor = new ProcessorGuiEdit(this);
		setGui();
	}

	@Override
	public void setGui() {

		// Tap panel chua username
		pnUserName = new JPanel();
		pnUserName.setBounds(550, 120, 310, 80);
		pnUserName.setLayout(null);
		pnUserName.setOpaque(false);

		JLabel lbUsername = new JLabel("User Name");
		lbUsername.setForeground(Color.green);
		lbUsername.setFont(new Font("Serif", Font.ITALIC | Font.BOLD, 15));
		lbUsername.setBounds(0, 0, 100, 40);
		pnUserName.add(lbUsername);

		tfUsername = new JTextField(getGame().getPlayer().getUsername());
		tfUsername.setBounds(110, 0, 200, 40);
		pnUserName.add(tfUsername);

		btUpdateUN = new JButton("UPDATE");
		btUpdateUN.addActionListener(this);
		btUpdateUN.setBounds(210, 50, 100, 30);
		pnUserName.add(btUpdateUN);

		// Tap panel chua pass
		pnPass = new JPanel();
		pnPass.setBounds(550, 300, 310, 80);
		pnPass.setLayout(null);
		pnPass.setOpaque(false);

		JLabel lbPassword = new JLabel("Password");
		lbPassword.setForeground(Color.green);
		lbPassword.setFont(new Font("Serif", Font.ITALIC | Font.BOLD, 15));
		lbPassword.setBounds(0, 0, 100, 40);
		pnPass.add(lbPassword);

		tfPass = new JTextField();
		tfPass.setBounds(110, 0, 200, 40);
		pnPass.add(tfPass);

		btUpdateP = new JButton("UPDATE");
		btUpdateP.addActionListener(this);
		btUpdateP.setBounds(210, 50, 100, 30);
		pnPass.add(btUpdateP);
		
		// Tao panel Avatar
		pnAvatar = new JPanel();
		pnAvatar.setBounds(150, 100, 300, 340);
		pnAvatar.setLayout(null);
		pnAvatar.setOpaque(false);

		image = game.getPlayer().getAvatar();
		btAvatar = new JButton(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		btAvatar.setBackground(Color.ORANGE);
		btAvatar.addActionListener(this);
		btAvatar.setBounds(0, 0, 300, 300);
		pnAvatar.add(btAvatar);
		
		btUpdateA = new JButton("UPDATE");
		btUpdateA.addActionListener(this);
		btUpdateA.setBounds(100, 310, 100, 30);
		pnAvatar.add(btUpdateA);
		
		// Tao btBack
		btBack = new MyButton("leftBt", "leftBt_Over", "leftBt");
		btBack.addActionListener(this);
		btBack.setBounds(20, 20, 100, 40);
		
		// Gan vao pnMain
		pnMain.add(pnAvatar);
		pnMain.add(pnUserName);
		pnMain.add(pnPass);
		pnMain.add(btBack);
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ProcessorGuiEdit processor = (ProcessorGuiEdit) this.processor;
		
		if (e.getSource() == btAvatar) {
			processor.getFileImage();
		}
		
		if (e.getSource() == btUpdateA) {
			processor.sendUpdateAvatar();
		}
		
		if (e.getSource() == btBack) {
			processor.sendBack();
		}
		
		if (e.getSource() == btUpdateUN) {
			processor.sendUpdateUserName(tfUsername.getText());
		}
		
		if (e.getSource() == btUpdateP) {
			processor.sendUpdatePassword(tfPass.getText());
		}
		
	}
	
	public void repaintAvatar() {
		btAvatar.repaint();
	}

}
