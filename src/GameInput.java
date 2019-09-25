import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameInput implements KeyListener {

    private GameContainer gc;
    private final int NUM_KEYS = 256;
    private boolean[] keys = new boolean[NUM_KEYS];
    private boolean[] keysLast = new boolean[NUM_KEYS];

    public GameInput(GameContainer gc) {
        this.gc = gc;

        gc.getGameWindow().getCanvas().addKeyListener(this);
    }

    public void update() {
        for(int i = 0; i < NUM_KEYS; i++){
            keysLast[i] = keys[i];
        }

        if (isKey(KeyEvent.VK_A)){
            gc.getPlayer().moveLeft();
        }

        if (isKey(KeyEvent.VK_D)){
            gc.getPlayer().moveRight();
        }
    }

    public boolean startGame() {
        return (isKey(KeyEvent.VK_SPACE));
    }

    public boolean isKey(int keyCode) {
        return keys[keyCode];
    }

    public boolean isKeyUp(int keyCode) {
        return !keys[keyCode] && keysLast[keyCode];
    }

    public boolean isKeyDown(int keyCode) {
        return keys[keyCode] && !keysLast[keyCode];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
}
