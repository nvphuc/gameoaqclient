package oaq.gui.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import oaq.gui.Gui;

public class MyDialog {

	private int choice = -1;
	private String[] args;
	private JButton[] buttons;
	private JPanel pnMain;
	private Image image;
	private int width = 0, height = 0;

	public int showMessage(Gui gui, String title, String content, String[] args) {

		// Tao panel
		JPanel pnContain = new JPanel();
		pnContain.setLayout(null);
		pnContain.setOpaque(false);

		JPanel pnButton = new JPanel();
		pnButton.setLayout(null);
		pnButton.setOpaque(false);

		// Label content
		JLabel lbContent = new JLabel(content);
		lbContent.setFont(new Font("Serif", Font.ITALIC | Font.BOLD, 15));
		lbContent.setForeground(Color.green);
		int lbWidth = lbContent.getPreferredSize().width;
		int lbHeight = lbContent.getPreferredSize().height;
		lbContent.setBounds(0, 0, lbWidth, lbHeight);
		pnContain.add(lbContent);

		// Tao cac button
		int btWidth = 0;
		this.args = args;

		buttons = new JButton[args.length];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton(this.args[i]);
			buttons[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < buttons.length; i++) {
						JButton button = (JButton) e.getSource();
						if (button == buttons[i]) {
							choice = i;
							SwingUtilities.getWindowAncestor(button).dispose();	
						}
					}			
				}
			});
			if (btWidth < buttons[i].getPreferredSize().width) {
				btWidth = buttons[i].getPreferredSize().width;
			}
		}
		int btHeight = buttons[0].getPreferredSize().height;

		// Tao pnButton
		int pnBTWidth = 0;
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setBounds(pnBTWidth, 0, btWidth, btHeight);
			pnBTWidth += btWidth + 15;
		}
		pnBTWidth -= 15;
		int pnBTHeight = btHeight;
		for (int i = 0; i < buttons.length; i++) {
			pnButton.add(buttons[i]);
		}
		int pnConWidth = 0;
		if (lbWidth > pnBTWidth) {
			pnConWidth = lbWidth;
			pnButton.setBounds((lbWidth - pnBTWidth) / 2, lbHeight + 20,
					pnBTWidth, pnBTHeight);
		} else {
			pnConWidth = pnBTWidth;
			pnButton.setBounds(0, lbHeight + 20, pnBTWidth, pnBTHeight);
		}
		pnContain.add(pnButton);

		int pnConHeight = lbHeight + pnBTHeight + 20;

		height = (int) (pnConHeight / 0.71);
		width = (int) (pnConWidth / 0.72);
		pnContain.setBounds((width - pnConWidth) / 2, height * 14 / 100,
				pnConWidth, pnConHeight);
		
		
		image = (new ImageIcon("images/BackGroundMessageBox.png")).getImage();
		pnMain = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		pnMain.setLayout(null);
		pnMain.setBorder(new BevelBorder(BevelBorder.RAISED));
		pnMain.add(pnContain);
		pnMain.setPreferredSize(new Dimension(width, height));
        
        JDialog dialog = new JDialog();
        dialog.setUndecorated(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.setTitle(title);
        dialog.getContentPane().add(pnMain);
        dialog.pack();
        dialog.setLocationRelativeTo(gui);
        dialog.setVisible(true);
		
		return choice;
	}
}
