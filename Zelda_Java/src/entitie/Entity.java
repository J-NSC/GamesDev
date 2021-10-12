package entitie;

import java.awt.image.*;
import java.util.List;

import jsudio.Game;
import world.Camera;
import world.Node;
import world.Vector2i;
import java.util.Comparator;


import java.awt.*;

public class Entity {

public int maskx , masky, maskw, maskh;
public static BufferedImage LIFEPACK_EN = Game.sprite.getSprite(241, 1, 16, 16);
public static BufferedImage WEAPONPACK_EN = Game.sprite.getSprite(271, 1, 16, 16);
public static BufferedImage ARROW_EN = Game.sprite.getSprite(301, 1, 16, 16);
public static BufferedImage ENEMY_EN = Game.sprite.getSprite(331, 1, 29, 29);

public static BufferedImage Enemy_FEEDBACK = Game.sprite.getSprite(121, 1 ,29,29);

public static BufferedImage arrow_shoting_right = Game.sprite.getSprite(211, 181, 29,29);
public static BufferedImage arrow_shoting_left = Game.sprite.getSprite(151, 181, 29,29);
public static BufferedImage arrow_shoting_up = Game.sprite.getSprite(181, 151, 29,29);
public static BufferedImage arrow_shoting_down = Game.sprite.getSprite(181, 181, 29,21);

public static BufferedImage gun_right = Game.sprite.getSprite(301, 151, 29, 29);
public static BufferedImage gun_left = Game.sprite.getSprite(301, 61, 29, 29);
public static BufferedImage gun_up = Game.sprite.getSprite(61, 151, 23, 23);
public static BufferedImage gun_donw = Game.sprite.getSprite(61, 61, 23, 23);

public int depth;
public double x;
public double y; 

protected int width;
protected int height;
protected List<Node> path;


    private BufferedImage sprite;

    public Entity (double x, double y ,int width, int height, BufferedImage sprite){
        this. x = x;
        this.y = y;
        this.width =width;
        this.height = height;
        this.sprite = sprite;

        this.maskx = 0;
        this.masky = 0;
        this.maskw = width;
        this.maskh = height;
    }

    public void setMask (int maskx, int masky , int maskw , int maskh ){
        this.maskx = maskx;
        this.masky = masky;
        this.maskw = maskw;
        this.maskh = maskh;
    }
    public static Comparator<Entity> entitySorter = new Comparator<Entity>(){

        @Override
        public int compare(Entity n0, Entity n1) {
            if(n0.depth < n1.depth)
                return -1;

            if(n1.depth > n0.depth)
                return +1;

            return 0;
        }
        
    };

    

    public int getX(){
        return (int)this.x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return (int)this.y;
    }

    public void setY(int y){
        this.y = y;
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public int getHeight(){
        return this.height;
    }

    public void tick(){
        
    }

    public double calculateDistance(int x1,int y1, int x2,int y2){
        return Math.sqrt((x1 - x2) * (x1 -x2) + (y1 - y2) * (y1 -y2) );
    }

    public void followPath(List<Node> path){
        if(path != null){
            if(path.size() > 0){
                Vector2i target = path.get(path.size() - 1).tile;
                // xprev = x;
                // yprev = y

                if(x < target.x * 16 && !isColliding2(this.getX() + 1,this.getY())){
                    x++;
                }else if (x > target.x *16 && !isColliding2(this.getX() - 1,this.getY()) ){
                    x--;
                }

                if(y < target.y * 16 && !isColliding2(this.getX() ,this.getY()+ 1)){
                    y++;
                }else if(y > target.y * 16 && !isColliding2(this.getX() ,this.getY()- 1)){
                    y--;
                }

                if(x== target.x * 16 && y == target.y * 16){
                    path.remove(path.size()-1);
                }
            }
        }
    }


    public static boolean isColliding(Entity e1, Entity e2){
        Rectangle e1Mask = new Rectangle(e1.getX()+ e1.maskx,e1.getY() + e1.masky, e1.maskw, e1.maskh);
        Rectangle e2Mask = new Rectangle(e2.getX()+ e2.maskx,e2.getY() + e2.masky, e2.maskw, e2.maskh);

        return e1Mask.intersects(e2Mask);

    }

    public boolean isColliding2(int xNext, int yNext) {
        Rectangle enemyCurrent = new Rectangle(xNext + maskx, yNext + masky, maskw, maskh);

        for (int i = 0; i < Game.enemy.size(); i++) {
            Enemy e = Game.enemy.get(i);
            if (e == this) {
                continue;
            }

            Rectangle targetEnenmy = new Rectangle(e.getX() + maskx, e.getY() + masky, maskw, maskh);
            if (enemyCurrent.intersects(targetEnenmy)) {
                return true;
            }
        }

        return false;
    }

    public void render(Graphics g){
        g.drawImage(sprite, (int)this.getX()- Camera.x, (int)this.getY() - Camera.y, null);

        // g.setColor(Color.red);
        // g.fillRect(this.getX() + maskx - Camera.x, this.getY()+ masky- Camera.y, 16 + maskw, 24 +maskh);
    }

   
    
}   
