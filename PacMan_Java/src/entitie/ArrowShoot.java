package entitie;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import jsudio.Game;
import world.Camera;
import world.World;

public class ArrowShoot extends Entity {

    private double dx;
    private double dy;
    private double spd = 4;

    private int life = 30, curLife =0;

    public ArrowShoot(double x, double y, int width, int height, BufferedImage sprite, double dx, Double dy) {
        super(x, y, width, height, sprite);

        this.dx = dx;
        this.dy = dy;
    }

    public void tick(){
        x+= dx * spd;
        y+= dy * spd;
        curLife ++;
        if(curLife == life){
            Game.arrow.remove(this);
            // World.generateParticles(100, x, y);
            return;
        }
    }


    public void render(Graphics g) {

        if(Player.dir == Player.right_dir){
            g.drawImage(Entity.arrow_shoting_right, getX() - Camera.x, getY() - Camera.y, null);
        }else if(Player.dir == Player.left_dir){
            g.drawImage(Entity.arrow_shoting_left, getX() - Camera.x, getY() - Camera.y, null);
        }else if(Player.dir == Player.up_dir){
            g.drawImage(Entity.arrow_shoting_up, getX() - Camera.x, getY() - Camera.y, null);
        }else if(Player.dir == Player.down_dir){
            g.drawImage(Entity.arrow_shoting_down, getX() - Camera.x, getY() - Camera.y, null);
        }
    }
}
