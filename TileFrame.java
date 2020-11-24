// This class is the top-level frame for a set of colored tiles.

import javax.swing.*;
import java.awt.*;

public class TileFrame extends JFrame {
    private TilePanel panel;

    public TileFrame() {
        setSize(1000, 600);
        setTitle("Typing Race");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel = new TilePanel();
        add(panel, BorderLayout.CENTER);
    }
}

