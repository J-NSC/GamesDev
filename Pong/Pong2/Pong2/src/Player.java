
import java.awt.Graphics;
import java.awt.Color;

public class Player {
    public boolean right;
    public boolean left;
    public int x,y;
    public int width, height;

    public Player(int x,int y){
        this.x = x;
        this.y = y;
        width = 160;
        height =10;
    }   

    public void tick(){
        if(right){
            x++;
        }else if(left){
            x--;
        }


        if(x+width > Game.WIDTH){
            x =Game.WIDTH - width;
        }

        else if( x < 0){
            x = 0;
        }
    }

    public void render (Graphics g){
        g.setColor(Color.red);
        g.fillRect(x, y, width, height);
    }
}
