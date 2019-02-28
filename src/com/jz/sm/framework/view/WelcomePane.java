package com.jz.sm.framework.view;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class WelcomePane extends JPanel{
	private ImageIcon icon = null;
	
	public WelcomePane() {
		this.icon = new ImageIcon("img/background.JPG");
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(this.icon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
	
}
