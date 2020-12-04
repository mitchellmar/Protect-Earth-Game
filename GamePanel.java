import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;
import javax.imageio.*;

public class GamePanel extends JPanel {
    private int score = 0;
    private boolean started = false;
    private boolean ended = false;
    
   public TilePanel() {
    	try {
    		asteroid1 = ImageIO.read(new File("asteroid1.png"));
    		earth = ImageIO.read(new File("earth.png"));
    	} catch (IOException e) {
    	}
        setBackground(Color.BLACK);
        addKeyListener(new PacManKeyListener());
        setFocusable(true);
        requestFocusInWindow();
        for (int i = 0; i < 5; i++) {
        	words.add(pick());
        }
        int DELAY = 10;
        ActionListener updater = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	if (!started || ended) {
            		return;            		
            	}
                for (int i = 0; i < words.size(); i++) {
                	Word word = words.get(i);
                	word.strX += word.move;
	                if (word.strX >= getWidth()) {
	                	score -= 5;
		            	if (score <= -50) {
		            		ended = true;
		            	}
	                	word = pick();
	                	words.set(i, word);
	                }
                }
                repaint();
            }
        };
        Timer tim = new Timer(DELAY, updater);
        tim.start();        
    }
}
