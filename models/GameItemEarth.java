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

public class GameItemEarth extends JComponent {
	
	//Declare final variables
	private static final long serialVersionUID = 1L;
	private static final String EARTH_IMG = "earth.png";
	
	//Item components
	private BufferedImage earth;
	
	public GameItemEarth() {
		super();
		
		try {
			earth= ImageIO.read(new File(EARTH_IMG));
		} catch (IOException ext) {}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(earth, getWidth() - 100, 0, earth.getWidth() * getHeight()/earth.getHeight(), getHeight(), null);
	}

}
