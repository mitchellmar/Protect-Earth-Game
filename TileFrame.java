// This class is the top-level frame for a set of colored tiles.

import javax.swing.*;
import java.awt.*;

public class TileFrame extends JFrame {

    private static final long serialVersionUID = -851524722421871621L;
    // private TilePanel panel;
    private GameScene panel;

    public TileFrame() {
        setSize(1000, 600);
        setTitle("Typing Race");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel = new GameScene();
        add(panel, BorderLayout.CENTER);
    }
}

