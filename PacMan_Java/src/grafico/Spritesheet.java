
package grafico;

import java.awt.image.*;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {

    private BufferedImage spritesheet;

    public Spritesheet(String path){
        try {
            spritesheet = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }  
    
    public BufferedImage getSprite(int x, int y, int widht, int height){
        return spritesheet.getSubimage(x, y, widht, height);
    }


}
