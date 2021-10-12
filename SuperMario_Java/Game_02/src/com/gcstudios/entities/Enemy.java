package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.world.Camera;
import com.gcstudios.world.World;



public class Enemy extends Entity{
	
    public int vida = 1;
    private boolean right= true,left= false;

    private int frameAnimation =0;
    private int maxFrames = 15;
    private int maxSprite = 2;
    private int curSprite = 0;

	public Enemy(int x, int y, int width, int height,double speed, BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
        speed = 0.01;
	}

	public void tick(){
		depth = 2;



        
        if(World.isFree(this.getX(),(int)(this.getY()+1))){
            y+=gravity;
        }

        if(right){
          
          if(World.isFree((int)(x+speed),(int)y)){
            
                x+=speed;

                if(World.isFree((this.getX()+16), this.getY()+1)){
                    right = false;
                    left = true;
                }
            }else{
                right = false;
                left = true;
            }
        }

        if(left ){
            if(World.isFree((int)(x-speed),(int)y)){
                x-=speed;

                if(World.isFree((this.getX()-16), this.getY()+1)){
                    right = true;
                    left = false;
                }
            }else{
                right = true;
                left = false;   
            }
        }



        if(vida == 0){
            // mudar os sprites 
        }
		/*
		if(path == null || path.size() == 0) {
				Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
				Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
				path = AStar.findPath(Game.world, start, end);
			}
		
			if(new Random().nextInt(100) < 50)
				followPath(path);
			
			if(x % 16 == 0 && y % 16 == 0) {
				if(new Random().nextInt(100) < 10) {
					Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
					Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
					path = AStar.findPath(Game.world, start, end);
				}
			}
			*/
		
	}
	

	
	public void render(Graphics g) {

        frameAnimation++;
    
        if(frameAnimation == maxFrames){
            curSprite++;
            frameAnimation = 0;
            if(curSprite == maxSprite){
                curSprite = 0;
            }
        }

        if(right){
            g.drawImage(Entity.ENEMY_KOOPA_SPRITE_RIGHT[curSprite], this.getX()- Camera.x,(this.getY()-Camera.y) - 11,null);
        }else if(left){
            g.drawImage(Entity.ENEMY_KOOPA_SPRITE_LEFT[curSprite], this.getX()- Camera.x,(this.getY()-Camera.y) - 11,null); 
        }
        
	}
	
	
}
