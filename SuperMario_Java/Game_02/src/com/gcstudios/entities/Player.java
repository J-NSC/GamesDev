package com.gcstudios.entities;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;
import com.gcstudios.world.World;

public class Player extends Entity{
	
	public boolean right,up,left,down;
    public boolean isJump = false, jump = false, isWalk= false,teste = true;
    private String dirJump = "right";
    
    public int jumpHeight = 32;
    public int jumpFrame = 0;

    
    public int dir;
    private int frameAnimation =0;
    private int maxFrames = 15;
    private int maxSprite = 2;
    private int curSprite = 0;
    

	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
	}
	
	public void tick(){
		depth = 2;


        if(World.isFree(this.getX(),(int)(this.getY()+gravity)) && !isJump){
            y+=gravity;
        }

        if(right && World.isFree((int)(this.getX()+speed),this.getY())) {
            x+=speed;
            dirJump = "right";
            dir = 1;
        }else if(left && World.isFree((int)(this.getX()-speed),this.getY())) {
            x-=speed;
            dirJump = "left";
            dir = -1;
        }

       

        if(jump){
            if(!World.isFree(this.getX(),this.getY()+1)){
                isJump = true;
            }else{
                jump = false;
            }
        }


        if(isJump){
            if(World.isFree(this.getX(), this.getY()-2)){
                y-=2;
                jumpFrame+=2;
                dir =3 ;
                if(jumpFrame == jumpHeight){
                    isJump = false;
                    teste = false;
                    jump = false;
                    jumpFrame=0;
                }
            }else{
                isJump = false;
                jump = false;
                teste = true;
                jumpFrame=0;
            }
        }


        Camera.x = Camera.clamp((int)x - Game.WIDTH/2, 0, World.WIDTH*16- Game.WIDTH);
        Camera.y = Camera.clamp((int)y - Game.HEIGHT/2, 0,World.HEIGHT*16 - Game.HEIGHT);
	}

    public void render(Graphics g){
 
        if(isWalk){
            frameAnimation++;
        
            if(frameAnimation == maxFrames){
                curSprite++;
                frameAnimation = 0;
                if(curSprite == maxSprite){
                    curSprite = 0;
                }
            }
        }else {
            curSprite = 0;
        }



        if(dir == 1){
            sprite = Entity.PLAYER_SPRITE_RIGHT[curSprite];
        }else if(dir == -1){
            sprite = Entity.PLAYER_SPRITE_LEFT[curSprite];
        }else if( dirJump == "right"){
           sprite = Entity.PLAYER_SPRITE_JUMP_RIGHT[0];
            if(!teste){ 
                sprite = Entity.PLAYER_SPRITE_JUMP_RIGHT[1];
                if(!World.isFree(this.getX(),this.getY()+1)){
                    teste = true;
                }
            }else {
                dir = 1;
            }
        }else if( dirJump == "left"){
            sprite = Entity.PLAYER_SPRITE_JUMP_LEFT[0];
             if(!teste){ 
                 sprite = Entity.PLAYER_SPRITE_JUMP_LEFT[1];
                 if(!World.isFree(this.getX(),this.getY()+1)){
                     teste = true;
                 }
             }else {
                 dir = -1;
             }
         }
     
        super.render(g);


    }

}
