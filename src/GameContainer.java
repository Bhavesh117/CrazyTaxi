import javax.swing.*;
import java.awt.*;

public class GameContainer extends JFrame {

    private GameWindow gameWindow;

    private int width = 320;
    private int height = 240;
    private float scale = 3f;

    private String title = "Crazy Taxi";


    public GameContainer () {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        gameWindow = new GameWindow(this);
        add(gameWindow, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        requestFocus();
        gameWindow.start();
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getScale() {
        return scale;
    }
}
