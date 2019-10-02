import java.awt.*;

public class HUD {

    private GameWindow gw;
    private Graphics2D g2;
    private int rgb = 252;
    private Font space, info;

    public HUD(GameWindow gw){
        this.gw = gw;
        this.g2 = gw.getG();

        space = new Font("Arial Black", Font.PLAIN, 50);
        info = new Font("Arial Black", Font.PLAIN, 25);
    }

    public void draw(){
        if(!gw.isPlaying() && !gw.isPlayerDead() && !gw.isPaused()){
            gameStart();
        }
        else if (gw.isPlayerDead()){
            gameOver();
        }
        else {
            gamePlaying();
        }
    }

    public void gameStart(){
        g2.setColor(new Color(rgb, rgb, rgb));
        g2.setFont(space);
        g2.drawString("Press Space To Start", 90 * 4, 210 * 4);

        rgb -= 2;

        if(rgb <= 0){
            rgb = 252;
        }
    }

    public void gamePlaying(){
        g2.setColor(Color.white);
        g2.setFont(info);
        g2.drawString("Score: " + gw.getScore(), 10 * 4, 10 * 4);
        g2.drawString("Health: " + gw.getPlayer().getHealth(), 140 * 4, 10 * 4);
        g2.drawString("FPS: " + gw.getFps(), 280 * 4, 10 * 4);
    }

    public void gameOver(){
        g2.setColor(Color.white);
        g2.setFont(space);
        g2.drawString("You Died!", 130 * 4, 100 * 4);
        g2.drawString("Your Score: " + gw.getScore(), 110 * 4, 140 * 4);

        g2.setColor(new Color(rgb, rgb, rgb));
        g2.drawString("Press Space To Start Over", 70 * 4, 210 * 4);

        rgb -= 2;

        if(rgb <= 0){
            rgb = 252;
        }
    }
}
