import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class PowerUp {
    private Graphics2D g2;
    private BufferedImage image;
    private GameWindow gw;
    private int x, y;
    private int type;
    private double pickUpTime = System.nanoTime() / 1000000000.0;
    private static final int SIZE = 20;
    private boolean isVisible = false;
    private boolean isHeart = false;
    private boolean isFocus = false;
    private boolean isFocusActive = false;


    public PowerUp(GameWindow gw) {
        this.gw = gw;
        this.g2 = gw.getG();
        Random rand = new Random();

        type = rand.nextInt(2);

        if(gw.getScore() < 150 || type == 0){
            type = 0;
            image = ImageReader.loadImage("assets/images/heart.png");
            isHeart = true;
        }
        else {
            image = ImageReader.loadImage("assets/images/focus.png");
            isFocus = true;
        }

        x = rand.nextInt((gw.getDimension().width - 100) + 1) + 50;
        y = rand.nextInt((gw.getDimension().height - 100) + 1) + 50;
    }

    public void draw() {
        if(isVisible)
            g2.drawImage(image, x, y, SIZE, SIZE, null);
    }

    public void update() {
        double presentTime = System.nanoTime() / 1000000000.0;

        if(gw.getPlayer().isPowerUp(this) && isVisible){
            pickUpTime = System.nanoTime() / 1000000000.0;

            if (type == 0){
                gw.getPlayer().healthUp();
            }
            else if (!isFocusActive){
                isFocusActive = true;
            }

            isVisible = false;
        }

        if (presentTime - pickUpTime > 6 && isFocusActive){
            isFocusActive = false;
            System.out.println("is focus is not active");
        }
    }

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double (x, y, SIZE, SIZE);
    }

    public boolean isFocusActive() {
        return isFocusActive;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
