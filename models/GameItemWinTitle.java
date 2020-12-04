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

public class GameItemWinTitle extends JComponent {

	// Declare final variables
	private static final long serialVersionUID = 1L;
	
	// Item states
	private int score;
	
	public GameItemWinTitle() {}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.drawString("YOU WON! THANK YOU FOR PROTECTING EARTH!", 200, 200);
        g.setColor(Color.RED);
        g.drawString("YOUR SCORE WAS " + score, 200, 250);
        g.setColor(Color.GREEN);
        g.drawString("HIT SPACEBAR TO RESTART GAME!", 200, 300);
		
	}
	
	public void setScore(int finalScore) {
		this.score=finalScore;
	}
	
}
