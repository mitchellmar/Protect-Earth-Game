/*
Draws the score, along with getter and setter methods for the score
*/

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

public class GameItemScore extends JComponent {

	// Declare final variables
	private static final long serialVersionUID = 1L;

	// Item states
	private int score;
	
	public GameItemScore() {}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
        g.drawString("Score: " + score, getWidth() - 80, 10);
	}
	
	public void setScore(int newScore) {
		this.score = newScore;
	}
	
	public int getScore() {
		return this.score;
	}
}
