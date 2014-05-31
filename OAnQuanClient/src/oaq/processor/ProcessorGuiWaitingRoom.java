package oaq.processor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import oaq.gui.Gui;
import oaq.gui.GuiEdit;
import oaq.gui.GuiLogin;
import oaq.gui.GuiPlay;
import oaq.gui.GuiWaitingRoom;
import oaq.gui.component.MessageBox;
import oaq.gui.component.Table;

public class ProcessorGuiWaitingRoom extends Processor implements Runnable {

	private boolean isRunning;
	
	public ProcessorGuiWaitingRoom(Gui gui) {
		super(gui);
		isRunning = true;
	}

	@Override
	public void run() {
		while (isRunning) {
			String message = getConnector().receiveMessage();
			System.out.println("WaitNhan: " + message);
			String[] args = message.split("@");
			String[] data;
			
			switch (args[0]) {
			
			case "RSCreateTable":
				/*
				 * msg gui: "CreateTable@TableName"
				 * msg nhan: "RSCreateTable@OK:orderNumber|ERROR:NONE"
				 */
				data = args[1].split(":");
				if (data[0].equals("OK")) {
					isRunning = false;
					new GuiPlay(getGame(), getGuiLocation(), Integer.parseInt(data[1]));
					gui.dispose();									
				} else {
					JOptionPane.showMessageDialog(getGui(),"Tên bàn đã tồn tại", "Error",JOptionPane.ERROR_MESSAGE);
				}
				break;
				
			case "RSJoinTable":
				/*
				 * msg nhan: "RSJoinTable@OK:orderNumber|ERROR:NONE"
				 */
				data = args[1].split(":");
				if (data[0].equals("OK")) {
					isRunning = false;
					new GuiPlay(getGame(), getGuiLocation(), Integer.parseInt(data[1]));
					gui.dispose();
				} else {
					JOptionPane.showMessageDialog(getGui(),
							"Bàn đã đầy", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

				break;
				
			case "RSUpdateTables":
				/* 
				 * msg nhan: "RSUpdateTables@NONE|name1:name2:..:namen"
				 */
				String[] tables;
				if (args[1].equals("NONE")) {
					tables = new String[0];
				} else {
					tables = args[1].split(":");
				}
				refreshTables(tables);
				break;

			case "RSPlayRight":
				data = args[1].split(":");
				if (data[0].equals("OK")) {
					new GuiPlay(getGame(), getGuiLocation(), Integer.parseInt(data[1]));
					gui.dispose();
					isRunning = false;
				} else {
					JOptionPane.showMessageDialog(getGui(),
							"Không còn bàn nào trống", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				break;

			default:
				System.out.println("TatWait");
				isRunning = false;
				break;
			}
		}
	}

	public void createTable() {
		String TableName = JOptionPane.showInputDialog(gui, "Nhập tên bàn :",
				"Inform", JOptionPane.INFORMATION_MESSAGE);
		if (TableName != null) {
			while (TableName.equals("") || TableName.length() > 10) {
				String input = JOptionPane.showInputDialog(gui,
						"Nhập tên bàn (Từ 1 đến 10 ký tự):", "Inform",
						JOptionPane.INFORMATION_MESSAGE);
				TableName = input;
				if (TableName == null)
					break;
			}
		}
		if (TableName != null) {
			getConnector().sendMessage("CreateTable@" + TableName);
		}
	}

	public void editAccount() {
		new GuiEdit(getGame(), getGuiLocation());
		gui.dispose();
		isRunning = false;
	}

	public void logout() {
		getConnector().disconnect();
		getPlayer().reset();
		new GuiLogin(getGame(), getGuiLocation());
		gui.dispose();
		isRunning = false;
	}

	public void playRight() {
		getConnector().sendMessage("PlayRight@NONE");
	}
	
	public void updateTables() {
		getConnector().sendMessage("UpdataTables@NONE");
	}

	public void refreshTables(String[] tableNames) {
		// Toa do x, y de ve cac phong len pnTables
		int x = 0;
		int y = 0;

		GuiWaitingRoom gui = (GuiWaitingRoom) this.gui;
		/* Xoa het cac phong da ve len pnTables */
		gui.scrollPaneTable.getContainer().removeAll();
		gui.scrollPaneTable.setSizeContainer(new Dimension(500,
				(tableNames.length / 4) * 100 + 100));
		/* Duyet va ve cac phong luu trong roomnames[] */
		for (int i = 0; i < tableNames.length; i++) {

			/* Khoi tao 1 phong tai toa do x,y voi ten phong la roomnames[i] */
			gui.tables[i] = new Table(tableNames[i]);

			/* Gan su kien click chuot cho phong vua tao */
			final Table tmp = gui.tables[i];
			tmp.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					getConnector().sendMessage("JoinTable@" + tmp.getName());
				}
			});

			/* Ve phong len pnTables */
			tmp.setBounds(x, y, tmp.getPreferredSize().width,
					tmp.getPreferredSize().height);
			gui.scrollPaneTable.getContainer().add(tmp);

			/* Chinh lai toa do ve */
			if ((i + 1) % 4 != 0) { // Moi dong ve toi da 5 room
				x += tmp.getPreferredSize().width + 5;
			} else {
				x = 0;
				y += tmp.getPreferredSize().height + 5;
			}
		}

		/* Repaint lai pnTables */
		gui.scrollPaneTable.getContainer().repaint();
	}

	public void showMessageBox() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String[] a = { "OK", "CANCEL", "" };
				gui.getPaneEffect().add(
						new MessageBox(gui, a, "Thử tạo message box"));
				gui.getPaneEffect().repaint();
				while (a[2].equals("")) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(a[2]);
			}
		}).start();
	}
}
