package world;

import java.awt.image.BufferedImage;
import java.util.Random;

import entitie.Entity;
import jsudio.Game;

import java.awt.Color;
import java.awt.Graphics;

public class Particles  extends Entity{

    public int lifeTime = 15;
    public int curLife = 0;

    public int spd = 2;
    public double dx = 0;
    public double dy = 0;

    public Particles(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);

        dx =  new Random().nextGaussian();
        dy =  new Random().nextGaussian();
    } 

    public void tick(){
        x+= dx * spd;
        y+= dy * spd;
        curLife++;
        if(lifeTime == curLife){
            Game.entities.remove(this);

        }
    }


    public void render(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(this.getX()-Camera.x,this.getY()-Camera.y,width,height);
    }
    
}
