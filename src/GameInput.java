import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameInput implements KeyListener {

    private GameWindow gw;
    private final int NUM_KEYS = 256;
    private boolean[] keys = new boolean[NUM_KEYS];

    public GameInput(GameWindow gw) {
        this.gw = gw;

        gw.addKeyListener(this);
    }


    public void playerUpdate() {
        if (isKey(KeyEvent.VK_A)){
            gw.getPlayer().moveLeft();
        }

        if (isKey(KeyEvent.VK_D)){
            gw.getPlayer().moveRight();
        }

        if (isKey(KeyEvent.VK_W )){
            gw.getPlayer().moveUp();
        }

        if (isKey(KeyEvent.VK_S)){
            gw.getPlayer().moveDown();
        }

        if ((isKey(KeyEvent.VK_S) && isKey(KeyEvent.VK_A)) || (isKey(KeyEvent.VK_W) && isKey(KeyEvent.VK_D))){
            gw.getPlayer().moveDiagonalRight();
        }
        if((isKey(KeyEvent.VK_S) && isKey(KeyEvent.VK_D)) ||  (isKey(KeyEvent.VK_W) && isKey(KeyEvent.VK_A)) ){
            gw.getPlayer().moveDiagonalLeft();
        }
    }

    public boolean startGame() {
        return (isKey(KeyEvent.VK_SPACE));
    }

    public boolean endGame() {
        return (isKey(KeyEvent.VK_ESCAPE));
    }

    public boolean pauseGame() {
        return (isKey(KeyEvent.VK_SHIFT));
    }

    public boolean isKey(int keyCode) {
        return keys[keyCode];
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
