
import java.awt.Graphics;
import java.awt.Color;

public class Player {
    public void tick(){

    }

    public void render (Graphics g){
        g.setColor(Color.blue);
        g.fillRect(200, 120-10, 40, 10);
    }
}
