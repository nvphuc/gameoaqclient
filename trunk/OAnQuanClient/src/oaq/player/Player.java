package oaq.player;

import javax.swing.ImageIcon;

public class Player {
	
	private String username;	
	private ImageIcon avatar ;
	private int credits;

	public Player(){
		username = "";
		avatar = null; 	
		credits = 0;
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

	public int getCredit() {
		return credits;
	}

	public void setCredit(int credits) {
		this.credits = credits;
	}
}