import java.awt.*;

public class HUD {

    private GameWindow gw;
    private Graphics2D g2;
    private int rgb = 252;
    private Font space, info;

    public HUD(GameWindow gw){
        this.gw = gw;
        this.g2 = gw.getG();

        space = new Font("Arial Black", Font.PLAIN, 40);
        info = new Font("Arial Black", Font.PLAIN, 20);
    }

    public void draw(){
        if(!gw.isPlaying() && !gw.isPlayerDead() && !gw.isPaused()){
            gameStart();
        }
        else if (gw.isPlayerDead()){
            gameOver();
        }
        else if (gw.isPaused()){
            gamePaused();
        }
        else {
            gamePlaying();
        }
    }

    public void gameStart(){
        g2.setColor(new Color(rgb, rgb, rgb));
        g2.setFont(space);
        g2.drawString("Press Space To Start", 85 * 3, 210 * 3);

        rgb -= 2;

        if(rgb <= 0){
            rgb = 252;
        }
    }

    public void gamePaused(){
        g2.setColor(new Color(rgb, rgb, rgb));
        g2.setFont(space);
        g2.drawString("Press Space To Resume", 70 * 3, 210 * 3);

        rgb -= 2;

        if(rgb <= 0){
            rgb = 252;
        }
    }

    public void gamePlaying(){
        g2.setColor(Color.white);
        g2.setFont(info);
        g2.drawString("Score: " + gw.getScore(), 10 * 3, 10 * 3);
        g2.drawString("Health: " + gw.getPlayer().getHealth(), 140 * 3, 10 * 3);
        g2.drawString("FPS: " + gw.getFps(), 280 * 3, 10 * 3);
    }

    public void gameOver(){
        g2.setColor(Color.white);
        g2.setFont(space);
        g2.drawString("You Died!", 130 * 3, 100 * 3);
        g2.drawString("Your Score: " + gw.getScore(), 110 * 3, 140 * 3);

        g2.setColor(new Color(rgb, rgb, rgb));
        g2.drawString("Press Space To Start Over", 70 * 3, 210 * 3);

        rgb -= 2;

        if(rgb <= 0){
            rgb = 252;
        }
    }
}
