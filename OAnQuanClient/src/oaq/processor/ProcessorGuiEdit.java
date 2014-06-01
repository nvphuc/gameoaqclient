package oaq.processor;

import java.awt.FileDialog;
import java.awt.Frame;

import javax.swing.ImageIcon;

import oaq.gui.Gui;
import oaq.gui.GuiEdit;
import oaq.gui.GuiWaitingRoom;
import oaq.gui.component.MyDialog;
import oaq.gui.component.MyInputDialog;

public class ProcessorGuiEdit extends Processor {

	private String strFilePath;

	public ProcessorGuiEdit(Gui gui) {
		super(gui);
		strFilePath = null;
	}

	private String showDialog() {
		FileDialog fd = new FileDialog(new Frame(), "Select File...",
				FileDialog.LOAD);
		fd.setFile("*.png");
		fd.setVisible(true);
		if (fd.getFile() != null) {
			return fd.getDirectory() + fd.getFile();
		}
		return null;
	}

	public void getFileImage() {
		GuiEdit gui = (GuiEdit) this.gui;
		strFilePath = showDialog();
		if (strFilePath != null) {
			gui.image = new ImageIcon(strFilePath);
			gui.repaintAvatar();
		}
	}

	public void sendUpdateAvatar() {
		if (strFilePath != null) {
			getConnector().sendMessage("UpdateAvatar");
			getConnector().sendImage(strFilePath);
			String result = getConnector().receiveMessage();
			if (result.equals("OK")) {
				GuiEdit gui = (GuiEdit) this.gui;
				String[] bt = { "XÁC NHẬN" };
				new MyDialog()
						.showMessage(gui, "", "ĐỔI AVATAR THÀNH CÔNG", bt);
				getGame().getPlayer().setAvatar(gui.image);
			} else {
				String[] bt = { "XÁC NHẬN" };
				new MyDialog().showMessage(gui, "", "ĐỔI AVATAR THẤT BẠI", bt);
			}
			strFilePath = null;
		}
	}

	public void sendBack() {
		getConnector().sendMessage("BackWaitingRoom");
		String result = getConnector().receiveMessage();
		if (result.equals("RSBack")) {
			new GuiWaitingRoom(getGame(), getGuiLocation());
			gui.dispose();
		} else {
			String[] bt = { "XÁC NHẬN" };
			new MyDialog().showMessage(gui, "", "CÓ LỖI", bt);
		}
	}

	public void sendUpdateUserName(String newUserName) {
		if (!newUserName.equals(getPlayer().getUsername())) {
			MyInputDialog inputDialog = new MyInputDialog();
			String pass2 = inputDialog.showMessage(gui, "Inform",
					"Nhập mật khẩu bảo mật (Pass 2): ");
			getConnector().sendMessage(
					"UpdateUserName@" + newUserName + ":" + pass2);
			String result = getConnector().receiveMessage();
			if (result.equals("OK")) {
				getPlayer().setUsername(newUserName);
				String[] bt = { "XÁC NHẬN" };
				new MyDialog().showMessage(gui, "", "ĐỔI USERNAME THÀNH CÔNG",
						bt);

			} else {
				String[] bt = { "XÁC NHẬN" };
				new MyDialog()
						.showMessage(gui, "", "ĐỔI USERNAME THẤT BẠI", bt);
			}
		}
	}

	public void sendUpdatePassword(String newPass) {
		MyInputDialog inputDialog = new MyInputDialog();
		String pass2 = inputDialog.showMessage(gui, "Inform",
				"Nhập mật khẩu bảo mật (Pass 2): ");
		getConnector().sendMessage(
				"UpdatePass@" + newPass + ":" + pass2);
		String result = getConnector().receiveMessage();
		if (result.equals("OK")) {
			String[] bt = { "XÁC NHẬN" };
			new MyDialog().showMessage(gui, "", "ĐỔI PASSWORD THÀNH CÔNG", bt);

		} else {
			String[] bt = { "XÁC NHẬN" };
			new MyDialog().showMessage(gui, "", "ĐỔI PASSWORD THẤT BẠI", bt);
		}
	}
}
