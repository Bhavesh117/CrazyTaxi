import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class People {

    private Graphics2D g2;
    private Random rand;
    private final int SIZE = 10;
    private float r, g, b;
    private int px, py, dx, dy;
    private Color color;
    private boolean isPassengerCollected = false;
    private boolean isDroppedOff = false;

    public People (Dimension dimension, Graphics2D g2) {
        this.g2 = g2;

        rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        color = new Color(r, g, b).brighter();

        px = rand.nextInt((dimension.width - 100) + 1) + 50;
        py = rand.nextInt((dimension.height - 100) + 1) + 50;

        dx = rand.nextInt((dimension.width - 100) + 1) + 50;
        dy = rand.nextInt((dimension.height - 100) + 1) + 50;
    }

    public void draw() {
        g2.setColor(color);

        if (!isPassengerCollected) g2.fill(new Ellipse2D.Double(px, py, SIZE, SIZE));
        if (!isDroppedOff && isPassengerCollected) g2.fill(new Rectangle2D.Double(dx, dy, SIZE, SIZE));
    }

    public Rectangle2D.Double getBoundingDropOffRectangle() {
        return new Rectangle2D.Double (dx, dy, SIZE, SIZE);
    }

    public Rectangle2D.Double getBoundingPassengerRectangle() {
        return new Rectangle2D.Double (px, py, SIZE, SIZE);
    }

    public void setPassengerCollected(boolean passengerCollected) {
        isPassengerCollected = passengerCollected;
    }

    public void setDroppedOff(boolean droppedOff) {
        isDroppedOff = droppedOff;
    }

    public boolean isPassengerCollected() {
        return isPassengerCollected;
    }
}
