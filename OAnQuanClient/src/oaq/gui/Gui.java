package oaq.gui;

import java.awt.Point;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import oaq.game.Game;
import oaq.gui.component.MyPanel;
import oaq.processor.Processor;
import oaq.sound.SoundManager;

public abstract class Gui extends JFrame implements ActionListener {

	protected Game game;
	protected Processor processor;
	protected JPanel pnMain;
	protected JPanel pnEffect;
	
	public Gui(Game game, Point location, String imageBackGround) {
		
		this.game = game;
		
		this.setSize(1006, 640);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocation(location);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pnMain = new MyPanel(imageBackGround);
		pnMain.setLayout(null);
		pnMain.setBounds(0, 0, 1000, 636);
		this.add(pnMain);
		
		pnEffect = new JPanel();
		pnEffect.setLayout(null);
		pnEffect.setOpaque(false);
		pnEffect.setBounds(0, 0, 1000, 636);
		pnMain.add(pnEffect);
	}
		
	public Game getGame(){
		return game;
	}
	
	public JPanel getPaneEffect() {
		return pnEffect;
	}

	public SoundManager getSoundManager() {
		return game.soundManager;
	}
	
	abstract public void setGui();
}
