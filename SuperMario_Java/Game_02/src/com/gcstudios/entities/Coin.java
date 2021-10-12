package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Coin extends Entity{


    private int frameAnimation =0;
    private int maxFrames = 10;
    private int maxSprite = 4;
    private int curSprite = 0;

    public Coin(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
        //TODO Auto-generated constructor stub
    }



    @Override
    public void render(Graphics g) {


        frameAnimation++;
    
        if(frameAnimation == maxFrames){
            curSprite++;
            frameAnimation = 0;
            if(curSprite == maxSprite){
                curSprite = 0;
            }
        }

        sprite = Entity.COIN_SPIRTE[curSprite];

        super.render(g);
    }
    
}
