package oaq.processor;

import javax.swing.JOptionPane;

import oaq.gui.*;
import oaq.gui.component.MyDialog;

public class ProcessorGuiLogin extends Processor {

	public ProcessorGuiLogin(Gui gui) {
		super(gui);
	}

	public void login(String userName, String pass) {
		GuiLogin gui = (GuiLogin) this.gui;
		if (!userName.equals("") && !pass.equals("")) {
			if (getConnector().connect()) {
				String message = "Login@" + userName + ":" + pass;
				getConnector().sendMessage(message);
				message = getConnector().receiveMessage();
				if (message.equals("OK")) {
					getPlayer().setUsername(userName);
					getPlayer().setCredit(Integer.parseInt(getConnector().receiveMessage()));
					getPlayer().setAvatar(getConnector().receiveImage());
					new GuiWaitingRoom(gui.getGame(), getGuiLocation());
					gui.dispose();
				} else {
					getConnector().disconnect();
					String[] args = {"OK"};
					new MyDialog().showMessage(gui, "", "Đăng nhập thất bại", args);
				}
			} else {
				String[] args = {"OK"};
				new MyDialog().showMessage(gui, "", "Không kết nối được với Server", args);
			}
		} else {
			String[] args = {"OK"};
			new MyDialog().showMessage(gui, "", "Phải nhập đầy đủ username và password", args);
		}
	}

	/* Ham xu ly button Register */
	public void register() {
		new GuiRegister(gui.getGame(), getGuiLocation());
		gui.dispose();
	}
}