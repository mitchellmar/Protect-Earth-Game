/* GameScene
    - Has GamePanel so it can call repaint
    - Has list of GameItems, add/remove
    - Asks each GameItem to draw :)
    - Has keyPressed 
    - Can startTimer, calls onTimer.

    - GameSceneTitle/GameSceneEnd
        ○ Has GameItemTitles e.g. for "PROTECT EARTH BY TYPING WORDS ON ASTEROIDS" or "YOU WON! THANK YOU FOR PROTECTING EARTH!" 
        ○ keyPress will setActive the GameSceneGame
    - GameSceneGame
        ○ Has GameWordPicker
        ○ Starts timer
        ○ Creates game items for words, earth, score, healthbar etc.
        ○ Pick words from GameWordPicker and create GameItemWords
        ○ Update score in GameItemScore , typed text in GameItemTyped etc.
 */

import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import models.GameItemWord;

public class GameScene extends JPanel {

    private static final long serialVersionUID = 2381970222661612660L;
    private static final int AST = 120;
    private static final int DELAY = 10;
    private static final String EARTH_URL = "earth.png";

    // Game components
    private Timer timer;
    private BufferedImage earth;
    private GameWordPicker wordPicker;
    private ArrayList<GameItemWord> itemWords = new ArrayList<>();

    // Game states
    private Random random = new Random();
    private String typed = "";
    private int score = 0;
    private boolean started = false;
    private boolean ended = false;
    private boolean same = false;

    public GameScene() {
        loadComponents();
        configureGameScene();
        setEventListeners();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(earth, getWidth() - 100, 0, earth.getWidth() * getHeight() / earth.getHeight(), getHeight(), null);
        Font font = new Font("default", Font.BOLD, 12);
        g.setFont(font);
        if (!started) {
            menuStage(g);
            return;
        }

        if (ended) {
            endStage(g);
            return;
        }

        for (int i = 0; i < this.getComponents().length; i++) {
            int height = getHeight() * (i + 1) / 6;
            this.getComponent(i).setBounds(((GameItemWord) this.getComponent(i)).getStrX() - 120 / 2, height - 120 / 2,
                    this.getComponent(i).getPreferredSize().width, this.getComponent(i).getPreferredSize().height);
        }
        g.setColor(Color.GREEN);
        g.drawString("Typed word: " + typed, 10, getHeight() - 10);
        g.setColor(Color.RED);
        g.drawString("Score: " + score, getWidth() - 80, 10);
    }

    private void loadComponents() {
        try {
            earth = ImageIO.read(new File(EARTH_URL));
        } catch (IOException ext) {
        }

        wordPicker = new GameWordPicker();

        for (int i = 0; i < 5; i++) {
            GameItemWord itemWord = new GameItemWord(wordPicker.pick());

            itemWords.add(itemWord);
            this.add(itemWord);
        }

    }

    private void configureGameScene() {
        setLayout(null);
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();
    }

    private void setEventListeners() {
        addKeyListener(new KeyAdapter() {
            @Override
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
                        loadComponents();
                        ended = false;
                        score = 0;
                    }
                } else {
                    typed += keyChar;
                }
                same = false;
                for (int i = 0; i < getComponentSize(); i++) {
                    handleKeyPressed(i);
                }
                if (same) {
                    typed = "";
                }
                repaint();
            }
        });
        ActionListener updater = new ActionListener() {
            @Override
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

        timer = new Timer(DELAY, updater);
        timer.start();
    }

    private void updateGameItemAt(int index) {
        GameItemWord iWord = ((GameItemWord) this.getComponent(index));
        iWord.setStrX();

        if (iWord.getStrX() >= getWidth()) {
            score -= 5;

            if (score <= -50) {
                ended = true;
            }
            iWord = new GameItemWord(wordPicker.pick());
            int height = getHeight() * (index + 1) / (itemWords.size() + 1);
            iWord.setBounds(-random.nextInt(100), height, iWord.getPreferredSize().width,
                    iWord.getPreferredSize().height);

            itemWords.set(index, iWord);
            this.remove(index);
            this.add(iWord, index);
        }
    }

    private void update(GameItemWord itemWord, int index) {
        this.remove(index);
        this.add(itemWord, index);
    }

    private int getComponentSize() {
        return this.getComponents().length;
    }

    private void handleKeyPressed(int i) {
        if (typed.equals(((GameItemWord) this.getComponent(i)).getText())) {
            same = true;
            score += 10;
            GameItemWord itemWord = new GameItemWord(wordPicker.pick());
            update(itemWord, i);
            if (score >= 100) {
                ended = true;
            }
        }
    }

    private void menuStage(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawString("PROTECT EARTH BY TYPING WORDS ON ASTEROIDS!", 200, 200);
        g.setColor(Color.RED);
        g.drawString("IF THEY HIT EARTH, YOU LOSE POINTS!", 200, 250);
        g.setColor(Color.GREEN);
        g.drawString("PLEASE HIT SPACE TO START GAME!", 200, 300);
    }

    private void endStage(Graphics g) {
        if (score >= 100) {
            g.drawString("YOU WON! THANK YOU FOR PROTECTING EARTH!", 200, 200);
        } else {
            g.drawString("YOU LOST! PLEASE TRY AGAIN...", 200, 200);
        }

        // Clear words after ended
        for (int i = 0; i < this.getComponents().length; i++) {
            if (this.getComponent(i) instanceof GameItemWord) {
                this.remove(i);
                itemWords.remove(i);
                i--;
            }
        }

        g.setColor(Color.RED);
        g.drawString("YOUR SCORE WAS " + score, 200, 250);
        g.setColor(Color.GREEN);
        g.drawString("PLEASE HIT SPACE TO START GAME!", 200, 300);
    }
}