package oaq.player;

import javax.swing.ImageIcon;

public class Player {
	
	private String username;	
	private ImageIcon avatar ;
	private int money;

	public Player(){
		username = "";
		avatar = null; 	
		money = 0;
	}

	public void reset() {
		username = "";
		avatar = null; 	
		money = 0;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ImageIcon getAvatar() {
		return avatar;
	}

	public void setAvatar(ImageIcon avatar) {
		this.avatar = avatar;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}	
}