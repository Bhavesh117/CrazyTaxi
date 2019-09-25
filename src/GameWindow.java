import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GameWindow {

    private JFrame frame;
    private BufferedImage image;
    private Canvas canvas;
    private BufferStrategy bs;
    private Player player;

    private Graphics2D g;

    public GameWindow(GameContainer gc) {
        if (gc.getPlayer() == null)
            System.out.println("player no existo");

        if (gc.getPlayer() != null)
            System.out.println("player exist 2");

        image = new BufferedImage(gc.getWidth(), gc.getHeight(), BufferedImage.TYPE_INT_RGB);
        canvas = new Canvas();
        Dimension s = new Dimension((int)(gc.getWidth() * gc.getScale()), (int)(gc.getHeight() * gc.getScale()));
        canvas.setPreferredSize(s);
        canvas.setMaximumSize(s);
        canvas.setMinimumSize(s);

        frame = new JFrame(gc.getTitle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        g = (Graphics2D) bs.getDrawGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public void start(GameContainer gc) {
        this.player = gc.getPlayer();
        if (gc.getPlayer() != null)
            System.out.println("player exist 3");
    }


    public void update() {
        g.setBackground(Color.BLACK);
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//        if (player != null)
//            player.draw(g);
//        g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
        bs.show();
    }

    public BufferedImage getImage() {
        return image;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public BufferStrategy getBs() {
        return bs;
    }

    public Graphics2D getG() {
        return g;
    }
}
