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
    private ArrayList<GameItemWord> itemWords = new ArrayList<>();
    private ArrayList<String> totype = new ArrayList<>();
    private Random rand = new Random();
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
            earth = ImageIO.read(new File("earth.png"));
        } catch (IOException e) {
        }
        setBackground(Color.BLACK);
        addKeyListener(new PacManKeyListener());
        setFocusable(true);
        requestFocusInWindow();
        for (int i = 0; i < 5; i++) {
            GameItemWord itemWord = new GameItemWord(pick());

            itemWords.add(itemWord);
            this.add(itemWord);
        }
        int DELAY = 10;
        ActionListener updater = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (!started || ended) {
                    return;
                }
                for (int i = 0; i < 5; i++) {
                    updateGameItemAt(i);

                }

                repaint();
            }
        };
        Timer tim = new Timer(DELAY, updater);
        tim.start();

    }

    String pick() {
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
        return totype.get(rand.nextInt(totype.size()));
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

        for (int i = 0; i < this.getComponents().length; i++) {
            GameItemWord itemWord = itemWords.get(i);
            int height = getHeight() * (i + 1) / 6;
            this.getComponent(i).setBounds(
                ((GameItemWord) this.getComponent(i)).getStrX() - 120 / 2,
                height - 120 / 2,
                this.getComponent(i).getPreferredSize().width,
                this.getComponent(i).getPreferredSize().height
            );
        }
        g.setColor(Color.GREEN);
        g.drawString("Typed word: " + typed, 10, getHeight() - 10);
        g.setColor(Color.RED);
        g.drawString("Score: " + score, getWidth() - 80, 10);
    }

    private void updateGameItemAt(int index) {
        GameItemWord iWord = ((GameItemWord) this.getComponent(index));
        iWord.setStrX();

        if (iWord.getStrX() >= getWidth()) {
            score -= 5;

            if (score <= -50) {
                ended = true;
            }
            iWord = new GameItemWord(pick());
            int height = getHeight() * (index + 1) / (itemWords.size() + 1);
            iWord.setBounds(- rand.nextInt(100), height, iWord.getPreferredSize().width, iWord.getPreferredSize().height);

            itemWords.set(index, iWord);
            this.remove(index);
            this.add(iWord, index);
        }
    }


    private void update(GameItemWord itemWord, int index) {
        this.remove(index);
        this.add(itemWord, index);
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
            for (int i = 0; i < itemWords.size(); i++) {
                GameItemWord itemWord = itemWords.get(i);
                if (typed.equals(itemWord.getText())) {
                    same = true;
                    score += 10;
                    itemWord = new GameItemWord(pick());
                    update(itemWord, i);
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
