package models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class GameItemTyped extends JComponent {

	// Declare final variables
	private static final long serialVersionUID = 1L;

	// Item states
	private String typed;
	
	public GameItemTyped() {}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GREEN);
        g.drawString("Typed word: " + typed, 10, getHeight() - 10);
	}
	
	public void setTyped(String s) {
		this.typed = s;
	}
	
	public String getTyped() {
		return this.typed;
	}
}
