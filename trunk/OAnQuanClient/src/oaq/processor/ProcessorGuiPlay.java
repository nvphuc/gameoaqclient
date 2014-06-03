package oaq.processor;

import oaq.connector.InforPlayer;
import oaq.connector.InforTable;
import oaq.gui.Gui;
import oaq.gui.GuiPlay;
import oaq.gui.GuiWaitingRoom;
import oaq.gui.component.Avatar;
import oaq.gui.component.Cell;
import oaq.gui.component.MyDialog;
import oaq.player.Player;

public class ProcessorGuiPlay extends Processor implements Runnable {

	private int orderNumber;
	private int benRaiquan;
	private boolean isRunning;
	
	private boolean isPlaying;
	public boolean isMovingStones;
	public int selectedCell, direction;

	public ProcessorGuiPlay(Gui gui, int orderNumber) {
		super(gui);
		this.orderNumber = orderNumber;
		isRunning = true;
		isPlaying = false;
		isMovingStones = false;
		selectedCell = -1;
		direction = 0;
		benRaiquan = -1;
	}

	@Override
	public void run() {
		while (isRunning) {
			String message = getConnector().receiveMessage();
			System.out.println("PlayNhan: " + message);
			String[] args = message.split("@");
			String[] data;

			switch (args[0]) {

			case "InforTable":
				InforTable inforTable = getConnector().receiveInforTable();
				displayPlayers(inforTable);
				break;

			case "Chat":
				((GuiPlay) gui).txtContent.append(args[1] + "\n");
				break;

			case "Ready":
				displayReady(Integer.parseInt(args[1]));
				break;

			case "Move":
				moveStones(args[1]);
				break;

			case "StartGame":
				((GuiPlay) gui).showStartGame();
				initGame();
				break;
				
			case "Turn":
				showTurn(Integer.parseInt(args[1]));
				break;
				
			case "HideReady":
				hideReady();
				break;
				
			case "RequestRaidan":
				showRequestRaidan(Integer.parseInt(args[1]));
				break;
				
			case "Raidan":
				int index = Integer.parseInt(args[1]);
				if(index == orderNumber) {
					benRaiquan = 0;
				}
				else {
					benRaiquan = 1;
				}				
				showRaidan();
				break;
				
			case "GameResult":
				data = args[1].split(":");
				gameResult(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
				break;
			
			case "Report":
				((GuiPlay) gui).showReport(args[1], orderNumber);
				break;
				
			case "RSLeaveTable":
				leaveTable(Integer.parseInt(args[1]));
				break;
				
			case "AnotherPlayerLeave":
				//showAnotherPlayerLeaveTable();
				break;
				
			default:
				isRunning = false;
				break;
			}
		}
	}
	
	
	

	private void gameResult(int winner, int bet) {
		GuiPlay gui = (GuiPlay) this.gui;
		isPlaying = false;
		
		if(winner == orderNumber) {
			gui.showWinner(0);
			getPlayer().setCredit(getPlayer().getCredit() + bet);
			gui.showCredit("+ " + bet);
		}
		else {
			if(winner == 2) {
				gui.showWinner(2);
			}			
			else {
				gui.showWinner(1);
				getPlayer().setCredit(getPlayer().getCredit() - bet);
				gui.showCredit("- " + bet);
			}
		}		
	}

	private void showAnotherPlayerLeaveTable() {
		GuiPlay gui = (GuiPlay) this.gui;
		String msg = gui.avatarPlayer[1].getName() + " đã rời bàn";
		gui.showMessage(msg);
	}

	private void leaveTable(int amount) {
		int credits = getPlayer().getCredit() + amount;
		getPlayer().setCredit(credits);
		
		new GuiWaitingRoom(getGame(), getGuiLocation());
		gui.dispose();	
	}

	private void showRaidan() {
		final GuiPlay gui = (GuiPlay) this.gui;
		new Thread(new Runnable() {
			@Override
			public void run() {
				int j = benRaiquan;
				if(j == 1)
					j = 6;
				
				for(int i = 0; i < 5; i++) {
					if(gui.stores[benRaiquan].getStonesNumber() != 0) {
						gui.stores[benRaiquan].removeStone();
						gui.cells[j].addStones(1);
						j++;
					}
					else {
						if(gui.stores[(benRaiquan+1)%2].getStonesNumber() != 0) {
							//Muon dan
							gui.stores[(benRaiquan+1)%2].removeStone();
							gui.cells[j].addStones(1);
							j++;
						}
						else {
							gui.stores[benRaiquan].setRaidan(false);
							benRaiquan = -1;
							return;
						}
					}
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				gui.stores[benRaiquan].setRaidan(false);
				benRaiquan = -1;
			}
		}).start();
	}

	private void showRequestRaidan(int index) {
		GuiPlay gui = (GuiPlay) this.gui;
		if(orderNumber == index) {
			gui.stores[0].setRaidan(true);
		}
		else {
			gui.stores[1].setRaidan(true);
		}		
	}
	
	public void raiDan() {
		getConnector().sendMessage("Raidan@" + orderNumber);	
	}

	public void sendLeaveTable() {
		if(isPlaying) {
			String[] bts = {"ĐỒNG Ý", "HỦY"};
			MyDialog dialog = new MyDialog();
			int choice = dialog.showMessage(gui, "", "Nếu rời phòng bạn sẽ thua tiền cược", bts);
			if(choice == 0) {
				getConnector().sendMessage("LeaveTable");
			}
		}
		else {
			getConnector().sendMessage("LeaveTable");
		}
	}

	public void invite() {
		// TODO Auto-generated method stub

	}

	// xong
	// =============================================================================

	private void hideReady() {
		GuiPlay gui = (GuiPlay) this.gui;
		gui.avatarPlayer[0].setReady(false);
		gui.avatarPlayer[1].setReady(false);
	}

	private void showTurn(int index) {
		GuiPlay gui = (GuiPlay) this.gui;
		if(orderNumber == index) {
			gui.stores[0].isTurn = true;
			gui.stores[0].refresh();
			gui.stores[1].isTurn = false;
			gui.stores[1].refresh();
		}
		else {
			gui.stores[1].isTurn = true;
			gui.stores[1].refresh();
			gui.stores[0].isTurn = false;
			gui.stores[0].refresh();
		}
		
	}

	public void sendMove(int directionMove) {
		GuiPlay gui = (GuiPlay) this.gui;
		if (gui.allowToClick()) {
			if (getSelectedCell() > -1) {
				selectedCell = getSelectedCell();
				direction = directionMove;
				isMovingStones = true;
				String msg = "Move@" + orderNumber + ":" + selectedCell + ":" + direction;
				getConnector().sendMessage(msg);
			}
		}
	}

	private void moveStones(String args) {
		GuiPlay gui = (GuiPlay) this.gui;
		isMovingStones = true;
		String[] data = args.split(":");
		int index = Integer.parseInt(data[0]);
		selectedCell = Integer.parseInt(data[1]);
		direction = Integer.parseInt(data[2]);
		if (index != orderNumber) {
			selectedCell += 6;
			gui.cells[selectedCell].isSelected = true;
			gui.cells[selectedCell].refresh();
		}
		showMoveStones();
	}
	
	public void showMoveStones() {
		final GuiPlay gui = (GuiPlay) this.gui;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int cur = selectedCell;
				int amount = gui.cells[cur].clearStones();
				while (amount > 0) {
					cur = getNextCell(cur);
					gui.cells[cur].addStones(1);
					amount--;
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				// dung tai bigcell khac 0
				int next = getNextCell(cur);
				if ((next == 5 || next == 11)
						&& gui.cells[next].getStonesNumber() != 0) {
					resetTurn();
					getConnector().sendMessage("FinishMove");
					//neu = 0
				} else if (gui.cells[next].getStonesNumber() == 0) {
					while ((next != cur)
							&& (gui.cells[next].getStonesNumber() == 0)) {
						next = getNextCell(next);
						if (gui.cells[next].getStonesNumber() == 0)
							break;
						else {
							if (gui.stores[0].isTurn) {
								if ((next == 5 || next == 11)
										&& gui.cells[next].isBigStone) {
									gui.stores[0].addBigStones(1);
								}
								gui.stores[0].addStones(gui.cells[next].clearStones());
							} else {
								if ((next == 5 || next == 11)
										&& gui.cells[next].isBigStone) {
									gui.stores[1].addBigStones(1);
								}
								gui.stores[1].addStones(gui.cells[next].clearStones());
							}
							next = getNextCell(next);
						}
					}
					resetTurn();
					getConnector().sendMessage("FinishMove");
				} else {
					selectedCell = next;
					gui.cells[next].select(true);
					showMoveStones();
				}
			}
		}).start();
	}
	
	private void resetTurn() {
		selectedCell = -1;
		direction = 0;
		isMovingStones = false;
	}
	
	public int getNextCell(int cell) {
		cell += direction;
		if (cell > 11)
			cell = 0;
		if (cell < 0)
			cell = 11;
		return cell;
	}
	
	private void displayReady(int index) {
		GuiPlay gui = (GuiPlay) this.gui;
		gui.avatarPlayer[(2 + index - orderNumber) % 2].setReady(true);
	}

	public void sendReady() {
		GuiPlay gui = (GuiPlay) this.gui;
		getConnector().sendMessage("Ready");
		gui.btReady.setEnabled(false);
	}

	private void displayPlayers(InforTable inforTable) {
		GuiPlay gui = (GuiPlay) this.gui;
		for (int i = 0; i < 2; i++) {
			InforPlayer inforPlayer = inforTable.getInforPlayers()[i];
			if (inforPlayer != null) {
				gui.avatarPlayer[(2 + i - orderNumber) % 2]
						.setUserName(inforPlayer.getUserName());
				gui.avatarPlayer[(2 + i - orderNumber) % 2]
						.setCredit(inforPlayer.getCredit());
				gui.avatarPlayer[(2 + i - orderNumber) % 2]
						.setImgAvatar(inforPlayer.getAvatar());
				if (inforPlayer.getStatus() == 1) {
					gui.avatarPlayer[(2 + i - orderNumber) % 2].setReady(true);
				}
			}
		}
	}

	public void sendChat() {
		GuiPlay gui = (GuiPlay) this.gui;
		String content = gui.txtChat.getText();
		if (!content.equals("")) {
			String msg = "Chat@" + getPlayer().getUsername() + ": " + content;
			getConnector().sendMessage(msg);
			gui.txtChat.setText("");
		}
	}

	private void initGame() {
		isPlaying = true;
		isMovingStones = false;
		selectedCell = -1;
		direction = 0;
		benRaiquan = -1;
		GuiPlay gui = (GuiPlay) this.gui;
		for(int i=0; i<12; i++){
			gui.cells[i].initStones();
		}
		for(int i=0; i<2; i++){
			gui.stores[i].initStones();
		}
	}
	
	public void resetGame() {
		isMovingStones = false;
		selectedCell = -1;
		direction = 0;
		benRaiquan = -1;
		GuiPlay gui = (GuiPlay) this.gui;
		for(int i=0; i<12; i++){
			gui.cells[i].init();
		}
		for(int i=0; i<2; i++){
			gui.stores[i].initStones();
		}
	}

	private int getSelectedCell() {
		GuiPlay gui = (GuiPlay) this.gui;
		for (int i = 0; i < 5; i++) {
			Cell cell = gui.cells[i];
			if (cell.isSelected == true && cell.getStonesNumber() > 0) {
				return cell.getID();
			}
		}
		return -1;
	}

}
