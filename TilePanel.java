// This class keeps track of a set of colored tiles in a panel.

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;
import javax.imageio.*;

import models.GameItemWord;

public class TilePanel extends JPanel {
    private String typed = "";
    private int score = 0;
    private ArrayList<Word> words = new ArrayList<>();
    private ArrayList<String> totype = new ArrayList<>();
    private Random rand = new Random();
    private BufferedImage asteroid1;
    private BufferedImage earth;
    private boolean started = false;
    private boolean ended = false;

    class Word {
        String str = "";
        int move = 1;
        int strX = 0;
    }

    public TilePanel() {
        setLayout(null);
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
        GameItemWord itemWord = new GameItemWord("HELLO");
        itemWord.setBounds(100, 100, itemWord.getPreferredSize().width, itemWord.getPreferredSize().height);
        this.add(itemWord);
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

    Word pick() {
        Scanner input = null;
        try {
            input = new Scanner(new File("words.txt"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        while (input.hasNextLine()) {
            totype.add(input.nextLine());
        }
        Word word = new Word();
        word.str = totype.get(rand.nextInt(totype.size()));
        word.move = rand.nextInt(4) + 1;
        return word;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(earth, getWidth() - 100, 0, earth.getWidth() * getHeight() / earth.getHeight(), getHeight(), null);
        Font font = new Font("default", Font.BOLD, 12);
        g.setFont(font);
        if (!started) {
            g.setColor(Color.BLUE);
            g.drawString("PROTECT EARTH BY TYPING WORDS ON ASTEROIDS!", 200, 200);
            g.setColor(Color.RED);
            g.drawString("IF THEY HIT EARTH, YOU LOSE POINTS!", 200, 250);
            g.setColor(Color.GREEN);
            g.drawString("PLEASE HIT SPACE TO START GAME!", 200, 300);
            return;
        }

        if (ended) {
            g.setColor(Color.BLUE);
            if (score >= 100) {
                g.drawString("YOU WON! THANK YOU FOR PROTECTING EARTH!", 200, 200);
            } else {
                g.drawString("YOU LOST! PLEASE TRY AGAIN...", 200, 200);
            }
            g.setColor(Color.RED);
            g.drawString("YOUR SCORE WAS " + score, 200, 250);
            g.setColor(Color.GREEN);
            g.drawString("PLEASE HIT SPACE TO START GAME!", 200, 300);
            return;
        }

        FontMetrics metrics = g.getFontMetrics(font);
        g.setColor(Color.WHITE);
        for (int i = 0; i < words.size(); i++) {
            Word word = words.get(i);
            int height = getHeight() * (i + 1) / (words.size() + 1);
            int ast = 120;
            g.drawImage(asteroid1, word.strX - ast / 2, height - ast / 2, ast, ast, null);
            g.drawString(word.str, word.strX - metrics.stringWidth(word.str) / 2, height);
        }
        g.setColor(Color.GREEN);
        g.drawString("Typed word: " + typed, 10, getHeight() - 10);
        g.setColor(Color.RED);
        g.drawString("Score: " + score, getWidth() - 80, 10);
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
            } else {
                typed += keyChar;
            }
            boolean same = false;
            for (int i = 0; i < words.size(); i++) {
                Word word = words.get(i);
                if (typed.equals(word.str)) {
                    same = true;
                    score += 10;
                    word = pick();
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
