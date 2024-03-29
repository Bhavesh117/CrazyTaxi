import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Vehicle {

    private double dx, dy;
    private Dimension dimension;
    private double x, y;
    private int xSize, ySize;
    private Color color;
    private boolean inFocus = false;

    public Vehicle (GameWindow gw, int dx, int dy){
        this.dx = dx;
        this.dy = dy;
        dimension = gw.getSize();
        int rgb = new Random().nextInt((200 - 150) + 1) + 150;
        color = new Color(rgb, rgb, rgb);

//        Random vehicle size based on movement direction.
        if (dx != 0){
            xSize = new Random().nextInt((80 - 60) + 1) + 60;
            ySize = new Random().nextInt((40 - 30) + 1) + 30;
        }

        if (dy != 0){
            ySize = new Random().nextInt((80 - 60) + 1) + 60;
            xSize = new Random().nextInt((40 - 30) + 1) + 30;
        }

//        Determine spawn location off screen based on vehicle direction.
        if (dx > 0){
            x = xSize * -1;
            y = new Random().nextInt((dimension.height - 10) + 1) + 10;
        }
        else if (dx < 0){
            x = xSize + dimension.width;
            y = new Random().nextInt((dimension.height - 10) + 1) + 10;
        }
        else if (dy > 0){
            x = new Random().nextInt((dimension.width - 10) + 1) + 10;
            y = ySize * -1;
        }
        else if (dy < 0){
            x = new Random().nextInt((dimension.width - 10) + 1) + 10;
            y = ySize + dimension.height;
        }

    }

    public void draw (Graphics2D g2) {
        g2.setColor (color);
        g2.fill (new Rectangle2D.Double (x, y, xSize, ySize));
    }

//    vehicles off screen teleport to the other side at a new random position
    public void move () {
        x += dx;
        y += dy;

        if (x > dimension.width + xSize + 10){
            y = new Random().nextInt((dimension.height - 10) + 1) + 10;
            x = xSize * -1;
        }
        else if (x < (xSize * -1) - 10){
            y = new Random().nextInt((dimension.height - 10) + 1) + 10;
            x = xSize + dimension.width;
        }
        else if (y > dimension.height + ySize + 10){
            x = new Random().nextInt((dimension.width - 10) + 1) + 10;
            y = ySize * -1;
        }
        else if (y < (ySize * -1) - 10){
            x = new Random().nextInt((dimension.width - 10) + 1) + 10;
            y = ySize + dimension.height;
        }
    }

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double (x, y, xSize, ySize);
    }

    public void setFocus(double factor) {
        this.dx = dx * factor;
        this.dy = dy * factor;

        if(!inFocus)
            inFocus = true;
        else
            inFocus = false;
    }



    public boolean isInFocus() {
        return inFocus;
    }
}
