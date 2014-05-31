package oaq.gui.component;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MyJScrollPane extends JScrollPane {

	private JPanel pnContain;
	
	public MyJScrollPane() {
		pnContain = new JPanel();
		pnContain.setLayout(null);
		pnContain.setOpaque(false);
		
		this.setOpaque(false);
		this.getViewport().setOpaque(false);
		this.getViewport().add(pnContain);
	}
	
	public void setSizeContainer(Dimension dimension) {
		this.getViewport().remove(pnContain);
		pnContain.setPreferredSize(dimension);
		this.getViewport().add(pnContain);
	}
	
	public JPanel getContainer() {
		return pnContain;
	}
}
