package oaq.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import oaq.game.Game;
import oaq.gui.component.HintPasswordField;
import oaq.gui.component.HintTextField;
import oaq.processor.ProcessorGuiRegister;

public class GuiRegister extends Gui {
	
	private JTextField tfUsername, tfPass1, tfConfirmPass1, tfPass2;
	private JButton btOk, btCancel;
	private JPanel pnRegister;
	
	public GuiRegister(Game game, Point location) {
		super(game, location, "BackGroundWait");
		processor = new ProcessorGuiRegister(this);
		setGui();
	}

	@Override
	public void setGui() {
		//ImageIcon icon1 = new ImageIcon("images/niem3.png");
		//ImageIcon icon2 = new ImageIcon("images/niem2.png");

		// Tao panel chua khung register
		pnRegister = new JPanel();
		pnRegister.setBorder(new BevelBorder(BevelBorder.RAISED));
		pnRegister.setBounds(305, 70, 390, 400);
		pnRegister.setLayout(null);
		pnRegister.setOpaque(false);

		// Tao textfield username
		tfUsername = new HintTextField("UserName");
		tfUsername.setBounds(170, 50, 200, 40);
		pnRegister.add(tfUsername);
		
		JLabel lbUsername = new JLabel("User Name");
		lbUsername.setForeground(Color.green);
		lbUsername.setFont(new Font("Serif", Font.ITALIC | Font.BOLD, 15));
		lbUsername.setBounds(20, 50, 150, 40);
		pnRegister.add(lbUsername);
		
		// Tao texfield password
		tfPass1 = new HintPasswordField("Password1");
		tfPass1.setBounds(170, 120, 200, 40);
		pnRegister.add(tfPass1);

		JLabel lbPass1 = new JLabel("Password 1");
		lbPass1.setForeground(Color.green);
		lbPass1.setFont(new Font("Serif", Font.ITALIC | Font.BOLD, 15));
		lbPass1.setBounds(20, 120, 150, 40);
		pnRegister.add(lbPass1);
		
		// tao textfield ConfirmPassword
		tfConfirmPass1 = new HintPasswordField("ConfirmPassword1");
		tfConfirmPass1.setBounds(170, 190, 200, 40);
		pnRegister.add(tfConfirmPass1);

		JLabel lbCfPass1 = new JLabel("Confirm Password 1");
		lbCfPass1.setForeground(Color.green);
		lbCfPass1.setFont(new Font("Serif", Font.ITALIC | Font.BOLD, 15));
		lbCfPass1.setBounds(20, 190, 150, 40);
		pnRegister.add(lbCfPass1);
		
		// tao textfield password2
		tfPass2 = new HintPasswordField("Password2");
		tfPass2.setBounds(170, 260, 200, 40);
		pnRegister.add(tfPass2);

		JLabel lbPass2 = new JLabel("Password 2");
		lbPass2.setForeground(Color.green);
		lbPass2.setFont(new Font("Serif", Font.ITALIC | Font.BOLD, 15));
		lbPass2.setBounds(20, 260, 150, 40);
		pnRegister.add(lbPass2);
		
		// Tao button Ok
		btOk = new JButton("ĐĂNG KÝ");
		//btOk.setIcon(icon1);
		btOk.setBounds(160, 340, 100, 30);
		btOk.addActionListener(this);
		pnRegister.add(btOk);

		// Tao button Cancel
		btCancel = new JButton("HỦY");
		//btCancel.setIcon(icon2);
		btCancel.setBounds(280, 340, 100, 30);
		btCancel.addActionListener(this);
		pnRegister.add(btCancel);

		pnMain.add(pnRegister);

		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		ProcessorGuiRegister processor = (ProcessorGuiRegister) this.processor;
		
		if (e.getSource() == btOk) {
			String userName = tfUsername.getText();
			String pass1 = tfPass1.getText();
			String cfPass1 = tfConfirmPass1.getText();
			String pass2 = tfPass2.getText();		
			processor.register(userName, pass1, cfPass1, pass2);
		}

		if (e.getSource() == btCancel) {
			processor.cancel();
		}
	}
}
