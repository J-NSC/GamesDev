package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import jsudio.Game;
public class Tile {

    public static BufferedImage TILE_FLOOR = Game.sprite.getSprite(181, 1, 16, 16);
    public static BufferedImage TILE_WALL = Game.sprite.getSprite(211, 1, 16, 16);

    public boolean show = false;

    private BufferedImage sprite;
    private int x,y;

    public Tile(int x, int y , BufferedImage sprite){
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public void render (Graphics g){
        if(show)
            g.drawImage(sprite, x - Camera.x, y - Camera.y, null);

    }
}
