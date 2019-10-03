import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Player {

    private static final int XSIZE = 30;
    private int YSIZE = 60;
    private static final int XPOS = 200;
    private static final int YPOS = 200;
    private int speed = 4;
    private int health = 5;
    private int playerXSize = XSIZE;
    private int playerYSize = YSIZE;
    private double angle = 0;
    private double accidentTime = System.nanoTime() / 1000000000.0;
    private List<People> passengers;
    private Graphics2D g2;
    private GameWindow gw;
    private Dimension dimension;
    private Color color;
    private SoundEffect sf;
    private String pickUpAudio = "assets/audio/Pickup.wav";
    private String dropOffAudio = "assets/audio/DropOff.wav";
    private String crashAudio = "assets/audio/Crash.wav";
    private int x;
    private int y;
    private boolean isImmune  = false;


    public Player (GameWindow gw){
        this.g2 = gw.getG();
        this.gw = gw;

        dimension = gw.getSize();
        color = Color.YELLOW;
        x = XPOS;
        y = YPOS;
        passengers = new ArrayList<>();
        sf = new SoundEffect();
    }

//    Player immune check.
    public void update(){
        double presentTime = System.nanoTime() / 1000000000.0;
        if (presentTime - accidentTime > 3){
            isImmune = false;
            color = Color.YELLOW;
        }
    }

    public void draw () {
        g2.setColor (color);
        AffineTransform at = AffineTransform.getRotateInstance(angle,x + playerXSize/2, y + playerYSize/2);
        g2.fill(at.createTransformedShape(new Rectangle2D.Double (x, y, playerXSize, playerYSize)));
    }

    public void erase () {
        g2.setColor (Color.BLACK);
        g2.fill(new Rectangle2D.Double (x, y, playerXSize, playerYSize));
    }

//    Returns bounding area for player.
    public Area getBoundingArea() {
        AffineTransform at = AffineTransform.getRotateInstance(angle,x + playerXSize/2, y + playerYSize/2);
        Area a = new Area(new Rectangle2D.Double (x, y, playerXSize, playerYSize));
        a.transform(at);
        return a;
    }

    public void moveLeft () {

        if (!gw.isVisible ()) return;

        erase();

        playerXSize = YSIZE;
        playerYSize = XSIZE;
        x = x - speed;
        angle = 0;

        if (x < 0) {					// hits left wall
            x = 0;
            erase();
        }
    }

    public void moveRight () {

        if (!gw.isVisible ()) return;

        erase();

        playerXSize = YSIZE;
        playerYSize = XSIZE;
        x = x + speed;
        angle = 0;

        if (x + playerXSize >= dimension.width) {		// hits right wall
            x = dimension.width - playerXSize;
            erase();
        }
    }

    public void moveUp () {
        if (!gw.isVisible ()) return;

        erase();

        playerXSize = XSIZE;
        playerYSize = YSIZE;
        y = y - speed;
        angle = 0;

        if (y < 0) {					// hits left wall
            y = 0;
            erase();
        }
    }

    public void moveDown () {
        if (!gw.isVisible ()) return;

        erase();

        playerXSize = XSIZE;
        playerYSize = YSIZE;
        y = y + speed;
        angle = 0;

        if (y + playerYSize>= dimension.height) {					// hits left wall
            y = dimension.height - playerYSize;
            erase();
        }
    }

    public void moveDiagonalRight () {
        if (!gw.isVisible ()) return;

        erase();
        angle = 45;

    }

    public void moveDiagonalLeft () {
        if (!gw.isVisible ()) return;

        erase();
        angle = -45;
    }

    public void increaseSpeed() {
        speed += 1;
    }

//    Determines if the player has collied with a vehicle.
    public boolean isAccident(Vehicle v) {
        Area playerBox = getBoundingArea();
        Rectangle2D vehicleRec = v.getBoundingRectangle();

        return (playerBox.intersects(vehicleRec));
    }

//    decreases player health and sets player to being immune to hits.
    public void damage(){
        if(!isImmune){
            health -= 1;
            isImmune = true;
            accidentTime = System.nanoTime() / 1000000000.0;
            color = Color.WHITE;
            sf.setFile(crashAudio);
            sf.play();
        }
    }

//    Determines if the player has collied with a power up.
    public boolean isPowerUp(PowerUp powerUp){
        Area playerBox = getBoundingArea();
        Rectangle2D powerUpRec = powerUp.getBoundingRectangle();

        return (playerBox.intersects(powerUpRec));
    }

//    Determines if the player has collied with a passenger.
    public boolean isPassenger(People passenger){
        Area playerBox = getBoundingArea();
        Rectangle2D passengerRec = passenger.getBoundingPassengerRectangle();

        return (playerBox.intersects(passengerRec));
    }

//    Determines if the player has collied with a passenger drop off.
    public boolean isDropOff(People passenger){
        Area playerBox = getBoundingArea();
        Rectangle2D dropOffRec = passenger.getBoundingDropOffRectangle();

        return (playerBox.intersects(dropOffRec) && passengers.contains(passenger));
    }

//    Adds a passenger to the player car and increases player size.
    public void pickUpPassenger(People passenger){
        passengers.add(passenger);
        sf.setFile(pickUpAudio);
        sf.play();
        YSIZE += 5;
    }

//    Removes a passenger from the player car and decreases player size.
    public void dropOffPassenger(People passenger){
        passengers.remove(passenger);
        sf.setFile(dropOffAudio);
        sf.play();
        YSIZE -= 5;
    }

    public void healthUp(){
        health += 1;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isImmune() {
        return isImmune;
    }

    public int getHealth() {
        return health;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
