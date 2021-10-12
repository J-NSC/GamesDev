package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entitie.Arrow;
import entitie.Enemy;
import entitie.Entity;
import entitie.LifePack;
import entitie.Weapon;
import jsudio.Game;
import entitie.Player;
import entitie.Npc;
import world.Particles;
import grafico.Spritesheet;

import java.util.ArrayList;

public class World {

    public static Tile[] tiles;
    public static int WIDTH, HEIGHT;
    public static final int TILE_SIZE = 16;

    public World (String path){
        // Game.player.setX(0);
        // Game.player.setY(0);

        // WIDTH =  100;
        // HEIGHT =  100;

        // tiles = new Tile[WIDTH*HEIGHT];

        // for (int xx = 0; xx < WIDTH; xx++) {
        //     for (int yy = 0; yy < HEIGHT; yy++) {
        //         tiles[xx+yy*WIDTH] = new WallTile(xx*16, yy*16,Tile.TILE_WALL);
                
        //     }
        // }

        // int dir = 0;
        // int xx = 0 , yy = 0;


        // for (int i = 0; i < 200; i++) {
        //     tiles[xx+(yy*WIDTH)] = new FloorTile(xx*16, yy*16,Tile.TILE_FLOOR);

        //     if(dir == 0){
        //         // direita;
        //         if(xx < WIDTH){
        //             xx++;
        //         }
        //     }else if (dir == 1){
        //         if(xx > 0){
        //             xx--;
        //         }
        //     }else if(dir == 2){
        //         if(yy < HEIGHT){
        //             yy++;
        //         }
        //     }else if(dir == 3){
        //         if(yy > 0){
        //             yy--;
        //         }
        //     }

        //     if(Game.rand.nextInt(100)< 30){
        //         dir = Game.rand.nextInt(4);
        //     }

        // }

        try {
            BufferedImage map = ImageIO.read(getClass().getResource(path));
            int[] pixels = new int[map.getWidth() * map.getHeight()];
            WIDTH = map.getWidth();
            HEIGHT = map.getHeight();
            tiles = new Tile [map.getWidth() *  map.getHeight()];
            map.getRGB(0, 0, map.getWidth(), map.getHeight(),pixels ,0,map.getWidth());
        
            for(int xx = 0; xx < map.getWidth(); xx++){
                for(int yy = 0 ; yy < map.getHeight(); yy++){
        
                    int pixelAtual = pixels[xx + (yy * map.getWidth())];
                    tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16,yy*16, Tile.TILE_FLOOR);
        
                    if(pixelAtual == 0xFF000000 ){
                        //floor
                        tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16,yy*16, Tile.TILE_FLOOR);
                    }else if( pixelAtual == 0xFFFFFFFF){
                        //parede
                        tiles[xx + (yy*WIDTH)] = new WallTile(xx*16,yy*16, Tile.TILE_WALL);
                    }else if(pixelAtual == 0xFF0094FF){
                        //player
                        Game.player.setX(xx*16);
                        Game.player.setY(yy*16);
        
        
                    }else if(pixelAtual == 0xFFFF0000){
                        //ENEMY
                        Enemy en = new Enemy(xx * 16, yy *16, 16,16,Entity.ENEMY_EN);
                        Game.entities.add(en);
                        Game.enemy.add(en);
                        
                    }else if(pixelAtual == 0xFFFFE97F){
                        //Weapon
                        Game.entities.add(new Weapon(xx * 16, yy *16, 16,16,Entity.WEAPONPACK_EN));
        
                    }else if(pixelAtual == 0xFF00FF21){
                        //LIFE
                        Game.entities.add(new LifePack(xx * 16, yy *16, 16,16,Entity.LIFEPACK_EN));
        
                    }else if(pixelAtual == 0xFF008080){
                        //Arrow
                        Game.entities.add(new Arrow(xx * 16, yy *16, 16,16,Entity.ARROW_EN));
        
                    }
                }
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
     

        
     
    }

    public static boolean isFree(int xnext, int ynext) {

        int x1 = xnext / TILE_SIZE;
        int y1 = ynext / TILE_SIZE;

        int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
        int y2 = ynext / TILE_SIZE;

        int x3 = xnext / TILE_SIZE;
        int y3 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

        int x4 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
        int y4 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

        return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile)
                || (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile)
                || (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile)
                || (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));
    }

    public static void restartGame(String level) {
        Game.entities.clear();
        Game.enemy.clear();
        Game.entities = new ArrayList<Entity>();
        Game.enemy = new ArrayList<Enemy>();
        Game.sprite = new Spritesheet("/link.png");
        Game.player = new Player(0, 0, 16, 16, Game.sprite.getSprite(0, 0, 25, 28));
        Game.npc = new Npc(32,32,16,16,Game.sprite.getSprite(241, 241, 16,23));
        Game.entities.add(Game.npc);
        Game.entities.add(Game.player);
        Game.world = new World("/" + level);
    }

    public void render(Graphics g) {
        int xstart = Camera.x >> 4;
        int ystart = Camera.y >> 4;

        int xfinal = xstart + (Game.WIDTH >> 4);
        int yfinal = ystart + (Game.HEIDTH >> 4);

        for (int xx = xstart; xx <= xfinal; xx++) {
            for (int yy = ystart; yy <= yfinal; yy++) {
                if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
                    continue;
                Tile tile = tiles[xx + (yy * WIDTH)];
                tile.render(g);
            }
        }
    }

    public static void generateParticles(int amount, double x, double y){
        for (int i = 0; i < amount; i++) {
            Game.entities.add(new Particles(x,y, 1, 1, null));
        }
    }

    public static void renderMiniMap() {
        for (int i = 0; i < Game.miniMapaPixels.length; i++) {
            Game.miniMapaPixels[i] = 0;
        }
        for (int xx = 0; xx < WIDTH; xx++) {
            for (int yy = 0; yy < HEIGHT; yy++) {
                if (tiles[xx + (yy * WIDTH)] instanceof WallTile) {
                    Game.miniMapaPixels[xx + (yy * WIDTH)] = 0xffffffff;
                }
            }
        }

        int xPlayer = Game.player.getX() / 16;
        int yPlayer = Game.player.getY() / 16;

        Game.miniMapaPixels[xPlayer + (yPlayer * WIDTH)] = 0x0000ff;

    }
}