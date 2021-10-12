package com.gcstudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;

public class Tile {
	
	public static BufferedImage TILE_FLOOR = Game.spriteFloor.getSprite(228,92,16,16);
	public static BufferedImage TILE_WALL = Game.spriteFloor.getSprite(24,5,16,16);
	public static BufferedImage TILE_WALL_2 = Game.spriteFloor.getSprite(24,22,16,16);
	public static BufferedImage TILE_WALL_3 = Game.spriteFloor.getSprite(7,5,16,16);

	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x,int y,BufferedImage sprite){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g){
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}

}
