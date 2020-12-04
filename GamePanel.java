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
    private String typed = "";
    private ArrayList<Word> words = new ArrayList<>();
    private BufferedImage asteroid1;
    private BufferedImage earth;
    private boolean started = false;
    private boolean ended = false;
    private GameWordPicker pick1 = new GameWordPicker();
    
    class Word {
    	String str = "";
    	int move = 1;
    	int strX = 0;
    }
   
   public GamePanel() {
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
        	words.add(pick1.pick());
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
				
	                	word = pick1.pick();
	                	words.set(i, word);
	                }
                }
                repaint();
            }
        };
	   
        Timer tim = new Timer(DELAY, updater);
        tim.start();
	   
    }
   
   class PacManKeyListener extends KeyAdapter {
	   
       public void keyPressed(KeyEvent event) {
           char keyChar = event.getKeyChar();
	       
           if (!Character.isAlphabetic(keyChar) && keyChar != ' ') {
               return;
           }
	       
           if (keyChar == ' ') {
           	typed = "";
		   
           	if (!started) {
           		started = true;
           	}
		   
           	if (ended) {
           		ended = false;
           		score = 0;
           	}
           } 
	   else {
           	typed += keyChar;
           }
	       
           boolean same = false;
	       
           for (int i = 0; i < words.size(); i++) {
           	Word word = words.get(i);
		   
           	if (typed.equals(word.str)) {
	        		same = true;
           		score += 10;
	            	word = pick1.pick();
	            	words.set(i, word);
	            	
			if (score >= 100) {
	            		ended = true;
	            	}
	        }
           }
	       
           if (same) {
       		typed = "";
           }
	       
           repaint();
       }
   }
}
