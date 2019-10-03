import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GameWindow extends Canvas implements Runnable {

    private Thread gameThread;
    private GameInput gameInput;
    private BufferedImage image; 
    private BufferStrategy bs;
    private Player player;
    private Passenger passengers;
    private PowerUp powerUp;
    private Traffic traffic;
    private Graphics2D g;
    private Splatter splatter;
    private Dimension dimension;
    private SoundEffect soundEffect;
    private HUD hud;
    private GameContainer gameContainer;

    private boolean isRunning;
    private boolean isPlaying;
    private boolean isPaused;
    private boolean canSplatter;
    private boolean canSpawnPowerUp;
    private boolean isPlayerDead;
    private final double UPDATE_CAP = 1.0/60.0;
    private int maxPassengers;
    private int score;
    private int fps;
    private int tMultiplier = 5;
    private double tsMultiplier = 10;
    private int psMultiplier = 5;
    private String backgroundAudio = "assets/audio/Background.wav";


    public GameWindow(GameContainer gc) {
        image = new BufferedImage(gc.getWidth(), gc.getHeight(), BufferedImage.TYPE_INT_RGB);
        dimension = new Dimension((int)(gc.getWidth() * gc.getScale()), (int)(gc.getHeight() * gc.getScale()));
        gameContainer = gc;
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
    }

    public void start() {
        if (gameThread == null) {
            setUpGame();
            gameThread.start();
        }
    }

//    sets up the game.
    public void setUpGame() {
        isRunning = true;
        createBufferStrategy(3);
        bs = getBufferStrategy();
        g = (Graphics2D)bs.getDrawGraphics();
        g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        gameInput = new GameInput(this);
        gameThread = new Thread(this);
        player = new Player(this);
        powerUp = new PowerUp(this);
        passengers = new Passenger(this);
        traffic = new Traffic(this);
        soundEffect = new SoundEffect();
        hud = new HUD(this);
        score = 0;
        maxPassengers = 1;
        canSpawnPowerUp = true;
        isPlayerDead = false;
        isPaused = false;
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

        soundEffect.setFile(backgroundAudio);
        soundEffect.play();
        soundEffect.loop();

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

                update();

                if (frameTime >= 1.0){
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                }
            }

            pauseGame();
            playGame();
            newGame();
            endGame();

            if(render){
                this.draw();
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

        gameContainer.dispose();
    }

    public void pauseGame(){
        if(gameInput.pauseGame()){
            isPaused = true;
            isPlaying = false;
        }
    }

    public void playGame(){
        if(!isPlaying && !isPlayerDead){
            if(gameInput.startGame()){
                isPlaying = true;
                isPaused = false;
            }
        }
    }

    public void endGame(){
        if(gameInput.endGame()){
            isRunning = false;
            soundEffect.stop();
        }
    }

    public void newGame(){
        if(!isPlaying && isPlayerDead){
            if(gameInput.startGame()){
                setUpGame();
                isPlaying = true;

            }
        }
    }

    public void draw() {
        g.setBackground(Color.BLACK);
        g.clearRect(0, 0, getWidth(), getHeight());

        if(player.isImmune())
            splatter.draw();

        powerUp.draw();
        passengers.draw();
        traffic.draw();
        player.draw();
        hud.draw();
        bs.show();
    }

    public void update(){
        if(!isPaused){
            if(isPlaying && !isPlayerDead){
                gameInput.playerUpdate();
                player.update();
                canSpawnPowerUp = updatePowerUpSpawn();
                powerUp.update();
                passengers.update();
                refreshPowerUp();
                addTraffic();
                increaseSpeed();
                increaseMaxPassengers();
            }

            traffic.update();
        }

        if (!player.isImmune()) canSplatter = true;

        if (player.isImmune() && canSplatter){
            splatter = new Splatter(g, player.getX(), player.getY());
            canSplatter = false;
        }

        if (player.getHealth() <= 0){
            isPlayerDead = true;
            isPlaying = false;
        }
    }

//  Adds more vehicles to the game according to the players score
    public void addTraffic(){
        if (score > traffic.trafficSize()*tMultiplier){
            traffic.addVehicle();
            tMultiplier += 2;
        }
    }

//  Refreshes the power up based on the score if none are available.
    public void refreshPowerUp() {
        if (score % 50 == 0 && score != 0 && !powerUp.isVisible() && canSpawnPowerUp){
            powerUp = new PowerUp(this);
            powerUp.setVisible(true);
        }
    }

//  Used to determine if it is possible for a power up to be spawned.
    public boolean updatePowerUpSpawn(){
        return (score % 50 != 0);
    }

//    Increases new traffic vehicles speed and the speed of the player based on the score.
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

    public void increaseScore(){
        score += 5;
    }

    public void increaseMaxPassengers(){
        if (score > maxPassengers*20 && maxPassengers < 3)
        maxPassengers += 1;
    }

    public int getMaxPassengers() {
        return maxPassengers;
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

    public Dimension getDimension() {
        return dimension;
    }

    public int getScore() {
        return score;
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }

    public int getFps() {
        return fps;
    }

    public boolean isPlayerDead() {
        return isPlayerDead;
    }

    public boolean isPaused() {
        return isPaused;
    }
}
