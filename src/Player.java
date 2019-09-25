import java.util.Random;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.applet.AudioClip;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.IOException;

import javax.sound.sampled.*;

public class Player {

    private static final int XSIZE = 30;
    private static final int YSIZE = 10;
    private static final int XPOS = 200;
    private static final int YPOS = 200;
    private int SPEED = 2;

    private GameWindow gw;
    private Dimension dimension;
    private int x;
    private int y;


    public Player (GameWindow gw){
        this.gw = gw;

        dimension = gw.getCanvas().getSize();
        x = XPOS;
        y = YPOS;
    }

    public void draw (Graphics2D g) {
        g.setColor (Color.GREEN);
        g.fill (new Rectangle2D.Double (x, y, XSIZE, YSIZE));
    }

    public void erase (Graphics2D g) {
        g.setColor (Color.BLACK);
        g.fill (new Rectangle2D.Double (x, y, XSIZE, YSIZE));
    }

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double (x, y, XSIZE, YSIZE);
    }

    public void moveLeft () {

        if (!gw.getCanvas().isVisible ()) return;

        erase(gw.getG());

        x = x - SPEED;

        if (x < 0) {					// hits left wall
            x = 0;
            erase(gw.getG());
        }

    }

    public void moveRight () {

        if (!gw.getCanvas().isVisible ()) return;

        erase(gw.getG());

        x = x + SPEED;

        if (x + XSIZE >= dimension.width) {		// hits right wall
            x = dimension.width - XSIZE;
            erase(gw.getG());
        }

    }

}
