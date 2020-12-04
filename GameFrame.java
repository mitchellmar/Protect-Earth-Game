
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
  
    private GamePanel panel;

    public GameFrame() {
        setSize(1000, 600);
        setTitle("Protect Earth");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel = new TilePanel();
        add(panel, BorderLayout.CENTER);
    }
  
}

