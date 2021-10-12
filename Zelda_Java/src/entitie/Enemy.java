package entitie;

import java.awt.image.BufferedImage;
import java.util.Random;
import java.awt.*;

import jsudio.Game;
import jsudio.Sound; 
import world.AStar;
import world.Camera;
import world.Vector2i;
import world.World;

public class Enemy extends Entity {
    private boolean moved;
    private double speed = 1;

    private boolean right, up, left, down;
    private int right_dir = 0, left_dir = 1;
    private int down_dir = 2, up_dir = 3;
    private int dir = right_dir;
    private BufferedImage[] sprites;

    private int life = 10;

    private int frames = 0, maxFrame = 42, index = 0, maxInde = 3;

    private boolean enemyDanege = false;
    private int framesDanege = 0;

    public Enemy(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, null);

        sprites = new BufferedImage[4];
        sprites[0] = Game.sprite.getSprite(331, 1, 29, 29);// baixo
        sprites[1] = Game.sprite.getSprite(361, 1, 29, 29);// esquerda
        sprites[2] = Game.sprite.getSprite(391, 1, 29, 29);// cima
        sprites[3] = Game.sprite.getSprite(421, 1, 21, 29);// direita
    }

    public void tick(){
        depth = 0;
        moved = false;
        
        // if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 100){

        //     if(this.isCollidingWithPlayer() == false){
        //         if((int)x < Game.player.getX() && World.isFree((int) (x +speed ), this.getY())
        //                                         && !isColidding((int) (x +speed ), this.getY())){
        //             right = false;
        //             left = true;
        //             down = false;
        //             up = false;
        //             x+= speed;
        //         }else if((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())
        //                                              && !isColidding((int)(x-speed), this.getY())) {
        //             right = true;
        //             left = false;
        //             down = false;
        //             up = false;
        //             x-=speed;
        //         }

        //         if( (int)y < Game.player.getY() && World.isFree( this.getX(), (int) (y + speed )) 
        //                                         && !isColidding(this.getX(), (int) (y + speed ))){
        //             right = false;
        //             left = false;
        //             down = true;
        //             up = false;
        //             y+= speed;
        //         }else if((int) y > Game.player.getY() && World.isFree( this.getX(), (int) (y -speed)  )
        //                                             && !isColidding(this.getX(), (int) (y - speed ))){
        //             right = false;
        //             left = false;
        //             down = false;
        //             up = true;
        //             y-=speed;
        //         }
        //     }else {
        //         //estamos colididngo
        //         if(Game.rand.nextInt(100) < 5){
        //             Sound.hurtEffect.play();
        //             Game.player.life-= 1;
        //             Game.player.isDamaged = true;
        //         }
        //     }
        // }

        // if(!isCollidingWithPlayer()){
        //     if(path == null || path.size() == 0){
        //         Vector2i start = new Vector2i((int)(x/16), (int)(y/16));
        //         Vector2i end = new Vector2i((int)(Game.player.x/16), (int)(Game.player.y/16));
        //         path = AStar.findPath(Game.world, start, end);
        //     }
    
        // }else {
        //     if(new Random().nextInt(100) < 5){
        //         Game.player.life-= Game.rand.nextInt(3);
        //         Game.player.isDamaged = true;
        //     }
        // }

        // followPath(path);


        if(right){
            moved  = true;
            dir = right_dir;
        }else if (down){
            moved = true;
            dir = down_dir;
        }else if(up){
            moved = true;
            dir = up_dir;
        }else if(left){
            moved = true;
            dir = left_dir;
        }

        if(moved){
            frames ++;
            if(frames == maxFrame){
                frames =0;
                index++;

                if(index > maxInde){
                    index = 0;
                }
            }
        }

        collidingBullet();

        if(life <= 0 ){
            destroySelf();
            return;
        }

        if(enemyDanege){
            this.framesDanege ++;

            if(this.framesDanege == 2 ){
                this.framesDanege = 0;
                enemyDanege = false;
            }

        }

    }

    private void destroySelf() {
        Game.enemy.remove(this);
        Game.entities.remove(this);

    }

    private void collidingBullet() {
        for (int i = 0; i < Game.arrow.size(); i++) {
            Entity e = Game.arrow.get(i);

            if (Entity.isColliding(this, e)) {
                life--;
                Game.entities.remove(e);
                Game.arrow.remove(i);
                enemyDanege = true;
                return;
            }
        }
    }

    public boolean isCollidingWithPlayer() {
        Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
        Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 24);

        return enemyCurrent.intersects(player);
    }

    // public boolean isColidding(int xNext, int yNext) {
    //     Rectangle enemyCurrent = new Rectangle(xNext + maskx, yNext + masky, maskw, maskh);

    //     for (int i = 0; i < Game.enemy.size(); i++) {
    //         Enemy e = Game.enemy.get(i);
    //         if (e == this) {
    //             continue;
    //         }

    //         Rectangle targetEnenmy = new Rectangle(e.getX() + maskx, e.getY() + masky, maskw, maskh);
    //         if (enemyCurrent.intersects(targetEnenmy)) {
    //             return true;
    //         }
    //     }

    //     return false;
    // }

    public void render(Graphics g) {

        if (!enemyDanege) {
            if (dir == right_dir) {
                g.drawImage(sprites[3], getX() - Camera.x, getY() - Camera.y, null);
            } else if (dir == down_dir) {
                g.drawImage(sprites[0], getX() - Camera.x, getY() - Camera.y, null);
            } else if (dir == up_dir) {
                g.drawImage(sprites[2], getX() - Camera.x, getY() - Camera.y, null);
            } else if (dir == left_dir) {
                g.drawImage(sprites[1], getX() - Camera.x, getY() - Camera.y, null);
            }
        } else {
            g.drawImage(Entity.Enemy_FEEDBACK, getX() - Camera.x, getY() - Camera.y, null);
        }
        // super.render(g);
        // g.setColor(Color.black);
        // g.fillRect(this.getX() + maskx - Camera.x, this.getY()+ masky- Camera.y, 16 +
        // maskw, 24 +maskh);
    }
}
