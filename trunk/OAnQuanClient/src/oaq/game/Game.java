package oaq.game;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import oaq.connector.Connector;
import oaq.gui.GuiLogin;
import oaq.player.Player;

public class Game {

	private Connector connector;
	private Player player;

	public Game() {
		connector = new Connector();
		player = new Player();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point location = new Point((screenSize.width - 1006)/2, (screenSize.height - 640)/2);
		new GuiLogin(this, location);
	}

	public Connector getConnector() {
		return connector;
	}

	public Player getPlayer() {
		return player;
	}
	
	public static void main(String[] args) {
		Game game = new Game();
	}
}