package oaq.processor;

import javax.swing.JOptionPane;

import oaq.gui.*;

public class ProcessorGuiLogin extends Processor {

	public ProcessorGuiLogin(Gui gui) {
		super(gui);
	}

	public void login(String userName, String pass) {
		GuiLogin gui = (GuiLogin) this.gui;
		if (!userName.equals("") && !pass.equals("")) {
			getConnector().connect();
			//msg: "Login@USERNAME:PASS"
			String message = "Login@" + userName + ":" + pass;
			getConnector().sendMessage(message);
			message = getConnector().receiveMessage();
			
			if (message.equals("OK")) {
				getPlayer().setUsername(userName);
				getPlayer().setMoney(Integer.parseInt(getConnector().receiveMessage()));
				getPlayer().setAvatar(getConnector().receiveImage());	
				new GuiWaitingRoom(gui.getGame(), getGuiLocation());
				gui.dispose(); 
			} else {
				getConnector().disconnect();
				JOptionPane.showMessageDialog(getGui(),
						"Đăng nhập thất bại", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			
		} else
			JOptionPane.showMessageDialog(getGui(),
					"Phải nhập đầy đủ username và password", "Error",
					JOptionPane.ERROR_MESSAGE);
	}

	/* Ham xu ly button Register */
	public void register() {
		new GuiRegister(gui.getGame(), getGuiLocation());
		gui.dispose();
	}
}