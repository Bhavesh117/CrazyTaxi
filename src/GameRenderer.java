import java.awt.image.DataBufferInt;

public class GameRenderer {

    private int pixelWidth, pixelHeight;
    private int[] pixels;

    public GameRenderer(GameContainer gc){
        pixelWidth = gc.getWidth();
        pixelHeight = gc.getHeight();
        pixels = ((DataBufferInt)gc.getGameWindow().getImage().getRaster().getDataBuffer()).getData();
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++){
            pixels[i] += 0;
        }
    }
}
