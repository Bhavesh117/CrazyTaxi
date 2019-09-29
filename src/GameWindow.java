import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GameWindow extends Canvas implements Runnable {

    private Thread gameThread;
    private GameInput gameInput;
    private BufferedImage image;
    private BufferStrategy bs;
    private Player player;
    private Traffic traffic;
    private Graphics2D g;
    private Splatter splatter;

    private boolean isRunning;
    private boolean isPlaying;
    private boolean canSplatter;
    private final double UPDATE_CAP = 1.0/60.0;
    private int score = 0;
    private int tMultiplier = 5;
    private double tsMultiplier = 10;
    private int psMultiplier = 5;


    public GameWindow(GameContainer gc) {
        image = new BufferedImage(gc.getWidth(), gc.getHeight(), BufferedImage.TYPE_INT_RGB);
        Dimension s = new Dimension((int)(gc.getWidth() * gc.getScale()), (int)(gc.getHeight() * gc.getScale()));
        setPreferredSize(s);
        setMaximumSize(s);
        setMinimumSize(s);
    }

    public void start() {
        if (gameThread == null) {
            isRunning = true;
            createBufferStrategy(3);
            bs = getBufferStrategy();
            g = (Graphics2D)bs.getDrawGraphics();
            g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
            gameInput = new GameInput(this);
            gameThread = new Thread(this);
            player = new Player(this);
            traffic = new Traffic(this);
            gameThread.start();
        }
    }

    public void run () {
        isPlaying = false;
        boolean render = false;
        double firstTime;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime;
        double unprocessedTime = 0;
        double frameTime = 0;
        int frames = 0;
        int fps;

        while (isRunning) {
            render = false;
            requestFocus();
            firstTime = System.nanoTime() / 1000000000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;

            unprocessedTime += passedTime;
            frameTime += passedTime;

            while(unprocessedTime >= UPDATE_CAP) {
                unprocessedTime -= UPDATE_CAP;
                render = true;

                gameInput.update();

                if(isPlaying){
                    gameInput.playerUpdate();
                    player.update();
                    addTraffic();
                    increaseSpeed();
                }

                traffic.update();

                if (!player.isImmune()) canSplatter = true;

                if (player.isImmune() && canSplatter){
                    splatter = new Splatter(g, player.getX(), player.getY());
                    canSplatter = false;
                }

                if (frameTime >= 1.0){
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                    System.out.println("FPS:" + fps);
                    score++;
                    System.out.println("Score:" + score);
                }
            }

            if(!isPlaying){
                if(gameInput.startGame()){
                    isPlaying = true;
                }
            }

            if(render){
                this.update();
                frames++;
            }
            else {
                try {
                    Thread.sleep (10);	// increase value of sleep time to slow down ball
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public void update() {
        g.setBackground(Color.BLACK);
        g.clearRect(0, 0, getWidth(), getHeight());

        if(player.isImmune())
            splatter.draw();

        traffic.draw();
        player.draw();
        bs.show();
    }

    public void addTraffic(){
        if (score > traffic.trafficSize()*tMultiplier){
            traffic.addVehicle();
            tMultiplier += 2;
        }
    }

    public void increaseSpeed(){
        if (score > traffic.getSpeed()*tsMultiplier){
            traffic.increaseSpeed();
            tsMultiplier += 1.5;
        }

        if (score > player.getSpeed()*psMultiplier){
            player.increaseSpeed();
            psMultiplier += 2;
        }
    }

    public Graphics2D getG() {
        return g;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
