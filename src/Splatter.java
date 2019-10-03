import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Splatter {
    private int x, y;
    private Graphics2D g2;
    private int red = 150;
    private int r1, r2, r3, r4, r5, r6, r7;

    public Splatter (Graphics2D g2, int x, int y){
        this.g2 = g2;
        this.x = x;
        this.y = y;

        Random r = new Random();
        r1 = r.nextInt(10) + 10;
        r2 = r.nextInt(10) + y;
        r3 = r.nextInt(17) + 8;
        r4 = r.nextInt(16) + 9;
        r5 = r.nextInt(11) + x;
        r6 = r.nextInt(17) + 8;
        r7 = r.nextInt(15) + 6;
    }

    public void draw(){

        g2.setColor(new Color(red , 0, 0));
        g2.fill(new Ellipse2D.Double(x, y, r1, r1));
        g2.fill(new Ellipse2D.Double(x - 15, r2, r3, r4));
        g2.fill(new Ellipse2D.Double(r5, y + 5, r6, 14));
        g2.fill(new Ellipse2D.Double(x - 20, y + 10, r7, r3));

        if(red > 0)
            red--; // fade effect
    }
}
