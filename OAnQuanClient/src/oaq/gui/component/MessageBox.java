package oaq.gui.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.tools.JavaFileManager.Location;

import oaq.gui.Gui;

public class MessageBox extends JPanel implements ActionListener {

	private Gui gui;
	private Image image;
	private int width = 0, height = 0;
	private JLabel lbContent;
	private String[] args;
	private JButton[] buttons;
	
	public MessageBox(Gui gui, String[] arg, String content) {
		this.image = (new ImageIcon("images/BackGroundMessageBox.png")).getImage();
		this.gui = gui;
		this.setLayout(null);
		this.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		// Tao panel
		JPanel pnContain = new JPanel();
		pnContain.setLayout(null);
		pnContain.setOpaque(false);
		
		JPanel pnButton = new JPanel();
		pnButton.setLayout(null);
		pnButton.setOpaque(false);
		
		// Label content
		lbContent = new JLabel(content);
		lbContent.setFont(new Font("Serif", Font.ITALIC | Font.BOLD, 15));
		/*String html1 = "<html><body "
				+ "style='font-family: Serif; font-style: italic; font-size: 13px; padding: 0px; margin: 0px;"
				+ " width: ";
		String html2 = "px'>";
		String html3 = "</body></html>";		
		lbContent = new JLabel(html1 + "200" + html2 + content + html3);*/
		lbContent.setForeground(Color.green);
		int lbWidth = lbContent.getPreferredSize().width;
		int lbHeight = lbContent.getPreferredSize().height;
		lbContent.setBounds(0, 0, lbWidth, lbHeight);	
		pnContain.add(lbContent);
		
		// Tao cac button
		int btWidth = 0;
		this.args = arg;	
		
		buttons = new JButton[args.length -1];		
		for(int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton(this.args[i]);
			buttons[i].addActionListener(this);
			if(btWidth < buttons[i].getPreferredSize().width) {
				btWidth = buttons[i].getPreferredSize().width;
			}
		}		
		int btHeight = buttons[0].getPreferredSize().height;
		
		// Tao pnButton
		int pnBTWidth = 0;
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].setBounds(pnBTWidth, 0, btWidth, btHeight);
			pnBTWidth += btWidth + 15;
		}	
		pnBTWidth -= 15;
		int pnBTHeight = btHeight;	
		for(int i = 0; i < buttons.length; i++) {
			pnButton.add(buttons[i]);
		}			
		int pnConWidth = 0;
		if(lbWidth > pnBTWidth) {
			pnConWidth = lbWidth;
			pnButton.setBounds((lbWidth - pnBTWidth)/2, lbHeight + 20, pnBTWidth, pnBTHeight);
		} 
		else {
			pnConWidth = pnBTWidth;
			pnButton.setBounds(0, lbHeight + 20, pnBTWidth, pnBTHeight);
		}
		pnContain.add(pnButton);
		
		int pnConHeight = lbHeight + pnBTHeight + 20;	
		
		height = (int) (pnConHeight / 0.71);
		width = (int) (pnConWidth / 0.72);
		pnContain.setBounds((width - pnConWidth)/2, height * 14 / 100, pnConWidth, pnConHeight);		
		this.add(pnContain);
		
		this.setBounds((1000-width)/2, (550 - height)/2 , width,  height);		
		
		disableAllButton(gui.getMainPane());
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		setOpaque(false);
		super.paintComponent(g);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < buttons.length; i++) {
			if(e.getSource() == buttons[i]) {
				args[args.length-1] = i+"";
				enableAllButton(gui.getMainPane());
				getParent().remove(this);
				gui.getPaneEffect().repaint();
			}
		}
	}
	
	void disableAllButton(Container container) {
		for(Component component : container.getComponents()){
			if (component instanceof JButton) {
				component.setEnabled(false);
			}
			else if(component instanceof Container) {
				disableAllButton((Container) component);
			}
		}		
	}
	
	void enableAllButton(Container container) {
		for(Component component : container.getComponents()){
			if (component instanceof JButton) {
				component.setEnabled(true);
			}
			else if(component instanceof Container) {
				enableAllButton((Container) component);
			}
		}		
	}
}
