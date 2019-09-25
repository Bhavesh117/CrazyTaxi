import java.awt.event.KeyEvent;

public class GameContainer implements  Runnable {

    private Thread gameThread;
    private GameWindow gameWindow;
    private GameRenderer gameRenderer;
    private GameInput gameInput;
    private Player player;

    private boolean isRunning;
    private final double UPDATE_CAP = 1.0/60.0;

    private int width = 320;
    private int height = 240;
    private float scale = 4f;
    private String title = "Crazy Taxi";


    public GameContainer () {

    }

    public void start() {
        gameWindow = new GameWindow(this);
        gameRenderer = new GameRenderer(this);
        gameInput = new GameInput(this);
        if (getPlayer() != null)
            System.out.println("player exist");
        gameThread = new Thread(this);
        gameThread.run();
    }

    public void run () {
        isRunning = true;
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
            firstTime = System.nanoTime() / 1000000000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;

            unprocessedTime += passedTime;
            frameTime += passedTime;

            while(unprocessedTime >= UPDATE_CAP) {
                unprocessedTime -= UPDATE_CAP;
                render = true;

                gameInput.update();

                if (frameTime >= 1.0){
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                    System.out.println("FPS:" + fps);
                }

            }

            if(render){
                gameRenderer.clear();
                gameWindow.update();
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

        dispose();
    }

    private void dispose() {

    }

    public GameWindow getGameWindow() {
        return gameWindow;
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

    public String getTitle() {
        return title;
    }

    public GameInput getGameInput() {
        return gameInput;
    }

    public Player getPlayer() {
        return player;
    }
}
