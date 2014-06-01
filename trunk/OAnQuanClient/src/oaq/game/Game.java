package oaq.game;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import oaq.connector.Connector;
import oaq.gui.GuiLogin;
import oaq.player.Player;
import oaq.sound.SoundManager;

public class Game {

	private Connector connector;
	private Player player;
	public SoundManager soundManager;
	
	public Game() {
		connector = new Connector();
		player = new Player();
		soundManager = new SoundManager("sounds/background.wav");
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
	
	public void reset() {
		connector = new Connector();
		player = new Player();
		soundManager = new SoundManager("sounds/background.wav");
	}
	
	public static void main(String[] args) {
		Game game = new Game();
	}
}