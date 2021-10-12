package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.awt.*;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;
import com.gcstudios.world.Node;
import com.gcstudios.world.Vector2i;
import com.gcstudios.world.World;


public class Entity {
	
    // player
    public static BufferedImage[] PLAYER_SPRITE_RIGHT = {Game.spritePlayer.getSprite(209, 0,14,27), Game.spritePlayer.getSprite(328, 0,15,19)};
    public static BufferedImage[] PLAYER_SPRITE_JUMP_RIGHT = {Game.spritePlayer.getSprite(208, 39,14,20),Game.spritePlayer.getSprite(248, 40,16,20)};

    public static BufferedImage[] PLAYER_SPRITE_LEFT = {Game.spritePlayer.getSprite(169, 0,14,20),Game.spritePlayer.getSprite(49, 0,15,19) };
    public static BufferedImage[] PLAYER_SPRITE_JUMP_LEFT = {Game.spritePlayer.getSprite(168, 39,14,20),Game.spritePlayer.getSprite(128, 40,14,20)};
    

    // enemy
    public static BufferedImage[] ENEMY_KOOPA_SPRITE_LEFT = {Game.spriteEnemy.getSprite(132,0,16,27),Game.spriteEnemy.getSprite(92,0,16,27),Game.spriteEnemy.getSprite(52,0,16,27)};
    public static BufferedImage[] ENEMY_KOOPA_SPRITE_RIGHT = {Game.spriteEnemy.getSprite(51,316,16,27),Game.spriteEnemy.getSprite(91,317,16,27),Game.spriteEnemy.getSprite(131,316,16,27)};

    

	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected double speed;
    protected double gravity = 2;
	
	public int depth;

	protected List<Node> path;
	
	public boolean debug = false;
	
	protected BufferedImage sprite;
	
	public static Random rand = new Random();
	
	public Entity(double x,double y,int width,int height,double speed,BufferedImage sprite){
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	
	public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity n0,Entity n1) {
			if(n1.depth < n0.depth)
				return +1;
			if(n1.depth > n0.depth)
				return -1;
			return 0;
		}
		
	};
	
	
	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*16 - Game.HEIGHT);
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void tick(){}
	
	public double calculateDistance(int x1,int y1,int x2,int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	
	public void followPath(List<Node> path) {
		if(path != null) {
			if(path.size() > 0) {
				Vector2i target = path.get(path.size() - 1).tile;
				//xprev = x;
				//yprev = y;
				if(x < target.x * 16) {
					x++;
				}else if(x > target.x * 16) {
					x--;
				}
				
				if(y < target.y * 16) {
					y++;
				}else if(y > target.y * 16) {
					y--;
				}
				
				if(x == target.x * 16 && y == target.y * 16) {
					path.remove(path.size() - 1);
				}
				
			}
		}
	}
	
	public static boolean isColidding(Entity e1,Entity e2){
		Rectangle e1Mask = new Rectangle(e1.getX(),e1.getY(),e1.getWidth(),e1.getHeight());
		Rectangle e2Mask = new Rectangle(e2.getX(),e2.getY(),e2.getWidth(),e2.getHeight());
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite,this.getX() - Camera.x,this.getY() - Camera.y,null);
		g.setColor(Color.red);
		g.fillRect(this.getX() + 8 - Camera.x,this.getY() + 8 - Camera.y,8,8);
	}
	
}
