package com.gcstudios.entities;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.itens.Mushroom;
import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;
import com.gcstudios.world.World;

public class Player extends Entity{
	
	public boolean right,up,left,down;
    public boolean isJump = false, jump = false, isWalk= false,fallJump = true;
    private String dirJump = "right";
    
    public int jumpHeight = 32;
    // 32
    public int jumpFrame = 0;

    public static double vida  = 100;
    public int dir;
    private int frameAnimation =0;
    private int maxFrames = 15;
    private int maxSprite = 2;
    private int curSprite = 0;
    public static int qCoin = 0;
    private String teste = "pequeno";

    // private double vspd = 0;
    // private double gravit = 0.3;

    

	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
	}
	
	public void tick(){
		depth = 2;


        if(World.isFree(this.getX(),(int)(this.getY()+gravity)) && !isJump){
            y+=gravity;

            // dano do pulo
            for (int i = 0; i < Game.entities.size(); i++) {
                Entity e= Game.entities.get(i);

                if(e instanceof Enemy){
                    if(Entity.isColidding(this, e)){
                        isJump = true;
                        jumpHeight =32;

                        ((Enemy)e).vida--;


                        if( ((Enemy)e).vida == 0){
                            Game.entities.remove(e);
                            break;
                        }
                    }
                }
              

            }

        }


        // vspd += gravit;
        // if(!World.isFree(getX(), getY()+1) && jump){
        //     vspd = -6;
        //     jump = false;
        // }

        // if(!World.isFree(getX(),(int)(getY()+vspd))){
        //     int singVsp = 0;

        //     if(vspd >= 0)
        //         singVsp = 1;
        //     else 
        //         singVsp = -1;
            
        //     while(World.isFree(getX(), getY()+singVsp)){
        //         y+= singVsp;
        //     }
        //     vspd = 0;
        // }

        // y+=vspd;

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
                    fallJump = false;
                    jump = false;
                    jumpFrame=0;
                }
            }else{
                isJump = false;
                jump = false;
                fallJump = true;
                jumpFrame=0;
            }
        }


        // colisão

        for (int i = 0; i < Game.entities.size(); i++) {
            Entity e= Game.entities.get(i);

            if(e instanceof Enemy){
                if(Entity.isColidding(this, e)){
                    if(Entity.rand.nextInt(100)<5){
                        vida--;
                    }
                }
            }

            if(e instanceof Coin){
                if(Entity.isColidding(this, e)){

                    Game.entities.remove(e);
                    qCoin++;
                }
            }

            if(e instanceof Mushroom){
                if(Entity.isColidding(this, e)){
                    Game.entities.remove(e);
                    teste = "grande";
                }
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



        if(teste == "pequeno"){

            if(dir == 1){
                sprite = Entity.PLAYER_SPRITE_RIGHT[curSprite];
            }else if(dir == -1){
                sprite = Entity.PLAYER_SPRITE_LEFT[curSprite];
            }else if( dirJump == "right"){
            sprite = Entity.PLAYER_SPRITE_JUMP_RIGHT[0];
                if(!fallJump){ 
                    sprite = Entity.PLAYER_SPRITE_JUMP_RIGHT[1];
                    if(!World.isFree(this.getX(),this.getY()+1)){
                        fallJump = true;
                    }
                }else {
                    dir = 1;
                }
            }else if( dirJump == "left"){
                sprite = Entity.PLAYER_SPRITE_JUMP_LEFT[0];
                if(!fallJump){ 
                    sprite = Entity.PLAYER_SPRITE_JUMP_LEFT[1];
                    if(!World.isFree(this.getX(),this.getY()+1)){
                        fallJump = true;
                    }
                }else {
                    dir = -1;
                }
            }
        }else if(teste == "grande"){
            sprite = Entity.PLAYERBIG_SPRITE_RIGHT[0];
        }

        
        super.render(g);


    }

}
