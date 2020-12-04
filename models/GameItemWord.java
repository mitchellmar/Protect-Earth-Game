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

public class GameItemWord extends JComponent {
    
    // Declare final variables
    private static final long serialVersionUID = 1L;
    private static final String ASTEROID_IMG = "asteroid1.png";
    
    // Item components
    private BufferedImage asteroid;
    private Random random = new Random();

    // Item states
    private String text;
    private int strX;
    private int move;

    public GameItemWord() {}
    public GameItemWord(String t) {
        super();

        try {
            asteroid = ImageIO.read(new File(ASTEROID_IMG));
        } catch (IOException ext) {}

        this.text = t;
        this.strX = 0;
        this.move = random.nextInt(4) + 1;
        setPreferredSize(new Dimension(120, 120));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font font = new Font("default", Font.BOLD, 12);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawImage(asteroid, 0, 0, 120, 120, null);
        g.drawString(text, 40, 60);
    }

    public void setStrX(int newStrX) {
        this.strX = newStrX;
    }

    public void setStrX() {
        this.strX += this.move;
    }

    public int getStrX() {
        return this.strX;
    }

    public String getText() {
        return this.text;
    }
}
