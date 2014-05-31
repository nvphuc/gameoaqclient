package oaq.processor;

import java.awt.Point;

import oaq.connector.Connector;
import oaq.game.Game;
import oaq.gui.Gui;
import oaq.player.Player;

public abstract class Processor {

	protected Gui gui;

	public Processor(Gui gui) {
		this.gui = gui;
	}

	public Gui getGui() {
		return gui;
	}

	public Game getGame() {
		return gui.getGame();
	}

	public Player getPlayer() {
		return gui.getGame().getPlayer();
	}

	public Connector getConnector() {
		return gui.getGame().getConnector();
	}

	public Point getGuiLocation(){
		return gui.getLocationOnScreen();
	}
}